package com.satish.techie.util;

public class CommonUtility {

    private CommonUtility() {}

    public static String findEventOrOld(Integer number) {
        return number % 2 == 0 ? "Even" : "Odd";
    }

}
