package streletzcoder.weatherinfo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Класс хелпер для создания и наполнения БД и подключения к ней
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scweatherinfo.db";
    //Версия базы данных
    private static final int DATABASE_VERSION = 2;
    private Context context;
    private SQLiteDatabase db;

    public DbHelper(Context con) {
        super(con, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = con;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Создание таблиц
        db.execSQL("CREATE TABLE CityCodes (_id INTEGER PRIMARY KEY AUTOINCREMENT, City TEXT NOT NULL, Code TEXT NOT NULL );");
        db.execSQL("CREATE TABLE CitySelected (_id INTEGER PRIMARY KEY AUTOINCREMENT, CityId INTEGER NOT NULL);");
        //Добавление городов
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Москва','524901');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Санкт-Петербург','519690');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Архангельск','581049');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Астрахань','580497');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Белгород','824987');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Брянск','576560');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Великий Новгород','519336');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Владимир','473247');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Волгоград','472757');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Воронеж','472045');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Иваново','555312');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Ижевск','554840');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Йошкар-Ола','466806');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Казань','547861');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Калуга','553915');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Киров','548408');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Кострома','543878');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Краснодар','542420');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Курск','538560');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Липецк','535121');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Мурманск','524305');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Нижний Новгород','470012');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Орёл','515012');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Пенза','511565');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Пермь','511196');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Петрозаводск','509820');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Ростов-На-Дону','524901');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Рязань','500096');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Самара','499099');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Саранск','498698');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Саратов','542458');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Смоленск','491687');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Сочи','823678');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Ставрополь','487846');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Тверь','480060');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Тула','480562');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Ульяновск','479123');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Чебоксары','569696');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Элиста','563514');");
        db.execSQL("INSERT INTO CityCodes (City,Code) VALUES('Ярославль','468902');");
        //Город по умолчанию
        db.execSQL("INSERT INTO CitySelected (CityId) SELECT _id FROM CityCodes WHERE City='Москва';");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE CityCodes;");
        db.execSQL("DROP TABLE CitySelected;");
        onCreate(db);
    }
}
