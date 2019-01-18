package com.shocknode.utilities;

import java.time.LocalTime;

public class DateTime {

    public static LocalTime getDayStartTime()
    {
        return LocalTime.of(0, 0);
    }

    public static LocalTime getDayEndTime()
    {
        return LocalTime.of(23, 59);
    }

}
