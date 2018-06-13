package main.java;

/**
 * Created by guoyifeng on 6/12/18.
 */
public class CityFactory {
    public City createCity(String name) {
        return new City(name);
    }
}
