package streletzcoder.weatherinfo.dataengine;

/**
 * Перечисление для полей БД
 */
public enum CityCodeFields {
    ID(0),
    CITY(1),
    CODE(2);

    CityCodeFields(int i) {
        this.fieldCode =i;
    }

    public int getFieldCode()
    {
        return fieldCode;
    }

    private int fieldCode;
}
