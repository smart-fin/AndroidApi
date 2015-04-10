package ru.toucan.merchant.business.connector;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 18.07.12
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
public class JsonMapper {

    private static JsonMapper instance;

    public ObjectMapper mapper = new ObjectMapper();

    public static JsonMapper getInstance() {
        if (instance == null) {
            synchronized (JsonMapper.class) {
                if (instance == null) {
                    instance = new JsonMapper();
                }
            }
        }
        return instance;
    }
}
