package com.onecreation.utils;

import java.util.Arrays;
import java.util.Hashtable;

public class ClientArgumentParser {

    private Hashtable<String, String> defaultArgs;

    public ClientArgumentParser(Hashtable<String, String> consts) {
        defaultArgs = consts;
    }

    public Hashtable<String, String> parseCommandLineArgs(String[] args) {
        Hashtable output = new Hashtable<>(defaultArgs);
        Arrays.stream(args).filter(s -> s.contains("input=")).findFirst().ifPresent(x -> output.put("fileName", x.replace("input=", "")));
        Arrays.stream(args).filter(s -> s.contains("host=")).findFirst().ifPresent(x -> output.put("hostName", x.replace("host=", "")));
        Arrays.stream(args).filter(s -> s.contains("hostp=")).findFirst().ifPresent(x -> output.put("portNumber", x.replace("hostp=", "")));
        Arrays.stream(args).filter(s -> s.contains("serverp=")).findFirst().ifPresent(x -> output.put("serverPort", x.replace("serverp=", "")));
        Arrays.stream(args).filter(s -> s.contains("output=")).findFirst().ifPresent(x -> output.put("fileName", x.replace("output=", "")));

        return output;
    }
}
