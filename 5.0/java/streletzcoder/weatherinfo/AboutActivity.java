package streletzcoder.weatherinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {
    /*ЭКРАН "О ПРОГРАММЕ"*/
    private Button buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Закрытие экрана по кнопке*/
                stop();
            }
        });
    setTitle(R.string.short_title);
    }
    public boolean onCreateOptionsMenu(Menu menu) {/*Иницализация меню*/
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.aboutmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {/*Обработка событий меню*/
        switch (item.getItemId()) {
            case R.id.donateItem:
                showDonatePage();
        }
        return false;
    }

    private void showDonatePage() {
        Intent donateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.donateURL)));
        startActivity(donateIntent);
    }

    private void stop() {
        this.finish();
    }
}
