package streletzcoder.weatherinfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import streletzcoder.weatherinfo.dataengine.AppDatabase;
import streletzcoder.weatherinfo.dataengine.DbHelper;
import streletzcoder.weatherinfo.dataengine.models.CityCodes;
import streletzcoder.weatherinfo.dataengine.models.CitySelected;
import streletzcoder.weatherinfo.dataengine.models.Country;
import streletzcoder.weatherinfo.dataengine.models.CountrySelected;
import streletzcoder.weatherinfo.dataengine.models.CountryWithCitys;
import streletzcoder.weatherinfo.gui_adapters.DaysListAdapter;
import streletzcoder.weatherinfo.models.WeatherInfo;
import streletzcoder.weatherinfo.network.HttpTask;

public class MainActivity extends AppCompatActivity {
    /*ГЛАВНЫЙ ЭКРАН ПРИЛОЖЕНИЯ*/
    /*ИНТЕРФЕЙС ПОЛЬЗОВАТЕЛЯ*/
    private TextView dayTemp;
    private TextView nightTemp;
    private TextView cityTitle;
    private TextView currentDayTitle;
    private TextView weatherText;
    private String tvArray[];
    /*ВЕКТОР ДАННЫХ О ПОГОДЕ*/
    ArrayList<WeatherInfo> weatherInfoArray = new ArrayList<>();
    private String DB_PATH;
    private String DB_NAME;
    private SharedPreferences appSettings;
    public static final String APP_PREFERENCES = "scwisettings";
    //Признак новой установки
    private boolean newInstanse;
    private boolean copied;
    private RecyclerView daysListRecycler;
    Timer timer = new Timer();
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try {
//            deleteDataBase();
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        dayTemp = findViewById(R.id.dayTemp);
        nightTemp = findViewById(R.id.nightTemp);
        cityTitle = findViewById(R.id.cityTitle);
        currentDayTitle = findViewById(R.id.currentDayTitle);
        weatherText = findViewById(R.id.weatherText);
        //Инициализация текстовых полей следующих 6 дней недели
        tvArray = new String[6];
        daysListRecycler = findViewById(R.id.daysListRecycler);
        daysListRecycler.setLayoutManager(new LinearLayoutManager(this));
        setTitle(R.string.short_title);
        timer.schedule(new UpdateTimerTask(), 0, 12 * 60 * 60 * 1000);
        // БД
        DbHelper helper = new DbHelper(this);
        try {
            helper.createDataBase();
            database = App.getInstance().getDatabase();
            List<CityCodes> codes = database.daoCity().getAll();
            List<Country> countries = database.daoCountry().getAll();
            List<CitySelected> citySelecteds = database.daoCitySelected().getAll();
            List<CountrySelected> countrySelecteds = database.daoCountrySelected().getAll();
            CountryWithCitys countryWithCitys = database.daoCountry().getCountryWithCitys(2);
            List<CountryWithCitys> countryWithCitysList = database.daoCountry().getCountryWithCitys();
            int a = 1;
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {/*Иницализация меню*/
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Обработка событий меню*/
        switch (item.getItemId()) {
            //Вызов окна настроек через меню
            case R.id.settingsItem:
                showSettingsActivity();
                break;
            //Вызов экрана "О Программе" через меню
            case R.id.aboutInfoItem:
                showAboutActivity();
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWeatherData();
    }

    /**
     * Загрузка данных о погоде
     */
    private void loadWeatherData() {
        /*Загрузка данных о погоде*/
        //Получаем сегодняшнюю дату
        currentDayTitle.setText(getFormattedDate());
        cityTitle.setText(database.daoCity().getById(database.daoCitySelected().getAll().get(0).CityId).City);
        //Получаем сведения о погоде с сайта
        HttpTask ht = new HttpTask();
        AsyncTask<String, Void, ArrayList<WeatherInfo>> s = ht.execute(HttpTask.getDataRequestString(this,database));
        try {
            //Извлекаем полученные данные о погоде
            weatherInfoArray.clear();
            weatherInfoArray.addAll(s.get());
            if ((weatherInfoArray != null) && (weatherInfoArray.size() > 0)) {
                /*Если данные есть выводим их на экран*/
                showWeatherData();
            } else {
                /*Если данных нет выводим заглушки*/
                showStub();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void showStub() {
        /*Отображение заглушек, если нет данных о погоде*/
        dayTemp.setText(R.string.noData);
        nightTemp.setText(R.string.noData);
        weatherText.setText(R.string.noData);
        Toast.makeText(MainActivity.this, getString(R.string.criticalError), Toast.LENGTH_SHORT).show();
    }

    /**
     * Обновление данных о погоде
     */
    private void refreshDayshList() {
        DaysListAdapter adapter = new DaysListAdapter(this, weatherInfoArray);
        daysListRecycler.setAdapter(adapter);
    }

    private void showWeatherData() {
        /*Отображение данных о погоде*/
        //Данные на сегодня
        WeatherInfo todayWeatherInfo = weatherInfoArray.get(0);
        //Температура день-ночь
        dayTemp.setText(String.valueOf(todayWeatherInfo.getDayTemp()));
        nightTemp.setText(String.valueOf(todayWeatherInfo.getNightTemp()));
        //Описание погоды
        weatherText.setText(todayWeatherInfo.getWeatherDescription());
        //Данные о погоде на следующие 6 дней
        refreshDayshList();
    }

    private String getFormattedDate() {/*Форматированное значение даты*/
        Locale local = new Locale("ru", "RU");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, local);
        return df.format(Calendar.getInstance().getTime());
    }

    private void showAboutActivity() {
        /*Вызов экрана "О Программе"*/
        Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    private void showSettingsActivity() {
        /*Вызов экрана настроек*/
        Intent aboutIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(aboutIntent);
    }

    /**
     * Класс задачи для таймера по обновлению данных о погоде.
     */
    class UpdateTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadWeatherData();
                }
            });
        }
    }



}
