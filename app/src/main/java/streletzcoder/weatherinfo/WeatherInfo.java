package streletzcoder.weatherinfo;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;



import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import streletzcoder.weatherinfo.dataengine.AppDatabase;

/**
 * Created by Стрелец Coder on 04.12.2015.
 * Класс информации о погоде
 */
public class WeatherInfo  {
    //Дата
    private Date date;
    //Тмпература (день, ночь)
    private int dayTemp;
    private int nightTemp;
    //Описание погоды
    private String weatherDescription;




    public void setDate(Date d) {
        //Установка даты
        date = d;
    }

    public String getShortDate() {
        //Возврат даты
        // return date.toString();
        return getFormattedDate(date);
    }

    public void setTemp(double dayT, double nightT) {
        //Установка темпаратуры
        dayTemp = (int) Math.round(dayT);
        nightTemp = (int) Math.round(nightT);
    }

    public void setWeatherDescription(String description) {
        //Установка описания погоды
        weatherDescription = description;
    }

    public int getDayTemp() {
        //Возврат температуры днём
        return dayTemp;
    }

    public int getNightTemp() {
        //Возврат температуры ночью
        return nightTemp;
    }

    public String getWeatherDescription() {
        //Установка описания погоды
        return weatherDescription;
    }

    public String getShortInfo() {
        return getShortDate() + ": " + getDayTemp() + "..." + getNightTemp() + " " + getWeatherDescription();
    }

    /**
     * Данные о погоде без даты
     *
     * @return Строка сданными о погоде.
     */
    public String getShortInfoOnlyWeather() {
        return getDayTemp() + "..." + getNightTemp() + " " + getWeatherDescription();
    }

    private String getFormattedDate(Date date) {/*Форматированное значение даты*/
        String s = "";
        Locale local = new Locale("ru", "RU");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, local);
        s = df.format(date);
        return s;
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        database = Room.databaseBuilder(this.getApplicationContext(),
//                AppDatabase.class,
//                "database_name.db")
//                .openHelperFactory(new AssetSQLiteOpenHelperFactory())
//                .allowMainThreadQueries()
//                .build();
//    }
//
//    public AppDatabase getDatabase() {
//        return database;
//    }
}
