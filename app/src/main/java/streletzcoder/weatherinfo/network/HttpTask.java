package streletzcoder.weatherinfo.network;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Vector;

import streletzcoder.weatherinfo.OpenWeatherMap;
import streletzcoder.weatherinfo.R;
import streletzcoder.weatherinfo.WeatherInfo;
import streletzcoder.weatherinfo.dataengine.AppDatabase;

/**
 * Created by Стрелец Coder on 03.12.2015.
 * AsyncTask для получения данных о погоде
 */
public class HttpTask extends AsyncTask<String, Void, ArrayList<WeatherInfo>> {


    @Override
    protected ArrayList<WeatherInfo> doInBackground(String... params) {
        OpenWeatherMap owm = new OpenWeatherMap(params);
        return owm.getWeatherArray();
    }

    public static String getDataRequestString(Context context, AppDatabase database) {

        return context.getString(R.string.dataUrl) + database.daoCity().getById(database.daoCitySelected().getAll().get(0).CityId).Code +  context.getString(R.string.key);
    }
}
