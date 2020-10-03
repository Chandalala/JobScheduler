package com.chandalala.jobscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scheduleJob(View view) {

        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true) //means job service will still run even if there is system reboot
                .setPeriodic(15 * 60 * 1000) // define how often we want to execute this job, this cannot be lower than 50mins
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = 0;
        if (scheduler != null) {
            resultCode = scheduler.schedule(info);
        }

        if (resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job scheduled");
        }
        else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    public void cancelJob(View view) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            scheduler.cancel(123);
        }

        Log.d(TAG, "Job cancelled");
    }
}
