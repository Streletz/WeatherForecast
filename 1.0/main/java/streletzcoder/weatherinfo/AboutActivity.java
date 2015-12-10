package streletzcoder.weatherinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {
    /*ЭКРАН "О ПРОГРАММЕ"*/
    private Button buttonOk;
    private Button buttonDonate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonDonate =(Button) findViewById(R.id.buttonDonate);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Закрытие экрана по кнопке*/
                stop();
            }
        });
        buttonDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.donateURL)));
                startActivity(donateIntent);
            }
        });
    }

    private void stop() {
        this.finish();
    }
}
