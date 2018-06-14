package main.java;

/**
 * Created by guoyifeng on 6/12/18.
 */
public class City {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "";
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
        if (!(o instanceof City)) {
            return false;
        }
        City city = (City) o;
        return city.getName().equals(name);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getName().hashCode();
        return result;
    }
}
