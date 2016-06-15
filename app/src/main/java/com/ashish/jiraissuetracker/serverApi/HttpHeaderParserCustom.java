package com.ashish.jiraissuetracker.serverApi;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * Created by Ashish on 16/06/16.
 */
public class HttpHeaderParserCustom extends HttpHeaderParser {

    public static Cache.Entry parseCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();
        Map headers = response.headers;
        long serverDate = 0L;
        long serverExpires = 0L;
        long softExpire = 0L;
        long maxAge = 0L;
        boolean hasCacheControl = false;
        String serverEtag = null;
        String headerValue = (String) headers.get("Date");
        if (headerValue != null) {
            serverDate = parseDateAsEpoch(headerValue);
        }

        headerValue = null;
        if (headerValue != null) {
            hasCacheControl = true;
            String[] entry = headerValue.split(",");

            for (int i = 0; i < entry.length; ++i) {
                String token = entry[i].trim();
                if (token.equals("no-cache") || token.equals("no-store")) {
                    return null;
                }

                if (token.startsWith("max-age=")) {
                    try {
                        maxAge = Long.parseLong(token.substring(8));
                    } catch (Exception var19) {
                        ;
                    }
                } else if (token.equals("must-revalidate") || token.equals("proxy-revalidate")) {
                    maxAge = 0L;
                }
            }
        }

        headerValue = (String) headers.get("Expires");
        if (headerValue != null) {
            serverExpires = parseDateAsEpoch(headerValue);
        }

        serverEtag = (String) headers.get("ETag");
        if (hasCacheControl) {
            softExpire = now + maxAge * 1000L;
        } else if (serverDate > 0L && serverExpires >= serverDate) {
            softExpire = now + (serverExpires - serverDate);
        }

        Cache.Entry var20 = new Cache.Entry();
        var20.data = response.data;
        var20.etag = serverEtag;
        var20.softTtl = softExpire;
        var20.ttl = var20.softTtl;
        var20.serverDate = serverDate;
        var20.responseHeaders = headers;
        return var20;
    }

}
