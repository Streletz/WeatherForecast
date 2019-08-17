package streletzcoder.weatherinfo.dataengine;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    /*public abstract DaoMan daoMan();
    public abstract DaoCar daoCar();*/
}
