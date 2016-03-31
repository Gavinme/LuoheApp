/*
 * Created by Storm Zhang, Feb 13, 2014.
 */

package com.luohe.android.luohe.net.data;

import java.util.HashMap;

public class ApiParams extends HashMap<String, String> {

    public ApiParams with(String key, String value) {
        put(key, value);
        return this;
    }
}
