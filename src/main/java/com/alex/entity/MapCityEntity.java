package com.alex.entity;

import com.alex.entity.vo.ListCityVO;
import com.alex.entity.vo.SearchByCities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malex on 09.06.16.
 */
public class MapCityEntity {
    private int numberTests ;
    private int numberCities ;
    private List<CityEntity> list;
    private List<SearchByCities> listSearch;

    public MapCityEntity() {
        this.list = new ArrayList<>();
        this.listSearch = new ArrayList<>();
    }

    public void addCity(CityEntity city) {
        list.add(city);
    }

    public void addCityBySearch(SearchByCities city){
        listSearch.add(city);
    }

    public int getNumberTests () {
        return numberTests ;
    }

    public void setNumberTests (int numberTests ) {
        this.numberTests  = numberTests ;
    }

    public int getNumberCities () {
        return numberCities ;
    }

    public void setNumberCities (int numberCities ) {
        this.numberCities  = numberCities ;
    }

    public List<CityEntity> getList() {
        return list;
    }

    public List<SearchByCities> getListSearch() {
        return listSearch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapCityEntity that = (MapCityEntity) o;

        if (numberTests  != that.numberTests ) return false;
        if (numberCities  != that.numberCities ) return false;
        if (list != null ? !list.equals(that.list) : that.list != null) return false;
        return listSearch != null ? listSearch.equals(that.listSearch) : that.listSearch == null;

    }

    @Override
    public int hashCode() {
        int result = numberTests ;
        result = 31 * result + numberCities ;
        result = 31 * result + (list != null ? list.hashCode() : 0);
        result = 31 * result + (listSearch != null ? listSearch.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MapCityEntity{" +
                "numberTests =" + numberTests  +
                ", numberCities =" + numberCities  +
                ", list=" + list +
                ", listSearch=" + listSearch +
                '}';
    }
}
