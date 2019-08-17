package streletzcoder.weatherinfo.dataengine.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CountrySelected {
    @PrimaryKey
    public int _id;
    public int CountryId = 1;
}
