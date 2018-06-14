package main.java;

/**
 * Created by guoyifeng on 6/12/18.
 */
public class Route {
    private City startCity;
    private City endCity;
    private int distance;

    public Route(City startCity, City endCity, int distance) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.distance = distance;
    }

    public City getStartCity() {
        return startCity;
    }

    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }

    public City getEndCity() {
        return endCity;
    }

    public void setEndCity(City endCity) {
        this.endCity = endCity;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route{" +
                "startCity=" + startCity +
                ", endCity=" + endCity +
                ", distance=" + distance +
                '}';
    }

    /**
     * override equals() and hashCode() for City because there are use cases
     * we need to compare different City objects with same name. Under that case,
     * we think both cities are pointing to one city
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Route)) {
            return false;
        }
        Route route = (Route) o;
        return route.getStartCity().equals(startCity)
                && route.getEndCity().equals(endCity)
                && route.getDistance() == distance;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getStartCity().hashCode();
        result = 31 * result + this.getEndCity().hashCode();
        result = 31 * result + this.getDistance();
        return result;
    }
}
