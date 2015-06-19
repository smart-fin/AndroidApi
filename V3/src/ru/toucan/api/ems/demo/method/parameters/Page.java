package ru.toucan.api.ems.demo.method.parameters;

import java.util.ArrayList;

/**
 * Created by Nastya on 17.04.2015.
 */
public class Page {

    public String caption;

    public ArrayList<Group> groups;

    @Override
    public String toString() {
        return "Page{caption=" + caption+
                ",groups="+ groups +"}";
    }
}
