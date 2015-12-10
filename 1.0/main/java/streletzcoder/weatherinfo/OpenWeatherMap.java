package streletzcoder.weatherinfo;

/**
 * Created by Стрелец Coder on 03.12.2015.
 * Класс для работы с API сайта openweathermap
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

public class OpenWeatherMap {
    //Строка GET запроса
    private String url;

    public OpenWeatherMap(String[] params) {
        url = params[0];
    }


    private JSONObject getJSONData(String site_url) throws IOException, JSONException {
        /*Загрузка JSON*/
        String res = "";
        //Отправляем GET запрос и получаем данные с сайта
        URL url = new URL(site_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //Если всё нормально возвращаем полученные данные
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            res = in.readLine();
            in.close();
        } else {
            return null;
        }
        //Преобразуем данные в JSON
        try {
            return new JSONObject(res);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Vector<WeatherInfo> getWeatherArray() {
        /*Выдача структурированных данных о погоде*/
        JSONObject json = null;
        Vector<WeatherInfo> infoVector = null;
        try {
            json = getJSONData(url);
            infoVector = new Vector<WeatherInfo>();
            //Получаем JSON массив с данными конкретно о погоде
            JSONArray weatherDataArray = json.getJSONArray("list");
            //Обходим полученный массив (парсинг)
            for (int i = 0; i <= weatherDataArray.length() - 1; i++) {
                //Получаем объект JSON с информацией на конкретный день
                JSONObject joDay = weatherDataArray.getJSONObject(i);
                //Инициализируем строитель WeatherInfo
                WeatherInfoBuilder builder = new WeatherInfoBuilder();
                builder.createNewWeatherInfo();
                //Получаем дату
                builder.setDate(joDay.getLong("dt"));
                //Получаем температуру
                builder.setTemp(joDay.getJSONObject("temp").getDouble("day"), joDay.getJSONObject("temp").getDouble("night"));
                //Получаем описание погоды
                builder.setWeatherDescription(joDay.getJSONArray("weather").getJSONObject(0).getString("description"));
                //Выдаём готовый объект WeatherInfo и добавляем его в вектор
                WeatherInfo info = builder.getWeatherInfo();
                infoVector.add(info);
            }
            return infoVector;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
