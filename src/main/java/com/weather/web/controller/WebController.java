package com.weather.web.controller;

import com.google.gson.Gson;
import com.weather.web.domain.History;
import com.weather.web.domain.HistoryResponse;
import com.weather.web.domain.SelectLimitation;
import com.weather.web.jms.JmsSender;
import com.weather.web.service.HistoryService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.json.JSONObject;

/**
 *
 * @author ryagudin
 */
@Controller
public class WebController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private JmsSender jmsSender;

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public void header() {

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        Map model = new HashMap();
        if (error != null) {
            logger.info("RequestMethod.GET /login?error");
            model.put("error", "Invalid username and password!");
        }
        if (logout != null) {
            logger.info("RequestMethod.GET /login?logout");
            model.put("msg", "You've been logged out successfully.");
        }
        setHeaderData(model);
        ModelAndView modelAndView = new ModelAndView("Login", model);
        modelAndView.setViewName("login");
        logger.info("RequestMethod.GET /login return modelAndView");
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request) {
        logger.info("RequestMethod.GET /login");
        return "redirect:/login";
    }

    private static final ExecutorService handler = Executors.newSingleThreadExecutor();

    @RequestMapping(value = "/getweather", method = RequestMethod.GET)
    @ResponseBody
    public String getweather(@RequestParam(value = "filter", required = false) String filter) {
        logger.info("RequestMethod.GET /getweather?filter=" + filter);
        jmsSender.send(filter);
        //Тут можно сделать по другому, отправлять запросы на поиск в activemq, на который будет смотреть воркер и выполнять последовательно работу
        //Класть резльтат обратно с activemq, а потом будет мониторить когда к нему придет ответ возвращать его.
        //Можно также использовать пуш-уведомление
        //Можно возвращать хэш код запроса и на клиенте переодически чекать результат обработки по этому хэш коду.
        //Можно переключаться в режим повышенной нагрузки, в случае когда в очереди слишком много запросов, тогда возвращать хэш запроса чтобы чекать ответ
        /*if (filter == null || filter.isEmpty()) {
            return "";
        }*/
        //filter может содержать запрещенные символы, можно проверять это на стороне клиента через валидатор поля
        //но опыт мне подсказывает что рядовому пользователю это не интересно и только отвлекает его от работы
        //ну и естественно проверяющий задание может попробовать вводить в это после всякий мусор
        //здесь я не буду изобретать самолет и просто удалю запрещенные символы, которые нельзя юзать по RFC 1738.
        String goodfilter = filter.replaceAll("[;/?:@=&,]", "");

        try {
            Future<String> result = handler.submit(new CallableThread(goodfilter));
            String retval = result.get();

            JSONObject json = new JSONObject(retval);
            String city = json.getString("name");
            // Тут по хорошему надо добавить проверку получаем ли мы действительно температуру или нет.
            int temperature = Math.round((float) (json.getJSONObject("main").getDouble("temp") - 273.15));
            History history = new History(getLogin(), filter, city, temperature);
            historyService.insertHistory(history);
            return retval;
        } catch (InterruptedException ex) {
            logger.error("RequestMethod.GET /upload return InterruptedException: " + ex.getMessage());
            //таким образом я могу пробрасывать ошибку на клиента.
            return "{\"error\":\"" + ex.getMessage() + "\"}";
        } catch (ExecutionException ex) {
            logger.error("RequestMethod.GET /upload return ExecutionException: " + ex.getMessage());
            return "{\"error\":\"" + ex.getMessage() + "\"}";
        }
    }

    @RequestMapping(value = "/gethistory", method = RequestMethod.GET)
    @ResponseBody
    public String gethistory(@RequestParam(value = "page", required = false) int page) {
        logger.info("RequestMethod.GET /gethistory?page=" + page);
        SelectLimitation selectLimitation = new SelectLimitation();
        selectLimitation.setPage(page > 0 ? page - 1 : page);
        Gson gson = new Gson();
        HistoryResponse historyResponse = new HistoryResponse(historyService.getHistoryList(selectLimitation), historyService.getHistoryCount());
        return gson.toJson(historyResponse);
    }

    @RequestMapping(value = {"/", "/weather"}, method = RequestMethod.GET)
    public ModelAndView weather(HttpServletRequest request, HttpServletResponse response) {
        logger.info("RequestMethod.GET /weather");
        Map model = new HashMap();
        setHeaderData(model);
        ModelAndView modelAndView = new ModelAndView("Weather", model);
        modelAndView.setViewName("weather");
        return modelAndView;
    }

    private boolean isAdministrator() {
        return ((Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities()).contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private boolean isUser() {
        return ((Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities()).contains(new SimpleGrantedAuthority("ROLE_USER"));
    }

    private String getLogin() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || "java.lang.String".equals(user.getClass().toString())) {
            return "";
        }
        if (user.getClass().toString().contains("org.springframework.security.core.userdetails.UserDetails")) {
            return ((UserDetails) user).getUsername();
        }
        if (user.getClass().toString().contains("org.springframework.security.core.userdetails.User")) {
            return ((org.springframework.security.core.userdetails.User) user).getUsername();
        }
        return "";
    }

    private void setHeaderData(Map model) {
        model.put("isAdministrator", isAdministrator());
        model.put("isUser", isUser());
        model.put("login", getLogin());
    }
}
