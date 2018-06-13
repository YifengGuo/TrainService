package main.java;

import java.util.*;

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
        return hasException ?  "NO SUCH ROUTE" : String.valueOf(total);
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
            throw new IllegalArgumentException("city name not found");
        }

        if (maximum <= 0) {
            throw new IllegalArgumentException("maximum stops must be positive.");
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
     * Helper depth first method for {@link #Q6(City, City, int)}
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

    public List<List<String>> Q7(City start, City end, int exactStops) {
        List<List<String>> res = new ArrayList<>();
        // sanity check for city and maximum stops validation
        if (!cities.contains(start) || !cities.contains(end)) {
            throw new IllegalArgumentException("city name not found");
        }

        if (exactStops <= 0) {
            throw new IllegalArgumentException("exact stops must be positive.");
        }

        List<String> allRoutes = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : trainMap.entrySet()) {
            allRoutes.add(entry.getKey());
        }

        List<String> plan = new ArrayList<>();
        dfs2(res, plan, allRoutes, start.getName(), end.getName(), start.getName(), "", exactStops,0 );
        return res;
    }

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
     * The method is based on Dijkstra's greedy algorithm on the graph
     * @param start City start
     * @param end City end
     * @return the shortest distance between two cities
     */
    public int Q8Dijkstra(City start, City end) {
        List<List<String>> res = new ArrayList<>();
        // sanity check for city and maximum stops validation
        if (!cities.contains(start) || !cities.contains(end)) {
            throw new IllegalArgumentException("city name not found");
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
                if (distanceToStart.get(adjacentCity) > distanceToStart.get(cur) + intervalDistance) {
                    distanceToStart.put(adjacentCity, distanceToStart.get(cur) + intervalDistance);
                    pq.offer(adjacentCity);
                }
            }
        }
        return Integer.MAX_VALUE; // cannot find valid path
    }

    private List<String> getAdjacentCities(String start) {
        List<String> res = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : trainMap.entrySet()) {
            if (start.equals(String.valueOf(entry.getKey().charAt(0)))) {
                res.add(String.valueOf(entry.getKey().charAt(1)));
            }
        }
        return res;
    }
}
