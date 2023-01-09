package com.github.cbuschka.striketool.core.io;

import java.nio.file.Path;
import java.util.regex.Pattern;


public class PathPattern {
    private String strPattern;
    private Pattern regexPattern;

    public static PathPattern valueOf(String s) {
        String regexStr = s.replace("[", "\\[")
                .replace("(", "\\(")
                .replace(".", "\\.")
                .replace("**", ".*")
                .replaceAll("[^.]?*", "[^/]*");
        Pattern regexPattern = Pattern.compile(regexStr);
        return new PathPattern(s, regexPattern);
    }

    private PathPattern(String strPattern, Pattern regexPattern) {
        this.strPattern = strPattern;
        this.regexPattern = regexPattern;
    }

    public boolean matches(String s) {
        return regexPattern.matcher(s).matches();
    }

    public boolean matches(Path p) {
        return regexPattern.matcher(p.toString().replace("\\", "/")).matches();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{pattern=" + strPattern + "}";
    }
}
