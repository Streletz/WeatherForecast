package streletzcoder.weatherinfo.dataengine.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import streletzcoder.weatherinfo.dataengine.models.Country;
import streletzcoder.weatherinfo.dataengine.models.CountryWithCitys;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM Country")
    List<Country> getAll();

    @Query("SELECT * FROM Country WHERE _id = :id")
    Country getById(int id);

    @Query("SELECT * FROM Country WHERE Country = :country")
    Country getByCountry(String country);

    @Insert
    void insert(Country country);

    @Update
    void update(Country country);

    @Delete
    void delete(Country country);
    @Transaction
    @Query("SELECT c1.* FROM Country c1 WHERE c1._id=:country_id")
    CountryWithCitys getCountryWithCitys(int country_id);

    @Transaction
    @Query("SELECT c1.* FROM Country c1" )
    List<CountryWithCitys> getCountryWithCitys();
}
