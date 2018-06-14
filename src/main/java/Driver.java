package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoyifeng on 6/12/18.
 */
public class Driver {
    public static void main(String[] args) {
        // initialize TrainRoutes
        TrainRoutes trainRoutes = new TrainRoutes();
        List<Route> routeList = generateRoutes();
        List<City> cities = generateCities();
        constructTrainRoutes(trainRoutes, routeList, cities);

        // test for map initialization
//        for (Map.Entry entry : trainRoutes.getTrainMap().entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

        // test for Q1 - Q5
        System.out.println("*********************** Question 1 - 5 ***********************\n");
        String route1 = "A-B-C";
        String route2 = "A-D";
        String route3 = "A-D-C";
        String route4 = "A-E-B-C-D";
        String route5 = "A-E-D";
        // test for invalid route format
        String route6 = "A-";
        String route7 = "AA";
        String route8 = "A--A";
        String route9 = "AA--";
        String route10 = "-A";
        String dis1 = trainRoutes.calculcateDistance(route1);
        String dis2 = trainRoutes.calculcateDistance(route2);
        String dis3 = trainRoutes.calculcateDistance(route3);
        String dis4 = trainRoutes.calculcateDistance(route4);
        String dis5 = trainRoutes.calculcateDistance(route5);
        String dis6 = trainRoutes.calculcateDistance(route6);
        String dis7 = trainRoutes.calculcateDistance(route7);
        String dis8 = trainRoutes.calculcateDistance(route8);
        String dis9 = trainRoutes.calculcateDistance(route9);
        String dis10 = trainRoutes.calculcateDistance(route10);
        System.out.println("The distance of the route " + route1 + " is " + dis1);
        System.out.println("The distance of the route " + route2 + " is " + dis2);
        System.out.println("The distance of the route " + route3 + " is " + dis3);
        System.out.println("The distance of the route " + route4 + " is " + dis4);
        System.out.println("The distance of the route " + route5 + " is " + dis5);
        System.out.println("\n*********************** Question 1 - 5 ***********************\n");

        // For convenience, here I will create all City objects in this graph once
        CityFactory cityFactory = new CityFactory();
        City A = cityFactory.createCity("A");
        City B = cityFactory.createCity("B");
        City C = cityFactory.createCity("C");
        City D = cityFactory.createCity("D");
        City E = cityFactory.createCity("E");

        // test for Q6
        System.out.println("*********************** Question 6 ***********************\n");

        // test case 1 for Q6
        City start_Q6 = C;
        City end_Q6 = C;
        int maximumStop_Q6 = 3;
        List<List<String>> res_Q6 = trainRoutes.Q6(start_Q6, end_Q6, maximumStop_Q6);
        System.out.println("The number of trips starting at "+ start_Q6 +" and ending at " + end_Q6 +
                " with a maximum of " + maximumStop_Q6 + " stops is " + trainRoutes.getCountOfRoutes(res_Q6));
        trainRoutes.printListOfList(res_Q6);

        // test case 2 for Q6
        System.out.println();
        start_Q6 = A;
        end_Q6 = E;
        maximumStop_Q6 = 4;
        res_Q6 = trainRoutes.Q6(start_Q6, end_Q6, maximumStop_Q6);
        System.out.println("The number of trips starting at "+ start_Q6 +" and ending at " + end_Q6 +
                " with a maximum of " + maximumStop_Q6 + " stops is " + trainRoutes.getCountOfRoutes(res_Q6));
        trainRoutes.printListOfList(res_Q6);

        // test case 3 for Q6: non-existed city name will throw IllegalArgumentException
        System.out.println();
        start_Q6 = new City("K"); // non-existed city name
        end_Q6 = E;
        maximumStop_Q6 = 4;
        res_Q6 = trainRoutes.Q6(start_Q6, end_Q6, maximumStop_Q6);
        System.out.println("The number of trips starting at "+ start_Q6 +" and ending at " + end_Q6 +
                " with a maximum of " + maximumStop_Q6 + " stops is " + trainRoutes.getCountOfRoutes(res_Q6));
        trainRoutes.printListOfList(res_Q6);

         // test case 4 for Q6: negative max maximum stops will throw IllegalArgumentException
        System.out.println();
        start_Q6 = A;
        end_Q6 = E;
        maximumStop_Q6 = -1; // negative invalid maximum stops
        res_Q6 = trainRoutes.Q6(start_Q6, end_Q6, maximumStop_Q6);
        System.out.println("The number of trips starting at "+ start_Q6 +" and ending at " + end_Q6 +
                " with a maximum of " + maximumStop_Q6 + " stops is " + trainRoutes.getCountOfRoutes(res_Q6));
        trainRoutes.printListOfList(res_Q6);
        System.out.println("\n*********************** Question 6 ***********************\n");

//        // test for Q7
        System.out.println("*********************** Question 7 ***********************\n");
        // test case 1 for Q7
        City start_Q7 = A;
        City end_Q7 = C;
        int exactStops_Q7 = 4;
        List<List<String>> res_Q7 = trainRoutes.Q7(start_Q7, end_Q7, exactStops_Q7);
        System.out.println("The number of trips starting at "+ start_Q7 +" and ending at " + end_Q7 +
                " with exact " + exactStops_Q7 + " stops is " + trainRoutes.getCountOfRoutes(res_Q7));
        trainRoutes.printListOfList(res_Q7);

        // test case 2 for Q7
        System.out.println();
        start_Q7 = B; // no such route can satisfy that with one stop from B to E
        end_Q7 = E;
        exactStops_Q7 = 1;
        res_Q7 = trainRoutes.Q7(start_Q7, end_Q7, exactStops_Q7);
        System.out.println("The number of trips starting at "+ start_Q7 +" and ending at " + end_Q7 +
                " with exact " + exactStops_Q7 + " stops is " + trainRoutes.getCountOfRoutes(res_Q7));
        trainRoutes.printListOfList(res_Q7);
        System.out.println("\n*********************** Question 7 ***********************\n");

        // test for Q8
        System.out.println("*********************** Question 8 ***********************\n");
        // test case 1 for Q8
        City start_Q8 = A;
        City end_Q8 = C;
        int minDis_Q8 = trainRoutes.Q8Dijkstra(start_Q8, end_Q8);
        System.out.println("The length of the shortest route (in terms of distance to travel) from " +
                start_Q8 + " to " +  end_Q8 + " is " + minDis_Q8);

        // test case 2 for Q8
        start_Q8 = E;
        end_Q8 = D;
        minDis_Q8 = trainRoutes.Q8Dijkstra(start_Q8, end_Q8);
        System.out.println("The length of the shortest route (in terms of distance to travel) from " +
                start_Q8 + " to " +  end_Q8 + " is " + minDis_Q8);

        // test case 3 for Q8: if input two cities are same one which cannot be handled by Dijkstra's algorithm
        start_Q8 = E;
        end_Q8 = E;
        minDis_Q8 = trainRoutes.Q8Dijkstra(start_Q8, end_Q8);
        System.out.println("The length of the shortest route (in terms of distance to travel) from " +
                start_Q8 + " to " +  end_Q8 + " is " + minDis_Q8);
        System.out.println("\n*********************** Question 8 ***********************\n");

        System.out.println("*********************** Question 9 ***********************\n");
        // test case 1 for Q9
        City start_Q9 = new City("B");
        City end_Q9 = new City("B");
        int minDis_Q9 = trainRoutes.Q9(start_Q9, end_Q9);
        System.out.println("The length of the shortest route (in terms of distance to travel) from " +
                start_Q9 + " to " +  end_Q9 + " is " + minDis_Q9);

        // test case 2 for Q9
        System.out.println();
        start_Q9 = new City("A");
        end_Q9 = new City("C");
        minDis_Q9 = trainRoutes.Q9(start_Q9, end_Q9);
        System.out.println("The length of the shortest route (in terms of distance to travel) from " +
                start_Q9 + " to " +  end_Q9 + " is " + minDis_Q9);
        System.out.println("\n*********************** Question 9 ***********************\n");

        System.out.println("*********************** Question 10 ***********************\n");
        // test case 1 for Q10
        City start_Q10 = C;
        City end_Q10 = C;
        int maxDistance_Q10 = 30;
        List<List<String>> res_Q10 = trainRoutes.Q10(start_Q10, end_Q10, maxDistance_Q10);
        System.out.println("The number of different routes from " + start_Q10 + " to " + end_Q10 +
                " with a distance of less than "+ maxDistance_Q10 + " is " + trainRoutes.getCountOfRoutes(res_Q10));
        trainRoutes.printListOfList(res_Q10);

        // test case 2 for Q10
        System.out.println();
        start_Q10 = A;
        end_Q10 = D;
        maxDistance_Q10 = 60;
        res_Q10 = trainRoutes.Q10(start_Q10, end_Q10, maxDistance_Q10);
        System.out.println("The number of different routes from " + start_Q10 + " to " + end_Q10 +
                " with a distance of less than "+ maxDistance_Q10 + " is " + trainRoutes.getCountOfRoutes(res_Q10));
        trainRoutes.printListOfList(res_Q10);

        System.out.println("\n*********************** Question 10 ***********************\n");

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

    /**
     * generate the city list which help construct the {@link TrainRoutes}
     * @return the list of City in this graph
     */
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

    /**
     * put all the routes and cities of given graph into the TrainRoutes object
     * @param trainRoutes
     * @param routeList
     * @param cities
     */
    private static void constructTrainRoutes(TrainRoutes trainRoutes, List<Route> routeList, List<City> cities) {
        for (Route route : routeList) {
            trainRoutes.addRoute(route);
        }

        for (City city : cities) {
            trainRoutes.addCity(city);
        }
    }
}
