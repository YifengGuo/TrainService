package main.java;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by guoyifeng on 6/12/18.
 */
@SuppressWarnings("Duplicates")
public class TrainRoutes {
    private Map<String, Integer> trainMap;
    private Set<City> cities;

    public TrainRoutes() {
        trainMap = new HashMap<>();
        cities = new HashSet<>();
    }

    public Map<String, Integer> getTrainMap() {
        return trainMap;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void addRoute(Route route) {
        trainMap.put(route.getStartCity().getName() + "" + route.getEndCity().getName(), route.getDistance());
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    /**
     *
     * @param route route request whose format is like A-B-C
     * @return the total distance accumulated by each two-stop distance
     */
    public String calculcateDistance(String route) {
        // check input format validation
        try {
            if (!route.matches("^(\\w\\-)+\\w$")) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("For Route "+ route +" please input route in format like X-X-X");
        }
        int total = 0;
        boolean hasException = false;
        List<String> twoGramRoutes = getTwoGram(route);
        for (String twoGramRoute : twoGramRoutes) {
            if (!trainMap.containsKey(twoGramRoute)) {
                try {
                    throw new NoSuchRouteException("NO SUCH ROUTE");
                } catch (NoSuchRouteException e) {
                    hasException = true;
                }
            } else {
                total += trainMap.get(twoGramRoute);
            }
        }
        return hasException ? "NO SUCH ROUTE" : String.valueOf(total);
    }

    /**
     * use n-gram algorithm to get the two for the given route
     * which is to divide the route A-B-C into AB, BC
     * @return the list contains all two grams (two-stop routes)
     */
    private List<String> getTwoGram(String route) {
        List<String> res = new ArrayList<>();
        final int noGram = 2;
        String formatedRoute = route.trim().replaceAll("-", ""); // remove all "-" dash in the route

        for (int i = 0; i < formatedRoute.length(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(formatedRoute.charAt(i));
            for (int j = 1; i + j < formatedRoute.length() && j < noGram; j++) {
                sb.append(formatedRoute.charAt(i + j));
                res.add(sb.toString());
            }
        }
        return res;
    }

    /**
     * This method is a general implementation for Question 6
     * @param start start City
     * @param end end City
     * @param maximum maximum stops
     * @return The list which contains all possible routes given the constraints <br>
     *         For example:                        <br>
     *                      given  C C 3           <br>
     *                      return:                <br>
     *                             C D C           <br>
     *                             C E B C         <br>
     *
     *                      given  A C 3           <br>
     *                      return:                <br>
     *                             A B C           <br>
     *                             A D C           <br>
     *                             A E B C         <br>
     *
     */
    public List<List<String>> Q6(City start, City end, int maximum) {
        List<List<String>> res = new ArrayList<>();
        // sanity check for city and maximum stops validation
        if (!cities.contains(start) || !cities.contains(end)) {
            throw new IllegalArgumentException("City name not found");
        }

        if (maximum <= 0) {
            throw new IllegalArgumentException("Maximum stops must be positive.");
        }
        List<String> allRoutes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : trainMap.entrySet()) {
            allRoutes.add(entry.getKey());
        }

        List<String> plan = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfs1(res, plan, allRoutes, start.getName(), end.getName(), start.getName(), "", maximum, visited);
        return res;
    }

    /**
     * Helper depth first search algorithm for {@link #Q6(City, City, int)}
     * @param res result list
     * @param plan list of String for current depth first recursive search
     * @param allRoutes all the routes in this system
     * @param start request start city name
     * @param end request end city name
     * @param cur current traversed city
     * @param prev previous traversed city
     * @param maximum request maximum stops
     * @param visited to deduplicate traversed route
     */
    private void dfs1(List<List<String>> res, List<String> plan, List<String> allRoutes,
                      String start, String end, String cur, String prev, int maximum, Set<String> visited) {
        // base case
        if (cur.equals(end) && plan.size() <= maximum && plan.size() > 0) {
            plan.add(end);
            res.add(new ArrayList<>(plan));
            plan.remove(plan.size() - 1);
            return;
        }

        for (int i = 0; i < allRoutes.size(); i++) {
            if (cur.equals(String.valueOf(allRoutes.get(i).charAt(0))) && !visited.contains(allRoutes.get(i))) {
                plan.add(cur);
                prev = cur;
                cur = String.valueOf(allRoutes.get(i).charAt(1));
                visited.add(allRoutes.get(i));
                dfs1(res, plan, allRoutes, start, end, cur, prev, maximum, visited); // dfs on search space
                // backtracking all necessary fields
                plan.remove(plan.size() - 1);
                visited.remove(allRoutes.get(i));
                cur = prev;
            }
        }
    }

    /**
     * This method is a general implementation for Question 7
     * @param start start City
     * @param end end City
     * @param exactStops exact count of requested stops
     * @return The list which contains all possible routes given the constraints <br>
     */
    public List<List<String>> Q7(City start, City end, int exactStops) {
        List<List<String>> res = new ArrayList<>();
        // sanity check for city and maximum stops validation
        if (!cities.contains(start) || !cities.contains(end)) {
            throw new IllegalArgumentException("City name not found");
        }

        if (exactStops <= 0) {
            throw new IllegalArgumentException("Exact stops must be positive.");
        }

        List<String> allRoutes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : trainMap.entrySet()) {
            allRoutes.add(entry.getKey());
        }

        List<String> plan = new ArrayList<>();
        dfs2(res, plan, allRoutes, start.getName(), end.getName(), start.getName(), "", exactStops,0 );
        return res;
    }

    /**
     * helper depth first search algorithm for {@link #Q7(City, City, int)}
     * @param res result list which contains every valid route info
     * @param plan list of String for current depth first recursive search
     * @param allRoutes all the routes in this system
     * @param start start City
     * @param end end City
     * @param cur current traversed city
     * @param prev previous traversed city
     * @param exactStops exact count of requested stops
     * @param level the depth of recursion tree, use it instead of visited to make dfs not stop too early
     */
    private void dfs2(List<List<String>> res, List<String> plan, List<String> allRoutes, String start,
                      String end, String cur, String prev, int exactStops, int level) {
        // base case
        if (cur.equals(end) && plan.size() == exactStops) {
            plan.add(end);
            res.add(new ArrayList<>(plan));
            plan.remove(plan.size() - 1);
            return;
        }

        for (int i = 0; i < allRoutes.size(); i++) {
            if (cur.equals(String.valueOf(allRoutes.get(i).charAt(0))) && level <= exactStops) {
                plan.add(cur);
                prev = cur;
                cur = String.valueOf(allRoutes.get(i).charAt(1));
                dfs2(res, plan, allRoutes, start, end, cur, prev, exactStops, level + 1); // dfs on search space
                // backtracking all necessary fields
                plan.remove(plan.size() - 1);
                cur = prev;
            }
        }
    }

    /**
     * This method is a general implementation for Question 8. Input two City object and return the
     * shortest distance between it
     * The method is based on Dijkstra's greedy path finding algorithm on the graph
     * @param start City start
     * @param end City end
     * @return the shortest distance between two cities
     */
    public int Q8Dijkstra(City start, City end) {
        List<List<String>> res = new ArrayList<>();
        // sanity check for city and maximum stops validation
        if (!cities.contains(start) || !cities.contains(end)) {
            throw new IllegalArgumentException("City name not found");
        }

        if (start.equals(end)) {
            throw new IllegalArgumentException("Q8() cannot receive one city, invoke Q9() instead.");
        }
        // initialize all cities' distance to start city as Integer.MAX_VALUE
        Map<String, Integer> distanceToStart = new HashMap<>();
        for (City city : cities) {
            if (city.equals(start)) {
                distanceToStart.put(start.getName(), 0);
            } else {
                distanceToStart.put(city.getName(), Integer.MAX_VALUE);
            }
        }

        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.offer(start.getName()); // generate initial start
        while (!pq.isEmpty()) {
            String cur = pq.poll();
            // base case
            if (cur.equals(end.getName())) {
                return distanceToStart.get(cur);
            }
            // get adjacent cities
            List<String> adjacentCities = getAdjacentCities(cur);
            // expansion process
            for (String adjacentCity : adjacentCities) {
                // get real distance from trainMap if it exists a valid route between two cities
                int intervalDistance = trainMap.get(cur + "" + adjacentCity) != null ?
                        trainMap.get(cur + "" + adjacentCity) : Integer.MAX_VALUE / 2; // avoid numeric overflow
                // System.out.println(intervalDistance);
                // update shorter path info for current adjacent city if possible
                if (distanceToStart.get(adjacentCity) > distanceToStart.get(cur) + intervalDistance) {
                    distanceToStart.put(adjacentCity, distanceToStart.get(cur) + intervalDistance);
                    pq.offer(adjacentCity); // this city will have priority to be traversed on next iteration
                }
            }
        }
        return Integer.MAX_VALUE; // cannot find valid path
    }

    /**
     * helper method for find all adjacent cities of the given city
     * @param start
     * @return
     */
    private List<String> getAdjacentCities(String start) {
        List<String> res = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : trainMap.entrySet()) {
            if (start.equals(String.valueOf(entry.getKey().charAt(0)))) {
                res.add(String.valueOf(entry.getKey().charAt(1)));
            }
        }
        return res;
    }

