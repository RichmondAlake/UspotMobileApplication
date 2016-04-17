package com.example.richmond.uspotmobileapplication.activities;

import com.vpedak.testsrecorder.core.ActivityListener;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.IntentFilter;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

@LargeTest
public class AndrTestRec extends ActivityInstrumentationTestCase2<CreateSpotPage> {
    public AndrTestRec() {
       super(CreateSpotPage.class);
    }
    private Activity activity;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }
    public void testRun() throws InterruptedException {
        new ActivityListener(getInstrumentation(), activity, 1460571387923L).start();
        Thread.sleep(24*60*60*1000);
    }
}
