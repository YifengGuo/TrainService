package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoyifeng on 6/12/18.
 */
public class Driver {
    public static void main(String[] args) {
        TrainRoutes trainRoutes = new TrainRoutes();
        List<Route> routeList = generateRoutes();
        List<City> cities = generateCities();
        constructTrainRoutes(trainRoutes, routeList, cities);

        // test for map
//        for (Map.Entry entry : trainRoutes.getTrainMap().entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
        // test for Q1 - Q5
//        String route1 = "A-B-C";
//        String dis1 = trainRoutes.calculcateDistance(route1);
//        System.out.print(dis1);

        // test for Q6
//        City start = new City("A");
//        City end = new City("C");
//
//        List<List<String>> res = trainRoutes.Q7(start, end, 4);
//        for (List<String> list : res) {
//            for (String s : list) {
//                System.out.print(s + " ");
//            }
//            System.out.print("\n");
//        }

//        // test for Q7
//        City start = new City("A");
//        City end = new City("C");
//
//        List<List<String>> res = trainRoutes.Q7(start, end, 4);
//        for (List<String> list : res) {
//            for (String s : list) {
//                System.out.print(s + " ");
//            }
//            System.out.print("\n");
//        }

        // test for Q8
//        City start = new City("E");
//        City end = new City("D");
//        int minDis = trainRoutes.Q8Dijkstra(start, end);
//        System.out.print(minDis);

        // test for Q9
//        City start = new City("B");
//        City end = new City("B");
//        int minDis = trainRoutes.Q9(start, end);
//        System.out.print(minDis);

        // test for Q10
        City start = new City("C");
        City end = new City("C");
        List<List<String>> res = trainRoutes.Q10(start, end, 30);
        for (List<String> list : res) {
            for (String s : list) {
                System.out.print(s + " ");
            }
            System.out.print("\n");
        }

    }

    /**
     * given the graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
     * generate the list which help construct the {@link TrainRoutes}
     * @return
     */
    private static List<Route> generateRoutes() {
        CityFactory cityFactory = new CityFactory();
        RouteFactory routeFactory = new RouteFactory();

        List<City> cities = new ArrayList<>();
        City A = cityFactory.createCity("A");
        City B = cityFactory.createCity("B");
        City C = cityFactory.createCity("C");
        City D = cityFactory.createCity("D");
        City E = cityFactory.createCity("E");
        cities.add(A);
        cities.add(B);
        cities.add(C);
        cities.add(D);
        cities.add(E);

        List<Route> routeList = new ArrayList<>();
        routeList.add(routeFactory.createRoute(A, B, 5));
        routeList.add(routeFactory.createRoute(B, C, 4));
        routeList.add(routeFactory.createRoute(C, D, 8));
        routeList.add(routeFactory.createRoute(D, C, 8));
        routeList.add(routeFactory.createRoute(D, E, 6));
        routeList.add(routeFactory.createRoute(A, D, 5));
        routeList.add(routeFactory.createRoute(C, E, 2));
        routeList.add(routeFactory.createRoute(E, B, 3));
        routeList.add(routeFactory.createRoute(A, E, 7));

        return routeList;
    }

    private static List<City> generateCities() {
        CityFactory cityFactory = new CityFactory();
        List<City> cities = new ArrayList<>();
        City A = cityFactory.createCity("A");
        City B = cityFactory.createCity("B");
        City C = cityFactory.createCity("C");
        City D = cityFactory.createCity("D");
        City E = cityFactory.createCity("E");
        cities.add(A);
        cities.add(B);
        cities.add(C);
        cities.add(D);
        cities.add(E);
        return cities;
    }

    private static void constructTrainRoutes(TrainRoutes trainRoutes, List<Route> routeList, List<City> cities) {
        for (Route route : routeList) {
            trainRoutes.addRoute(route);
        }

        for (City city : cities) {
            trainRoutes.addCity(city);
        }
    }
}
