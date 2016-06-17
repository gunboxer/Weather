package com.weather.web.mappers;

import com.weather.web.domain.User;

/**
 *
 * @author ryagudin
 */
public interface UserMapper {
    public User getUserByName(String userName);
}

