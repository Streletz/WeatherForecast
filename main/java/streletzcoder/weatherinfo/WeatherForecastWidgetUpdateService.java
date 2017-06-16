package streletzcoder.weatherinfo;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

public class WeatherForecastWidgetUpdateService extends Service {
    public WeatherForecastWidgetUpdateService() {
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        updateInfoWidget();
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateInfoWidget()
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int ids[] = appWidgetManager.getAppWidgetIds(new ComponentName(this.getApplicationContext().getPackageName(), InfoWidget.class.getName()));
        //Проверяем, а существуют ли вообще виджеты в данный момент
        if (ids.length>0) {
            InfoWidget.updateAppWidget(this.getApplicationContext(), appWidgetManager, ids[0]);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
