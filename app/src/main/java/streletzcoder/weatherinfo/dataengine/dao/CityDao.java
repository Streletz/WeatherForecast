package streletzcoder.weatherinfo.dataengine.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import streletzcoder.weatherinfo.dataengine.models.CityCodes;

@Dao
public interface CityDao {
    @Query("SELECT * FROM CityCodes")
    List<CityCodes> getAll();

    @Query("SELECT * FROM CityCodes WHERE Code = :code")
    CityCodes getByCode(String code);

    @Query("SELECT * FROM CityCodes WHERE City = :city")
    CityCodes getByCity(String city);

    @Query("SELECT * FROM CityCodes WHERE _id = :id")
    CityCodes getById(int id);

    @Insert
    void insert(CityCodes cityCodes);

    @Update
    void update(CityCodes cityCodes);

    @Delete
    void delete(CityCodes cityCodes);
}
