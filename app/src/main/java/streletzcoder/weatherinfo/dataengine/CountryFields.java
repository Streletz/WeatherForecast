package streletzcoder.weatherinfo.dataengine;

/**
 * Перечисление для полей БД
 */
public enum CountryFields {
    ID(0),
    COUNTRY(1);

    CountryFields(int i) {
        this.fieldCode =i;
    }

    public int getFieldCode()
    {
        return fieldCode;
    }

    private int fieldCode;
}
