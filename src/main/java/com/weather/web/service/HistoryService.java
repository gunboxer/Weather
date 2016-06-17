package com.weather.web.service;

import com.weather.web.domain.History;
import com.weather.web.domain.SelectLimitation;
import com.weather.web.mappers.HistoryMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ryagudin
 */
@Service("historyService")
@Transactional
public class HistoryService {

    @Autowired
    private SqlSession sqlSession;

    private static final Logger logger = LoggerFactory.getLogger(HistoryService.class);

    public void insertHistory(History history) {
        HistoryMapper historyMapper = sqlSession.getMapper(HistoryMapper.class);
        logger.info("insertHistory called.");
        historyMapper.insertHistory(history);
    }

    public List getHistoryList(SelectLimitation selectLimitation) {
        HistoryMapper historyMapper = sqlSession.getMapper(HistoryMapper.class);
        logger.info("getHistoryList called.");
        return historyMapper.getHistoryList(selectLimitation);
    }

    public int getHistoryCount() {
        HistoryMapper historyMapper = sqlSession.getMapper(HistoryMapper.class);
        logger.info("getHistoryCount called.");
        return historyMapper.getHistoryCount();
    }

}
