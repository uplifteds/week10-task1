package org.uplifteds.entitygenerator;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomType {
    public static LocalDate generRandBirthdate() {
        final int DAYS_IN_YEAR = 365;
        final int FIFTEEN_YEARS = 15;
        int periodOfDays = FIFTEEN_YEARS * DAYS_IN_YEAR;
        return LocalDate.ofEpochDay(ThreadLocalRandom
                .current().nextInt(-periodOfDays, periodOfDays));
    }

    public static Timestamp generRandTimestampWithin2025Year() {
        long offset = Timestamp.valueOf("2025-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2025-12-31 23:59:59").getTime();
        long diff = end - offset;
        Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
        return rand;
    }

    public static String generRandString(int targetStringLength) {
        int leftLimit = 97; // code of letter 'a'
        int rightLimit = 122; // code of letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String randString = buffer.toString();
        if (targetStringLength > UserGenerator.maxNameLength){
            randString = randString.toUpperCase(); // only name would be lowercase
        }

        return randString;
    }

}
