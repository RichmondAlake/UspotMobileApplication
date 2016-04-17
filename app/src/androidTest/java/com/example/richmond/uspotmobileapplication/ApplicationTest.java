package com.example.richmond.uspotmobileapplication;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    @Test
    public void sizeReturnsNumberOfElements() {
        List instance = new ArrayList();
        instance.add(new Object());
        instance.add(new Object());
        assertThat(instance.size(), is(2));
    }




}