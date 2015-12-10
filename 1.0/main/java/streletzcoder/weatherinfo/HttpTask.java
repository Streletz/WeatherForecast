package streletzcoder.weatherinfo;

import android.os.AsyncTask;

import java.util.Vector;

/**
 * Created by Стрелец Coder on 03.12.2015.
 * AsyncTask для получения данных о погоде
 */
public class HttpTask extends AsyncTask<String, Void, Vector<WeatherInfo>> {


    @Override
    protected Vector<WeatherInfo> doInBackground(String... params) {
        OpenWeatherMap owm = new OpenWeatherMap(params);
        return owm.getWeatherArray();
    }
}
