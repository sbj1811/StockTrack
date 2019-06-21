package com.sjani.stocktrack.Utils;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class RefreshScheduler {


    public static void refreshPeriodicWork() {

        //define constraints
        Constraints myConstraints = new Constraints.Builder()
                .setRequiresCharging(false)
                .build();

        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(RefreshLatestStockPrice.class, 5, TimeUnit.MILLISECONDS)
                        .setConstraints(myConstraints)
                        .build();

        WorkManager.getInstance().enqueue(periodicWorkRequest);
    }
}
