package streletzcoder.weatherinfo.dataengine.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class CityCodes {
    @PrimaryKey
    public int _id;
    public String City;
    public String Code;
    public int CountryId = 1;
}
