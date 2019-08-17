package streletzcoder.weatherinfo.dataengine.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.Transaction;

import java.util.List;

public class CountryWithCitys {
    @Embedded
    public Country country;
    @Relation(parentColumn = "_id", entityColumn = "CountryId")
    public List<CityCodes> cityCodes;
}
