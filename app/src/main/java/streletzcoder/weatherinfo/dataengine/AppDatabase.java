package streletzcoder.weatherinfo.dataengine;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import streletzcoder.weatherinfo.dataengine.dao.CityDao;
import streletzcoder.weatherinfo.dataengine.dao.CitySelectedDao;
import streletzcoder.weatherinfo.dataengine.dao.CountryDao;
import streletzcoder.weatherinfo.dataengine.dao.CountrySelectedDao;
import streletzcoder.weatherinfo.dataengine.models.CityCodes;
import streletzcoder.weatherinfo.dataengine.models.CitySelected;
import streletzcoder.weatherinfo.dataengine.models.Country;
import streletzcoder.weatherinfo.dataengine.models.CountrySelected;

@Database(entities = {
        CityCodes.class,
        CitySelected.class,
        Country.class,
        CountrySelected.class
},
        version = DbHelper.DATABASE_VERSION
)
public abstract class AppDatabase extends RoomDatabase {
    public  abstract CityDao daoCity();
    public  abstract CountryDao daoCountry();
    public abstract CitySelectedDao daoCitySelected();
    public abstract CountrySelectedDao daoCountrySelected();
    /*public abstract DaoMan daoMan();
    public abstract DaoCar daoCar();*/
//    public static final Migration MIGRATION_22_23 = new Migration(22, 23) {
//        @Override
//        public void migrate(final SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE CityCodes ADD PRIMARY KEY (Code)");
//        }
//    };
}
