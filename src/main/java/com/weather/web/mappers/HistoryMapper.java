package com.weather.web.mappers;

import com.weather.web.domain.History;
import com.weather.web.domain.SelectLimitation;
import java.util.List;

/**
 *
 * @author ryagudin
 */
public interface HistoryMapper {
    public void insertHistory(History history);
    public List getHistoryList(SelectLimitation selectLimitation);
    public int getHistoryCount();
}
