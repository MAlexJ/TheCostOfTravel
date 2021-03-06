package com.alex.service.impl;

import com.alex.entity.CityEntity;
import com.alex.entity.MapCityEntity;
import com.alex.entity.vo.CityVO;
import com.alex.entity.vo.ListCityVO;
import com.alex.entity.vo.SearchByCities;
import com.alex.exception.*;
import com.alex.service.FillCityService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by malex on 07.06.16.
 */
public class FillCityServiceImpl implements FillCityService {

    // Search by city name
    @Override
    public CityEntity fillCity(String str) {
        CityEntity city = new CityEntity();
        String[] split = listString(str);
        String name = split[0];
        if (validateNameCity(name)) {
            city.setName(name);
        }
        int count = validateNumberCity(split[1]);
        city.setNumberNeighboursCity(count);
        int position = 2;
        while (count >= 0) {
            int indexCity = validateNumberCity(split[position]);
            int costCity = validateNumberCity(split[position + 1]);
            city.addIndexOfCityTransportationCost(indexCity, costCity);
            position++;
            count--;
        }
        return city;
    }

    @Override
    public void fillMapCity(String str, MapCityEntity mapCityEntity) {
        String[] split = listString(str);
        int numberTest = validateNumberCity(split[0]);
        if (numberTest <= 10) {
            mapCityEntity.setNumberTests (numberTest);
        } else {
            throw new IncorrectNumberTestsException("Incorrect number test: " + numberTest);
        }
        int numberCity = validateNumberCity(split[1]);
        if (numberCity <= 10000) {
            mapCityEntity.setNumberCities (numberCity);
        } else {
            throw new IncorrectNumberCityException("Incorrect number city: " + numberCity);
        }
        String[] resultString = new String[split.length - 2];
        System.arraycopy(split, 2, resultString, 0, resultString.length);
        int countCity = mapCityEntity.getNumberCities ();
        findCity(resultString, mapCityEntity, countCity);

    }

    @Override
    public void addCityToList(ListCityVO listCity, MapCityEntity mapCityEntity) {
        String[] name = new String[mapCityEntity.getNumberCities ()];
        List<CityEntity> list = mapCityEntity.getList();
        for (int i = 0; i < name.length; i++) {
            name[i] = list.get(i).getName();
        }
        for (String item : name) {
            CityVO city = new CityVO();
            city.setName(item);

            CityEntity cityEntity = getCityEntity(item, list);
            Map<Integer, Integer> map = cityEntity.getIndexOfCityTransportationCost();

            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                String nameCity = getNameCity(entry.getKey(), name);
                city.addToMap(new CityVO(nameCity), entry.getValue());
            }
            listCity.addToListCity(city);
        }
    }

    //Get CityEntity
    private CityEntity getCityEntity(String item, List<CityEntity> list) {
        for (CityEntity city : list) {
            if (city.getName().equals(item)) {
                return city;
            }
        }
        throw new ListCityIsEmptyException("City not found!");
    }

    //Get name
    private String getNameCity(Integer id, String[] name) {
        try {
            return name[id - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AppException("The list contains one city!");
        }

    }

    private void findCity(String[] listData, MapCityEntity mapCityEntity, int countCity) {
        CityEntity city = new CityEntity();
        String nameCity = listData[0];

        if (isString(nameCity)) {
            city.setName(nameCity);
        } else {
            throw new IncorrectNameCityException("Invalid city name: " + nameCity);
        }
        city.setNumberNeighboursCity(validateNumberCity(listData[1]));
        String[] resultList = new String[0];
        LinkedList<Integer> listInt = new LinkedList<>();
        for (int i = 2; i < listData.length; i++) {
            if (isString(listData[i])) {
                resultList = new String[listData.length - i];
                System.arraycopy(listData, i, resultList, 0, resultList.length);
                break;
            }
            listInt.add(validateNumberCity(listData[i]));
        }
        if (validateList(listInt)) {
            listInt.removeLast();
        }
        fillListCity(listInt, city);
        mapCityEntity.addCity(city);
        if (countCity > 1) {
            findCity(resultList, mapCityEntity, --countCity);
        } else {
            LinkedList<String> citesBySearch = new LinkedList<>(Arrays.asList(resultList));
            fillListCityBySearch(citesBySearch, mapCityEntity);
        }
    }

    private void fillListCityBySearch(LinkedList<String> cites, MapCityEntity mapCityEntity) {
        if (!cites.isEmpty()) {
            String src = cites.getFirst();
            cites.removeFirst();
            String dest = cites.getFirst();
            if (validateNameCity(dest)) {
                cites.removeFirst();
            }
            SearchByCities searchByCities = new SearchByCities(src, dest);
            mapCityEntity.addCityBySearch(searchByCities);
            fillListCityBySearch(cites, mapCityEntity);
        }
    }

    private void fillListCity(LinkedList<Integer> listItem, CityEntity city) {
        if (!listItem.isEmpty()) {
            int cityNeMame = listItem.getFirst();
            listItem.removeFirst();
            int cityNeCost = listItem.getFirst();
            if (cityNeCost < 0) {
                throw new IncorrectCostException("Value is less than zero: " + cityNeCost);
            }
            listItem.removeFirst();
            Map<Integer, Integer> map = city.getIndexOfCityTransportationCost();
            map.put(cityNeMame, cityNeCost);
            fillListCity(listItem, city);
        }
    }

    private boolean validateList(List<Integer> listInt) {
        return listInt.size() % 2 != 0;
    }

    // The name of a city is a string containing characters a,...,z and is at most 10 characters long.     
    private boolean validateNameCity(String name) {
        Pattern pt = Pattern.compile("[a-z]{0,10}");
        Matcher mt = pt.matcher(name);
        if (mt.matches() && name.length() <= 10) {
            return true;
        }
        throw new IncorrectNameCityException("Invalid city name: " + name);
    }

    private int validateNumberCity(String num) {
        try {
            int parseInt = Integer.parseInt(num);
            if (parseInt > 200000) {
                throw new IncorrectCostException("Limit is exceeded cost of the trip!");
            }
            return parseInt;
        } catch (NumberFormatException e) {
            throw new IncorrectNumberCityException("Invalid index city or cost: " + num);
        }
    }

    private boolean isString(String num) {
        Pattern pt = Pattern.compile("[a-z]{0,10}");
        Matcher mt = pt.matcher(num);
        return mt.matches();
    }

    private String[] listString(String str) {
        if (str == null || str.equals("")) {
            throw new AppException("Invalid city!");
        }
        return str.split(" ");
    }
}
