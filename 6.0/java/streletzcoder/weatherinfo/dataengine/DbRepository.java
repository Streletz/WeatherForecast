package streletzcoder.weatherinfo.dataengine;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

import streletzcoder.weatherinfo.R;

/**
 * Класс репозиторий для работы с данными из БД
 */
public class DbRepository {

    private DbHelper helper;
    private SQLiteDatabase db;
    private Context cont;

    public DbRepository(Context context) {
        //Подключение к базе данных
        DbHelper helper = new DbHelper(context);
        try {
            helper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Не возможно инициализировать базу данных");
        }
        try {
            helper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        db = helper.getWritableDatabase();
        cont = context;
    }

    /**
     * Получение данных о городах
     *
     * @param where Условие отбора
     * @param field Поле отбора
     * @return Список данных о городах
     */
    public ArrayList<String> getData(String where, CityCodeFields field) {
        //Вывод записей с отбором (where!=null) и без
        ArrayList<String> list = new ArrayList<String>();
        String where1 = null;
        if (where != null) {
            where1 = where + " AND CountryId=" + String.valueOf(getSelectedCountryId());
        } else {
            where1 = "CountryId=" + String.valueOf(getSelectedCountryId());
        }
        Cursor cursor = db.query("CityCodes", null, where1, null, null, null, "City");
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(cursor.getString(field.getFieldCode()));
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * Получаем информацию о странах
     *
     * @param where Условие отбора
     * @param field Поле отбора
     * @return Список данных по странам
     */
    public ArrayList<String> getCountryData(String where, CountryFields field) {
        //Вывод записей с отбором (where!=null) и без
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query("Country", null, where, null, null, null, "Country");
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(cursor.getString(field.getFieldCode()));
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * Получаем id выбранного города
     *
     * @return
     */
    private int getSelectedCityId() {
        //Вывод id выбранного города
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query("CitySelected", null, null, null, null, null, null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return Integer.parseInt(list.get(0));
    }

    /**
     * Получаем id выбранной страны
     *
     * @return
     */
    private int getSelectedCountryId() {
        //Вывод id выбранной страны
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query("CountrySelected", null, null, null, null, null, null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return Integer.parseInt(list.get(0));
    }

    /**
     * Позиция выбранного страны для spinner'а
     *
     * @return
     */
    public int getSelectedCountryPosition() {
        return getSelectedCountryId() - 1;
    }

    /**
     * Возвращает код города для запроса на OpenWeatherMap
     *
     * @return
     */
    private String getSelectedCityCode() {
        ArrayList<String> list = getData("_id='" + String.valueOf(getSelectedCityId()) + "'", CityCodeFields.CODE);
        return list.get(0);
    }

    /**
     * Возвращает название города для отображения в виджете
     *
     * @return
     */
    public String getSelectedCityName() {
        ArrayList<String> list = getData("_id='" + String.valueOf(getSelectedCityId()) + "'", CityCodeFields.CITY);
        if (list.size() != 0) {
            //Если для выбранной страны есть выбранный город, возвращаем его
            return list.get(0);
        } else {
            //Иначе возвращаем первый город данной страны из общего списка
            list = getData(null, CityCodeFields.CITY);
            return list.get(0);
        }
    }

    /**
     * Возвращает строку запрос на OpenWeatherMap
     *
     * @return
     */
    public String getDataRequestString() {

        return cont.getString(R.string.dataUrl) + getSelectedCityCode() + cont.getString(R.string.key);
    }

    /**
     * Сохранение выбранного города
     *
     * @param cityName Название города
     */
    public void setSelectedCity(String cityName) {
        setSelectedCity(getCityId(cityName));
    }

    /**
     * Сохранение выбранного города
     *
     * @param cityId id города
     */
    public void setSelectedCity(int cityId) {
        db.execSQL("UPDATE CitySelected SET CityId=" + cityId);
    }

    /**
     * Сохранение выбранной страны
     *
     * @param spinnerPosition Позиция страны в раскрывающемся списке
     */
    public void setSelectedCountry(int spinnerPosition) {
        //Сохраняем выбранный город
        String cityId = String.valueOf(spinnerPosition + 1);
        db.execSQL("UPDATE CountrySelected SET CountryId=" + cityId);
    }

    /**
     * Получаем id выбранного города
     *
     * @param cityName
     * @return
     */
    private int getCityId(String cityName) {
        ArrayList<String> list = new ArrayList<String>();
        String whereArgs[] = new String[1];
        whereArgs[0] = cityName;
        Cursor cursor = db.query("CityCodes", null, "City=?", whereArgs, null, null, null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return Integer.parseInt(list.get(0));
    }

}
