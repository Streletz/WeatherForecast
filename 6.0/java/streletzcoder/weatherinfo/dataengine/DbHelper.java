package streletzcoder.weatherinfo.dataengine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import streletzcoder.weatherinfo.R;

/**
 * Класс хелпер для создания и наполнения БД и подключения к ней
 */
public class DbHelper extends SQLiteOpenHelper {
    private String DB_PATH;
    private String DB_NAME;
    //Версия базы данных
    private static final int DATABASE_VERSION = 22;
    private Context fContext;
    private SQLiteDatabase dataBase;
    private boolean upgrading = false;
    private SharedPreferences appSettings;
    public static final String APP_PREFERENCES = "scwisettings";
    //Признак новой установки


    public DbHelper(Context con) {
        super(con, con.getString(R.string.db_name), null, DATABASE_VERSION);
        this.fContext = con;
        DB_NAME = fContext.getString(R.string.db_name);
        DB_PATH = "/data/data/" + fContext.getPackageName() + "/databases/";
    }

    /**
     * Создание БД
     *
     * @throws IOException
     */
    public void createDataBase() throws IOException {
        if (upgrading) {
            return;
        }
        if (!checkDataBase()) {
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //файл базы данных отсутствует
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Копирование файла БД из assets в рабочую директорию
     *
     * @throws IOException
     */
    private void copyDataBase() throws IOException {
        InputStream input = fContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream output = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    }

    public void openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        dataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (dataBase != null)
            dataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

