package local.vda.votingsystem.web;

import static local.vda.votingsystem.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private SecurityUtil() {
    }

    private static int id = 100000;

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}