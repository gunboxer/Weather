package com.weather.web.domain;

import java.util.List;

/**
 *
 * @author ryagudin
 */
public class HistoryResponse {
    private List<History> histories;
    private int count;
    
    public HistoryResponse(List<History> histories, int count) {
        this.histories = histories;
        this.count = count;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
}
