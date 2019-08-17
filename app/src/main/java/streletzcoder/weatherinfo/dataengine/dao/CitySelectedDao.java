package streletzcoder.weatherinfo.dataengine.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import streletzcoder.weatherinfo.dataengine.models.CityCodes;
import streletzcoder.weatherinfo.dataengine.models.CitySelected;
@Dao
public interface CitySelectedDao {
    @Query("SELECT * FROM CitySelected")
    List<CitySelected> getAll();

    @Query("SELECT * FROM CitySelected WHERE _id = :id")
    CitySelected getById(int id);

    @Insert
    void insert(CitySelected cityCodes);

    @Update
    void update(CitySelected cityCodes);

    @Delete
    void delete(CitySelected cityCodes);
}
