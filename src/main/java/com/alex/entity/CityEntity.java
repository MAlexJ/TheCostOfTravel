package com.alex.entity;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by malex on 07.06.16.
 */
public class CityEntity {
    private String name;
    private int numberNeighboursCity;
    private Map<Integer, Integer> indexOfCityTransportationCos t;

    public CityEntity() {
        this.indexOfCityTransportationCos t = new TreeMap<>();
    }

    public void addIndexOfCityTransportationCost(int city, int cost) {
        this.indexOfCityTransportationCos t.put(city, cost);
    }

    public Map<Integer, Integer> getIndexOfCityTransportationCos t() {
        return indexOfCityTransportationCos t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberNeighboursCity() {
        return numberNeighboursCity;
    }

    public void setNumberNeighboursCity(int numberNeighboursCity) {
        this.numberNeighboursCity = numberNeighboursCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity city = (CityEntity) o;
        if (numberNeighboursCity != city.numberNeighboursCity) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        return indexOfCityTransportationCos t != null ? indexOfCityTransportationCos t.equals(city.indexOfCityTransportationCos t) : city.indexOfCityTransportationCos t == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + numberNeighboursCity;
        result = 31 * result + (indexOfCityTransportationCos t != null ? indexOfCityTransportationCos t.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CityEntity{" +
                "name='" + name + '\'' +
                ", numberNeighboursCity=" + numberNeighboursCity +
                ", indexOfCityTransportationCos t=" + indexOfCityTransportationCos t +
                '}';
    }
}
