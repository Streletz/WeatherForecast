package streletzcoder.weatherinfo;

import java.util.Date;

/**
 * Created by Стрелец Coder on 04.12.2015.
 * Класс строитель для WeatherInfo
 */
public class WeatherInfoBuilder {
    private WeatherInfo wi;
    private final double absZero=-273.15;
    public void createNewWeatherInfo()
    {
        //Создаём "чистый" WeatherInfo
        wi=new WeatherInfo();
    }
    public void setDate(Long dt)
    {
        //Установка даты
        wi.setDate(new Date(dt*1000));
    }
    public void setTemp(double dayT,double nigthT)
    {
        //Установка температуры
        wi.setTemp(dayT+absZero,nigthT+absZero);
    }

    public void setWeatherDescription(String weatherDescription)
    {
        //Установка описания погоды
        wi.setWeatherDescription(weatherDescription);
    }
    public WeatherInfo getWeatherInfo()
    {
        //Возвращаем готовый WeatherInfo
        return wi;
    }
}
