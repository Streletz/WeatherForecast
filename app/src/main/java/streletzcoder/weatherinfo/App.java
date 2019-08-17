package streletzcoder.weatherinfo;

import android.app.Application;
import android.arch.persistence.room.Room;

import streletzcoder.weatherinfo.dataengine.AppDatabase;

public class App extends Application {

    private AppDatabase database;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, getString(R.string.db_name))
                /* .addMigrations(AppDatabase.MIGRATION_22_23)*/.allowMainThreadQueries().build();
        instance = this;
    }

    public static  App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
