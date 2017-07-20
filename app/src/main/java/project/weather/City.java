package project.weather;

/**
 * Created by Crystal on 11/14/2016.
 */

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Peter on 2015.07.01..
 */
public class City extends SugarRecord<City> implements Serializable {

    private String cityName;

    public City() {}

    public City(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}