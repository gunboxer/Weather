package com.weather.web.domain;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ryagudin
 */
public class SelectLimitation 
{
    private int page;
    private final int limit = 10;
    private int offset;
    
    public SelectLimitation(HttpServletRequest request)
    {
        this.page = 0;
        this.offset = 0;
    }

    public SelectLimitation(){
    }
    
    public int getPage(){
        return this.page;
    }
    public void setPage(int page)
    {
        this.page = page;
        this.offset = page * limit;
    }
    
    public int getLimit(){
        return this.limit;
    }
   
    public int getOffset(){
        return this.offset;
    }
    
}
