package streletzcoder.weatherinfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Класс репозиторий для работы с данными из БД
 */
public class DbRepository {

    private SQLiteDatabase db;
    private Context cont;

    public DbRepository(Context context) {
        //Подключение к базе данных
        db = new DbHelper(context).getWritableDatabase();
        cont=context;
    }

    public ArrayList<String> getData(String where,Fields field) {
        //Вывод записей с отбором (where!=null) и без
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query("CityCodes", null, where, null, null, null, null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            do {
                list.add(cursor.getString(field.getFieldCode()));
            } while (cursor.moveToNext());
        }
        return list;
    }

    private int getSelectedId() {
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

    public int getSelectedCityPosition() {
        //Позиция выбранного города для spinner'а
        return getSelectedId() - 1;
    }

    private String getSelectedCityCode() {
        //Возвращает код города для запроса на OpenWeatherMap
        ArrayList<String> list = getData("_id='"+String.valueOf(getSelectedId())+"'", Fields.CODE);
        return list.get(0);
    }
    public String getSelectedCityName() {
        //Возвращает название города для отображения в виджете
        ArrayList<String> list = getData("_id='"+String.valueOf(getSelectedId())+"'", Fields.CITY);
        return list.get(0);
    }
    public String getDataRequestString(){
        //Возвращает строку запрос на OpenWeatherMap
        return cont.getString(R.string.dataUrl) + getSelectedCityCode() + cont.getString(R.string.key);
    }

    public void setSelectedCity(int spinnerPosition) {
        //Сохраняем выбранный город
        String cityId = String.valueOf(spinnerPosition + 1);
        db.execSQL("UPDATE CitySelected SET CityId=" + cityId);
    }

}
