package ru.toucan.api.ems.demo.method.parameters;

import java.util.ArrayList;

/**
 * Created by Nastya on 17.04.2015.
 */
public class Group {

    public String caption;

    public ArrayList<Parameter> parameters;

    @Override
    public String toString() {
        return "Group{caption="+caption +
                ",parameters="+parameters+"}";
    }
}