    /**
     * This method is a general implementation for Question 9. Input two identical City object and return
     * the shortest distance to get back to the original city. <br>
     * This method is kind of complement for the Dijkstra's algorithm which cannot work when input cities are pointing
     * to the same one. <br>
     * @param start start city
     * @param end identical end city
     * @return shortest distance to get back. If impossible to get back, return Integer.MAX_VALUE
     */
    public int Q9(City start, City end) {
        // sanity check for city and maximum stops validation
        if (!cities.contains(start) || !cities.contains(end)) {
            throw new IllegalArgumentException("City name not found");
        }
        Set<String> visited = new HashSet<>();
        List<String> plan = new ArrayList<>();
        int[] minDis = new int[1];
        minDis[0] = Integer.MAX_VALUE; // initialize minimal distance
        boolean secondVisit = false;
        dfs3(plan, end.getName(), visited, minDis, start.getName(),0, secondVisit);
        return minDis[0];
    }

    /**
     * helper depth first search algorithm for {@link #Q9(City, City)}
     * @param plan path info
     * @param end end city name
     * @param visited deduplicate visited entry
     * @param minDis a one-size array to store shortest path value
     * @param cur current traversed city
     * @param dis current path's distance
     * @param secondVisit to guarantee first time the dfs would not stop
     */
    private void dfs3(List<String> plan, String end, Set<String> visited,
                      int[] minDis, String cur,int dis, boolean secondVisit) {
        // base case
        if (cur.equals(end) && minDis[0] >= dis && secondVisit) {
            minDis[0] = dis;
            return;
        }
        secondVisit = true;
        List<String> adjacentCities = getAdjacentCities(cur);
        for (String adjacentCity : adjacentCities) {
            if (!visited.contains(adjacentCity)) {
                visited.add(adjacentCity);
                plan.add(cur);
                dis += trainMap.get(cur + "" + adjacentCity);
                dfs3(plan, end, visited, minDis, adjacentCity, dis, secondVisit);
                dis -= trainMap.get(cur + "" + adjacentCity);
                plan.remove(plan.size() - 1);
                visited.remove(adjacentCity);
            }
        }
    }

