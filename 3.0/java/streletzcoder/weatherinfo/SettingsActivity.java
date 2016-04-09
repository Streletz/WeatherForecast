package streletzcoder.weatherinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonOk;
    private Spinner citySelect;
    //Переменная для работы с настройками
    private SharedPreferences programSettings;
    //Имя файла настроек
    public static final String APP_PREFERENCES = "weatherInfo_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        buttonOk = (Button) findViewById(R.id.buttonOk);
        citySelect = (Spinner) findViewById(R.id.citySelect);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
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

            }
        });
        initializeSettings();
    }

    private void stop() {
        this.finish();
    }

    private void initializeSettings() {
        programSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        //Считываем выбранный ранее город
        int position = programSettings.getInt("cityPosition", 0);
        citySelect.setSelection(position);
    }

    private void saveCityPosition() {
        int a = citySelect.getSelectedItemPosition();
        SharedPreferences.Editor editor = programSettings.edit();
        editor.putInt("cityPosition", a);
        editor.apply();
    }
}
