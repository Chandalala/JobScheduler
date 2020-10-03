package com.chandalala.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.SystemClock;
import android.util.Log;

public class ExampleJobService extends JobService {

    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: Job started");

        //If our task is short and can be executed in the scope of this method we have to return false
        //otherwise we have to return true
        //return false;

        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Define what you want to do in here
                for (int i = 0; i < 10; i++){
                    Log.d(TAG, " run " + i);

                    if (jobCancelled) return;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    //This method will be called by the system if the job gets cancelled
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion ");
        jobCancelled = true;
        return true;
    }
}