    /**
     * This method is a general implementation for Question 10. Input two identical City object and a maximum distance
     * limit, return all the possible routes <br>
     * @param start start City
     * @param end end City
     * @param maxDistance requested maximum distance
     * @return all the possible routes which satisfy the constraints
     */
    public List<List<String>> Q10(City start, City end, int maxDistance) {
        List<List<String>> res = new ArrayList<>();
        // sanity check for city and maximum stops validation
        if (!cities.contains(start) || !cities.contains(end)) {
            throw new IllegalArgumentException("City name not found");
        }
        if (maxDistance <= 0) {
            throw new IllegalArgumentException("Max distance must be positive.");
        }
        List<String> plan = new ArrayList<>();

        dfs4(res, plan, end.getName(), start.getName(), 0, maxDistance);
        return res;
    }

    /**
     * helper depth first search algorithm for {@link #Q10(City, City, int)}
     * @param res result list
     * @param plan list of String for current depth first recursive search
     * @param end end City
     * @param cur current traversed city
     * @param dis current path's distance
     * @param maxDistance requested maximum distance
     */
    private void dfs4(List<List<String>> res, List<String> plan, String end, String cur, int dis, int maxDistance) {
        // base case
        if (plan.size() > 1 && cur.equals(end) && dis < maxDistance) {
            plan.add(cur);
            res.add(new ArrayList<>(plan));
            plan.remove(plan.size() - 1);
            //return; // do not return to make dfs continue
        }

        List<String> adjacentCities = getAdjacentCities(cur);
        for (String adjacentCity : adjacentCities) {
            if (dis < maxDistance) {
                plan.add(cur);
                dfs4(res, plan, end, adjacentCity, dis + trainMap.get(cur + "" + adjacentCity), maxDistance);
                plan.remove(plan.size() - 1);
            }
        }
    }

    // helper function for printing the result list
    public void printListOfList(List<List<String>> res) {
        System.out.println("All possible routes for this question input are: ");
        for (List<String> list : res) {
            for (String s : list) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    // get the count of result routes in the result list
    public int getCountOfRoutes(List<List<String>> res) {
        return res.size();
    }
}
