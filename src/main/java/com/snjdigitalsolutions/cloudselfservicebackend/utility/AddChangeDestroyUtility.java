package com.snjdigitalsolutions.cloudselfservicebackend.utility;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AddChangeDestroyUtility {

    public Optional<String> matchLine(String line) {
        Optional<String> result = Optional.empty();
        Pattern pattern = Pattern.compile("(\\d+) to add, (\\d+) to change, (\\d+) to destroy");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            result = Optional.of(matcher.group());
        }
        return result;
    }

    public Integer numberToAdd(String line) {
        int result = -1;
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            result = Integer.parseInt(matcher.group());
        }
        return result;
    }

    public Integer numberToChange(String line) {
        int result = -1;
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find() && matcher.find()) {
            result = Integer.parseInt(matcher.group());
        }
        return result;
    }

    public Integer numberToDestroy(String line) {
        int result = -1;
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find() && matcher.find() && matcher.find()) {
            result = Integer.parseInt(matcher.group());
        }
        return result;
    }



}
