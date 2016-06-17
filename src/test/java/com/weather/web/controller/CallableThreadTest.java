/*
 * Copyright 2016 NightWolf.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weather.web.controller;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author ryagudin
 */
public class CallableThreadTest {

    //Написал для примера тест, можно наверно еще какие-то тесты придумать...
    @Test
    public void CallableThreadTestExecute() throws Exception {
        CallableThread callableThread = new CallableThread("Ufa");
        String resultJSON = callableThread.call();
        JSONObject json = new JSONObject(resultJSON);
        String city = json.getString("name");
        Assert.assertEquals("Ufa", city);
    }

}
