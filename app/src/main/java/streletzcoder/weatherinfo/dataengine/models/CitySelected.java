package streletzcoder.weatherinfo.dataengine.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CitySelected {
    @PrimaryKey
    //@NonNull
    public int _id;
    public int CityId = 1;
}
