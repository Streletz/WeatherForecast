package streletzcoder.weatherinfo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    /*ГЛАВНЫЙ ЭКРАН ПРИЛОЖЕНИЯ*/
    /*ИНТЕРФЕЙС ПОЛЬЗОВАТЕЛЯ*/
    private TextView dayTemp;
    private TextView nightTemp;
    private TextView currentDayTitle;
    private TextView weatherText;
    private TextView tvJson;
    private ListView listView;
    private String tvArray[];
    /*ВЕКТОР ДАННЫХ О ПОГОДЕ*/
    Vector<WeatherInfo> weatherInfoVector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dayTemp = (TextView) findViewById(R.id.dayTemp);
        nightTemp = (TextView) findViewById(R.id.nightTemp);
        currentDayTitle = (TextView) findViewById(R.id.currentDayTitle);
        weatherText = (TextView) findViewById(R.id.weatherText);
        listView=(ListView) findViewById(R.id.listView);
        //Инициализация текстовых полей следующих 6 дней недели
        tvArray = new String[6];
       /* tvArray[0] = (TextView) findViewById(R.id.day2Text);
        tvArray[1] = (TextView) findViewById(R.id.day3Text);
        tvArray[2] = (TextView) findViewById(R.id.day4Text);
        tvArray[3] = (TextView) findViewById(R.id.day5Text);
        tvArray[4] = (TextView) findViewById(R.id.day6Text);
        tvArray[5] = (TextView) findViewById(R.id.day7Text);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {/*Иницализация меню*/
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {/*Обработка событий меню*/
        switch (item.getItemId()) {
            //Обновление данных о погоде через меню
            case R.id.refreshItem:
                loadWeatherData();break;
                //Вызов экрана "О Программе" через меню
            case R.id.aboutInfoItem:
                showAboutActivity();break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWeatherData();
    }

    private void loadWeatherData() {
        /*Загрузка данных о погоде*/
        //Получаем сегодняшнюю дату
        currentDayTitle.setText(getFormattedDate());
        //Получаем сведения о погоде с сайта
        HttpTask ht = new HttpTask();
        AsyncTask<String, Void, Vector<WeatherInfo>> s = ht.execute(getDataRequestString());
        try {
            //Извлекаем полученные данные о погоде
            weatherInfoVector = s.get();
            if (weatherInfoVector != null) {
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
        for (int i = 0; i <= 5; i++) {
            tvArray[i]=getString(R.string.noData);
        }
        RefresDayshList();
        Toast.makeText(MainActivity.this, "Невозможно загрузить данные!\nВероятно отсутствует доступ в Интернет", Toast.LENGTH_SHORT).show();
    }

    private void RefresDayshList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getApplicationContext(),android.R.layout.simple_list_item_1,tvArray);
        listView.setAdapter(adapter);
    }

    private void showWeatherData() {
        /*Отображение данных о погоде*/
        //Данные на сегодня
        WeatherInfo todayWeatherInfo = weatherInfoVector.get(0);
        //Температура день-ночь
        dayTemp.setText(String.valueOf(todayWeatherInfo.getDayTemp()));
        nightTemp.setText(String.valueOf(todayWeatherInfo.getNightTemp()));
        //Описание погоды
        weatherText.setText(todayWeatherInfo.getWeatherDescription());
        //Данные о погоде на следующие 6 дней

        for (int i = 0; i <= 5; i++) {
            tvArray[i]=weatherInfoVector.get(i + 1).getShortInfo();
           // adapter.notifyDataSetChanged();
          //  tvArray[i].setText(weatherInfoVector.get(i + 1).getShortInfo());
        }
        RefresDayshList();

    }

    private String getFormattedDate() {/*Форматированное значение даты*/
        Locale local = new Locale("ru", "RU");
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, local);
        return df.format(Calendar.getInstance().getTime());
    }

    private String getDataRequestString() {
            /*Получаем из ресурсов строку запроса*/
        return getString(R.string.dataUrl) + getString(R.string.key);
    }
    private void showAboutActivity()
    {
        /*Вызов экрана "О Программе"*/
        Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(aboutIntent);
    }

}
