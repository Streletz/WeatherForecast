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

import streletzcoder.weatherinfo.dataengine.AppDatabase;
import streletzcoder.weatherinfo.dataengine.CityCodeFields;
import streletzcoder.weatherinfo.dataengine.CountryFields;
import streletzcoder.weatherinfo.dataengine.DbRepository;
import streletzcoder.weatherinfo.dataengine.models.CityCodes;
import streletzcoder.weatherinfo.dataengine.models.CitySelected;
import streletzcoder.weatherinfo.dataengine.models.Country;
import streletzcoder.weatherinfo.dataengine.models.CountrySelected;
import streletzcoder.weatherinfo.dataengine.models.CountryWithCitys;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonOk;
    private Spinner citySelect;
    private Spinner countrySelect;
    private ArrayList<String> cityList = new ArrayList<>();
    private ArrayList<String> countryList = new ArrayList<>();
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        database = App.getInstance().getDatabase();
        //Включение кнопки "Назад"
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Инициализация интерфейса
        buttonOk = (Button) findViewById(R.id.buttonOk);
        citySelect = (Spinner) findViewById(R.id.citySelect);
        countrySelect = (Spinner) findViewById(R.id.countrySelect);

        //Получаем из БД список стран и заполняем spinner
        for (Country country : database.daoCountry().getAll()) {
            countryList.add(country.Country);
        }
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
                String cityName = database.daoCity().getById(database.daoCitySelected().getAll().get(0).CityId).City;
                citySelect.setSelection(cityList.indexOf(cityName));
                // saveCityPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        citySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // saveCityPosition();
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
        cityList.clear();
        for (CityCodes cityCodes : database.daoCountry().getCountryWithCitys(database.daoCountrySelected().getAll().get(0).CountryId).cityCodes) {
            cityList.add(cityCodes.City);
        }
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
        countrySelect.setSelection(countryList.indexOf(database.daoCountry().getById(database.daoCountrySelected().getAll().get(0).CountryId).Country));
        //Считываем выбранный ранее город
        String cityName = database.daoCity().getById(database.daoCitySelected().getAll().get(0).CityId).City;
        int a = cityList.indexOf(cityName);
        citySelect.setSelection(cityList.indexOf(cityName));
    }

    private void saveCityPosition() {
        //Сохраняем выбранный город
        CitySelected citySelected = database.daoCitySelected().getAll().get(0);
        CityCodes cityCode = database.daoCity().getByCity(citySelect.getSelectedItem().toString());
        citySelected.CityId = cityCode._id;
        database.daoCitySelected().update(citySelected);
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
        String countryName = countrySelect.getSelectedItem().toString();
        Country country = database.daoCountry().getByCountry(countryName);
        CountrySelected countrySelected = database.daoCountrySelected().getAll().get(0);
        countrySelected.CountryId = country._id;
        database.daoCountrySelected().update(countrySelected);
        // repository.setSelectedCountry(countrySelect.getSelectedItem().toString());
    }

    private void showAboutActivity() {
        /*Вызов экрана "О Программе"*/
        Intent aboutIntent = new Intent(SettingsActivity.this, AboutActivity.class);
        startActivity(aboutIntent);
    }
}
