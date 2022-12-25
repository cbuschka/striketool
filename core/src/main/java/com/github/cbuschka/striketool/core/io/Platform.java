package com.github.cbuschka.striketool.core.io;

public class Platform {

    public static boolean isUnixoid() {
        return osName().contains("linux");
    }

    public static boolean isWindowish() {
        return osName().contains("windows");
    }

    private static String osName() {
        return System.getProperty("os.name", "unknown").toLowerCase();
    }
}
