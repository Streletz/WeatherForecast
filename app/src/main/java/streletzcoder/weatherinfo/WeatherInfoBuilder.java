package streletzcoder.weatherinfo;

import java.util.Date;

import streletzcoder.weatherinfo.models.WeatherInfo;
import streletzcoder.weatherinfo.models.WeatherType;

/**
 * Created by Стрелец Coder on 04.12.2015.
 * Класс строитель для WeatherInfo
 */
public class WeatherInfoBuilder {
    public static final String CLEAR = "Clear";
    public static final String CLOUDS = "Clouds";
    public static final String RAIN = "Rain";
    public static final String SNOW = "Snow";
    private WeatherInfo wi;
    private final double absZero = -273.15;

    public void createNewWeatherInfo() {
        //Создаём "чистый" WeatherInfo
        wi = new WeatherInfo();
    }

    public void setDate(Long dt) {
        //Установка даты
        wi.setDate(new Date(dt * 1000));
    }

    public void setTemp(double dayT, double nigthT) {
        //Установка температуры
        wi.setTemp(dayT + absZero, nigthT + absZero);
    }

    public void setWeatherDescription(String weatherDescription) {
        //Установка описания погоды
        wi.setWeatherDescription(weatherDescription);
    }

    public void setWeatherType(String typeValue) {
        switch (typeValue) {
            case CLEAR:
                wi.setWeatherType(WeatherType.CLEAR);
                break;
            case CLOUDS:
                wi.setWeatherType(WeatherType.CLOUDS);
                break;
            case RAIN:
                wi.setWeatherType(WeatherType.RAIN);
                break;
            case SNOW:
                wi.setWeatherType(WeatherType.SNOW);
                break;
            default:
                wi.setWeatherType(WeatherType.UNKNOWN);
                break;
        }
    }

    public WeatherInfo getWeatherInfo() {
        //Возвращаем готовый WeatherInfo
        return wi;
    }
}
