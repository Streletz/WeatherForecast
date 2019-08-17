package streletzcoder.weatherinfo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import streletzcoder.weatherinfo.dataengine.DbRepository;
import streletzcoder.weatherinfo.network.HttpTask;

/**
 * Implementation of App Widget functionality.
 */
public class InfoWidget extends AppWidgetProvider {
    //GUI
    //Интервал обновления
    private final int updateInterval = 150000;
    public static String ACTION_WIDGET_CLICKED = "ClickWidget";
    private PendingIntent service = null;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.info_widget);
        //DbRepository repository = new DbRepository(context);
        //Выводим название города
        views.setTextViewText(R.id.appwidget_textHeader, "");
        //Выводим погоду
        //getWeather(context, views, repository);
        //Делаем виджет кликабельным
        Intent clickIntent = new Intent(context,InfoWidget.class);
        clickIntent.setAction(ACTION_WIDGET_CLICKED);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,0,clickIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_textHeader,clickPendingIntent);
        views.setOnClickPendingIntent(R.id.appwidget_textContent,clickPendingIntent);
        // Обновляем информацию, отображаемую виджетом
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void getWeather(Context context, RemoteViews views, DbRepository repository) {
        ArrayList<WeatherInfo> weatherInfoVector= new ArrayList<>();
        String showingWeatherText = "";
        HttpTask ht = new HttpTask();
        AsyncTask<String, Void, ArrayList<WeatherInfo>> s = ht.execute(repository.getDataRequestString());
        try {
            //Извлекаем полученные данные о погоде
            weatherInfoVector.addAll(s.get());
            if ((weatherInfoVector != null)&&(weatherInfoVector.size()>0)) {
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
      //Заглушка
    }
    @Override
    public void onReceive(Context context, Intent intent)
    {
        final AlarmManager manager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        final Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        final Intent i = new Intent(context, WeatherForecastWidgetUpdateService.class);
        if (service == null)
        {
            service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        manager.setRepeating(AlarmManager.RTC,startTime.getTime().getTime(),updateInterval,service);
    }
    @Override
    public void onEnabled(Context context) {
        //Заглушка
    }

    @Override
    public void onDisabled(Context context) {
        stopAlarmManager(context);
    }

    private static String getStub() {
        /*Отображение заглушек, если нет данных о погоде*/
        return "Нет данных";
    }

    private void stopAlarmManager(Context context) {
        final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        m.cancel(service);
    }

}

