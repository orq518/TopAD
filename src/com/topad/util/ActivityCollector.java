package com.topad.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }

        activities.clear();
    }

    public static void finishActivitys(ArrayList<String> activityList) {
        if (activityList != null && activityList.size() > 0) {
            for (int i = 0; i < activityList.size(); i++) {
                for (Activity activity : activities) {
//                    if(activityList.get(i).equals()){}

                }
            }
        }
    }

    public static void print(){
        Debugger.d("ucf_log", "print start:");
        Debugger.d("ucf_log", "activities:" + activities.size());
        for (Activity activity : activities) {
            Debugger.d("ucf_log", "activities:" + activity.getClass().getSimpleName());
        }
        Debugger.d("ucf_log", "print end");
    }
}
