package streletzcoder.weatherinfo;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import streletzcoder.weatherinfo.dataengine.CityCodeFields;
import streletzcoder.weatherinfo.dataengine.CountryFields;
import streletzcoder.weatherinfo.dataengine.DbRepository;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonOk;
    private Spinner citySelect;
    private Spinner countrySelect;
    //private DbRepository repository;
    private ArrayList<String> cityList;
    private ArrayList<String> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Включение кнопки "Назад"
        ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Инициализация интерфейса
        buttonOk = (Button) findViewById(R.id.buttonOk);
        citySelect = (Spinner) findViewById(R.id.citySelect);
        countrySelect = (Spinner) findViewById(R.id.countrySelect);
      //  repository = new DbRepository(this.getApplicationContext());
        //Получаем из БД список стран и заполняем spinner
       // countryList = repository.getCountryData(null, CountryFields.COUNTRY);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countryList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySelect.setAdapter(countryAdapter);
        fillCityes();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Закрытие экрана по кнопке*/
                stop();
            }
        });
        countrySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                saveCountryPosition();
                fillCityes();
           //     citySelect.setSelection(cityList.indexOf(repository.getSelectedCityName()));
                saveCityPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {/*Иницализация меню*/
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settingsmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Обработка событий меню*/
        switch (item.getItemId()) {
            //Кнопка "Назад"
            case android.R.id.home:
                this.finish();
                return true;
            //Вызов экрана "О Программе" через меню
            case R.id.aboutInfoItem:
                showAboutActivity();
                break;
        }
        return false;
    }

    private void fillCityes() {
        //Получаем из БД список городов и заполняем spinner
        citySelect.setAdapter(null);
        cityList = null;
       // cityList = repository.getData(null, CityCodeFields.CITY);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySelect.setAdapter(adapter);
    }

    private void stop() {
        saveCountryPosition();
        saveCityPosition();
        this.finish();
    }

    private void initializeSettings() {
        //Считываем ранее выбранную страну
       // countrySelect.setSelection(countryList.indexOf(repository.getSelectedCountryName()));
        //Считываем выбранный ранее город
       // citySelect.setSelection(cityList.indexOf(repository.getSelectedCityName()));
    }

    private void saveCityPosition() {
        //Сохраняем выбранный город
       // repository.setSelectedCity(citySelect.getSelectedItem().toString());
        //Быстрое обновление при смене города
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int ids[] = appWidgetManager.getAppWidgetIds(new ComponentName(this.getApplicationContext().getPackageName(), InfoWidget.class.getName()));
        //Проверяем, а существуют ли вообще виджеты в данный момент
        if (ids.length > 0) {
            InfoWidget.updateAppWidget(this.getApplicationContext(), appWidgetManager, ids[0]);
        }
    }

    /**
     * Сохраняем выбранную страну
     */
    private void saveCountryPosition() {
       // repository.setSelectedCountry(countrySelect.getSelectedItem().toString());
    }
    private void showAboutActivity() {
        /*Вызов экрана "О Программе"*/
        Intent aboutIntent = new Intent(SettingsActivity.this, AboutActivity.class);
        startActivity(aboutIntent);
    }
}
