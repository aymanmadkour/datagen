package com.aymanmadkour.datagen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

final class RandomDateTimeDataGen extends DataGen {
    private final long minDateTime;
    private final long maxDateTime;
    private final DateFormat dateFormat;

    public RandomDateTimeDataGen(long minDateTime, long maxDateTime, String dateFormatStr) {
        this.minDateTime = minDateTime;
        this.maxDateTime = maxDateTime;
        this.dateFormat = new SimpleDateFormat(dateFormatStr);
    }

    public RandomDateTimeDataGen(String minDateTime, String maxDateTime, String dateFormatStr) {
        this.dateFormat = new SimpleDateFormat(dateFormatStr);

        try {
            this.minDateTime = this.dateFormat.parse(minDateTime).getTime();
        } catch (Throwable e) {
            throw new IllegalArgumentException("Invalid date format: " + minDateTime);
        }

        try {
            this.maxDateTime = this.dateFormat.parse(maxDateTime).getTime();
        } catch (Throwable e) {
            throw new IllegalArgumentException("Invalid date format: " + maxDateTime);
        }
    }

    @Override
    public final String generate(Context context) {
        long timestamp = this.minDateTime + context.generateLong(this.maxDateTime - this.minDateTime);
        return this.dateFormat.format(new Date(timestamp));
    }

    @Override
    public final void generate(Context context, StringBuilder s) {
        s.append(this.generate(context));
    }
}
