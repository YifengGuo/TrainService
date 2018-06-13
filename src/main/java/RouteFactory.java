package main.java;

/**
 * Created by guoyifeng on 6/12/18.
 */
public class RouteFactory {
    public Route createRoute(City start, City end, int distance) {
        return new Route(start, end, distance);
    }
}
