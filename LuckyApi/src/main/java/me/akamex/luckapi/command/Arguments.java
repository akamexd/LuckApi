package me.akamex.luckapi.command;

import java.util.ArrayList;
import java.util.List;

public class Arguments {

    private final List<String> arguments = new ArrayList<>();
    private int now = 0;

    Arguments(String[] args) {
        for(int x = 0; x < args.length; x++) {
            arguments.add(x, args[x]);
        }
    }

    private Arguments(List<String> args) {
        for(int x = 1; x < args.size(); x++) {
            arguments.add(args.get(x));
        }
    }

    public static Arguments removeFirst(Arguments arguments) {
        return new Arguments(arguments.arguments);
    }

    void nextToStart() {
        now = 0;
    }

    public boolean hasNext() {
        return now < length();
    }

    public String next() {
        if(arguments.size() <= now) {
            nextToStart();
        }
        return get(now++);
    }

    public String current() {
        if(arguments.size() <= now) {
            nextToStart();
        }
        return get(now);
    }

    public String get(int index) {
        if(arguments.size() <= index) {
            return "";
        }
        return arguments.get(index);
    }

    public int length() {
        return arguments.size();
    }

}
