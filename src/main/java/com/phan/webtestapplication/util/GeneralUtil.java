package com.phan.webtestapplication.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GeneralUtil {

    public static String getStackTrace(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    public static String getPageTitle(String title) {
        if (title == null) {
            return "Webtest Application";
        }
        return title + " | Webtest Application";
    }

}
