package edwang.weatherappjava.data;

import edwang.weatherappjava.adapter.CityRecyclerAdapter;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by student on 28/06/2017.
 */

public class CityWeatherRow extends RealmObject {
    @PrimaryKey
    private String id;
    private String cityName;
    private double temp;
    private String icon;

    public CityWeatherRow() {

    }

    public CityWeatherRow(String cityName, double temp, String icon) {
        this.cityName = cityName;
        this.temp = temp;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
