import java.time.LocalDateTime;

public class Objects {

    private Integer integerObject;
    private Long longObject;
    private Double doubleObject;
    private Float floatObject;
    private String stringObject;
    private LocalDateTime localDateTimeObject;

    public Objects() {

        this.integerObject = 0;
        this.longObject = 0L;
        this.doubleObject = 0.0;
        this.floatObject = 0F;
        this.stringObject = "";
        this.localDateTimeObject = LocalDateTime.now();


    }

    public Objects(Integer integerObject, Long longObject, Double doubleObject, Float floatObject, String stringObject, LocalDateTime localDateTimeObject) {
        this.integerObject = integerObject;
        this.longObject = longObject;
        this.doubleObject = doubleObject;
        this.floatObject = floatObject;
        this.stringObject = stringObject;
        this.localDateTimeObject = localDateTimeObject;
    }

    public Integer getIntegerObject() {
        return integerObject;
    }

    public void setIntegerObject(Integer integerObject) {
        this.integerObject = integerObject;
    }

    public Long getLongObject() {
        return longObject;
    }

    public void setLongObject(Long longObject) {
        this.longObject = longObject;
    }

    public Double getDoubleObject() {
        return doubleObject;
    }

    public void setDoubleObject(Double doubleObject) {
        this.doubleObject = doubleObject;
    }

    public Float getFloatObject() {
        return floatObject;
    }

    public void setFloatObject(Float floatObject) {
        this.floatObject = floatObject;
    }

    public String getStringObject() {
        return stringObject;
    }

    public void setStringObject(String stringObject) {
        this.stringObject = stringObject;
    }

    public LocalDateTime getLocalDateTimeObject() {
        return localDateTimeObject;
    }

    public void setLocalDateTimeObject(LocalDateTime localDateTimeObject) {
        this.localDateTimeObject = localDateTimeObject;
    }
}
