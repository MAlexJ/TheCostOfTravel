package com.malex.service;

import com.alex.entity.MapCityEntity;
import com.alex.entity.vo.CityVO;
import com.alex.entity.vo.ListCityVO;
import com.alex.entity.vo.SearchByCities;
import com.alex.exception.AppException;
import com.alex.exception.IncorrectCostException;
import com.alex.service.FillCityService;
import com.alex.service.FindShortestPathService;
import com.alex.service.LoadFileService;
import com.alex.service.impl.FillCityServiceImpl;
import com.alex.service.impl.FindShortestPathServiceImpl;
import com.alex.service.impl.LoadFileServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by malex on 12.06.16.
 */
public class FindShortestPathServiceTest_4 {

    private FillCityService fillCityService;
    private LoadFileService loadFileService;
    private FindShortestPathService findShortestPathService;
    private MapCityEntity mapCityEntity;

    @Before
    public void init() {
        fillCityService = new FillCityServiceImpl();
        loadFileService = new LoadFileServiceImpl();
        findShortestPathService = new FindShortestPathServiceImpl();
        mapCityEntity = new MapCityEntity();
    }

    //List: two cities
    @Test
    public void test_01() {
        //given
        String str = loadFileService.loadFile("./test_15.txt");
        fillCityService.fillMapCity(str, mapCityEntity);

        ListCityVO listCity = new ListCityVO(mapCityEntity.getNumberTests (),
                mapCityEntity.getNumberCities ());
        fillCityService.addCityToList(listCity, mapCityEntity);
        int minCost = 0;

        //when
        for (SearchByCities city : mapCityEntity.getListSearch()) {
            this.findShortestPathService.reset();
            CityVO srcCity = new CityVO(city.getSource());
            CityVO descCity = new CityVO(city.getDestination());
            this.findShortestPathService.findPath(srcCity,
                    descCity,
                    listCity.getListCity(),
                    listCity.getExceptionListNameCity(),
                    listCity.getCountCity());
            minCost = findShortestPathService.findMinCost();
        }

        //then
        assertEquals(1, minCost);
    }

    //List: one city
    @Test(expected = AppException.class)
    public void test_02() {
        //given
        String str = loadFileService.loadFile("./test_16.txt");
        fillCityService.fillMapCity(str, mapCityEntity);

        ListCityVO listCity = new ListCityVO(mapCityEntity.getNumberTests (),
                mapCityEntity.getNumberCities ());
        fillCityService.addCityToList(listCity, mapCityEntity);
    }

    //List: gdansk bydgoszcz; gdansk torun; gdansk warszawa;
    @Test
    public void test_03() {
        //given
        String str = loadFileService.loadFile("./test_17.txt");
        fillCityService.fillMapCity(str, mapCityEntity);

        ListCityVO listCity = new ListCityVO(mapCityEntity.getNumberTests (),
                mapCityEntity.getNumberCities ());
        fillCityService.addCityToList(listCity, mapCityEntity);
        //Cost: gdansk bydgoszcz = 1$
        //Cost: gdansk torun = 2$
        //Cost: gdansk warszawa =3$
        int expectCost[] = new int[]{1, 2, 3};

        //when
        for (int i = 0; i < mapCityEntity.getListSearch().size(); i++) {
            this.findShortestPathService.reset();
            CityVO srcCity = new CityVO(mapCityEntity.getListSearch().get(i).getSource());
            CityVO descCity = new CityVO(mapCityEntity.getListSearch().get(i).getDestination());
            findShortestPathService.findPath(srcCity,
                    descCity,
                    listCity.getListCity(),
                    listCity.getExceptionListNameCity(),
                    listCity.getCountCity());
            int minCost = findShortestPathService.findMinCost();
            assertEquals(expectCost[i], minCost);
        }
    }

    //List: torun gdansk; torun bydgoszcz; torun warszawa
    @Test
    public void test_04() {
        //given
        String str = loadFileService.loadFile("./test_18.txt");
        fillCityService.fillMapCity(str, mapCityEntity);

        ListCityVO listCity = new ListCityVO(mapCityEntity.getNumberTests (),
                mapCityEntity.getNumberCities ());
        fillCityService.addCityToList(listCity, mapCityEntity);
        //Cost: torun gdansk = 2$
        //Cost: torun bydgoszcz = 2$
        //Cost: torun warszawa =3$
        int expectCost[] = new int[]{2, 1, 1};

        //when
        for (int i = 0; i < mapCityEntity.getListSearch().size(); i++) {
            findShortestPathService.reset();
            CityVO srcCity = new CityVO(mapCityEntity.getListSearch().get(i).getSource());
            CityVO descCity = new CityVO(mapCityEntity.getListSearch().get(i).getDestination());
            findShortestPathService.findPath(srcCity,
                    descCity,
                    listCity.getListCity(),
                    listCity.getExceptionListNameCity(),
                    listCity.getCountCity());
            int minCost = findShortestPathService.findMinCost();
            assertEquals(expectCost[i], minCost);
        }
    }

    //Value is less than zero
    @Test(expected = IncorrectCostException.class)
    public void test_05() {
        //given
        String str = loadFileService.loadFile("./test_19.txt");
        fillCityService.fillMapCity(str, mapCityEntity);

        ListCityVO listCity = new ListCityVO(mapCityEntity.getNumberTests (),
                mapCityEntity.getNumberCities ());
        fillCityService.addCityToList(listCity, mapCityEntity);
    }


}
