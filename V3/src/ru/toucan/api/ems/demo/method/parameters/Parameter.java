package ru.toucan.api.ems.demo.method.parameters;

/**
 * Created by Nastya on 17.04.2015.
 */
public class Parameter {

    public String name;

    public String caption;

    public String typeValue;

    public String defaultValue;

    @Override
    public String toString() {
        return "Parameter{name=" + name +
                ",caption="+caption +
                ",typeValue="+typeValue+
                ",defaultValue=" + defaultValue+"}";
    }
}
