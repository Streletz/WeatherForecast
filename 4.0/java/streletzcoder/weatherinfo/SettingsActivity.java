package streletzcoder.weatherinfo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Spinner;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonOk;
    private Spinner citySelect;
    private DbRepository repository;
    //Имя файла настроек
    public static final String APP_PREFERENCES = "weatherInfo_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        buttonOk = (Button) findViewById(R.id.buttonOk);
        citySelect = (Spinner) findViewById(R.id.citySelect);
        repository=new DbRepository(this.getApplicationContext());
        //Получаем из БД список городов и заполняем spinner
        ArrayList<String> cityList = repository.getData(null, Fields.CITY);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySelect.setAdapter(adapter);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Закрытие экрана по кнопке*/
                stop();
            }
        });
        citySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                saveCityPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Заглушка
            }
        });
        initializeSettings();
        setTitle(R.string.short_title);
    }

    private void stop() {
        this.finish();
    }

    private void initializeSettings() {
        //Считываем выбранный ранее город
        citySelect.setSelection(repository.getSelectedCityPosition());
    }

    private void saveCityPosition() {
        //Сохраняем выбранный город
        repository.setSelectedCity(citySelect.getSelectedItemPosition());
        //Быстрое обновление при смене города
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int ids[] = appWidgetManager.getAppWidgetIds(new ComponentName(this.getApplicationContext().getPackageName(), InfoWidget.class.getName()));
        InfoWidget.updateAppWidget(this.getApplicationContext(),appWidgetManager,ids[0]);
    }
}
