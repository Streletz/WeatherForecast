package streletzcoder.weatherinfo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of App Widget functionality.
 */
public class InfoWidget extends AppWidgetProvider {
    //GUI
    //Интервал обновления
    private final int updateInterval = 300000;
    //Таймер для обновления
    Timer normalTimer = new Timer();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.info_widget);
        DbRepository repository = new DbRepository(context);
        //Выводим название города
        views.setTextViewText(R.id.appwidget_textHeader, repository.getSelectedCityName());
        //Выводим погоду
        getWeather(context, views, repository);
        // Обновляем информацию, отображаемую виджетом
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void getWeather(Context context, RemoteViews views, DbRepository repository) {
        Vector<WeatherInfo> weatherInfoVector;
        String showingWeatherText = "";
        HttpTask ht = new HttpTask();
        AsyncTask<String, Void, Vector<WeatherInfo>> s = ht.execute(repository.getDataRequestString());
        try {
            //Извлекаем полученные данные о погоде
            weatherInfoVector = s.get();
            if (weatherInfoVector != null) {
                /*Если данные есть выводим их на экран*/
                WeatherInfo todayWeatherInfo = weatherInfoVector.get(0);
                //Температура день-ночь
                String dayTemp = String.valueOf(todayWeatherInfo.getDayTemp());
                String nightTemp = String.valueOf(todayWeatherInfo.getNightTemp());
                //Описание погоды
                String weatherText = todayWeatherInfo.getWeatherDescription();
                showingWeatherText = nightTemp + "..." + dayTemp;
                views.setTextViewText(R.id.appwidget_textContent, showingWeatherText);
            } else {
                /*Если данных нет выводим заглушки*/
                views.setTextViewText(R.id.appwidget_textContent, getStub());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        updateAppWidget(context, appWidgetManager, appWidgetIds[0]);
        startNormalTimer(context, appWidgetManager, appWidgetIds[0]);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        stopTimer();
    }

    private static String getStub() {
        /*Отображение заглушек, если нет данных о погоде*/
        return "Нет данных";
    }

    private void startNormalTimer(final Context context, final AppWidgetManager appWidgetManager,
                                  final int appWidgetId) {
        normalTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }, 0, updateInterval);
    }

    private void stopTimer() {
        normalTimer.cancel();
    }

}

