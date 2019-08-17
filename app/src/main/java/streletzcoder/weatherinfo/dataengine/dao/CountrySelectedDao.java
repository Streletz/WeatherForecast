package streletzcoder.weatherinfo.dataengine.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import streletzcoder.weatherinfo.dataengine.models.CountrySelected;

@Dao
public interface CountrySelectedDao {
    @Query("SELECT * FROM CountrySelected")
    List<CountrySelected> getAll();

    @Query("SELECT * FROM CountrySelected WHERE _id = :id")
    CountrySelected getById(int id);

    @Insert
    void insert(CountrySelected countrySelected);

    @Update
    void update(CountrySelected countrySelected);

    @Delete
    void delete(CountrySelected countrySelected);
}
