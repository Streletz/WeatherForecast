package streletzcoder.weatherinfo;

/**
 * Перечисление для полей БД
 */
public enum Fields {
    ID(0),
    CITY(1),
    CODE(2);

    Fields(int i) {
        this.fieldCode =i;
    }

    public int getFieldCode()
    {
        return fieldCode;
    }

    private int fieldCode;
}
