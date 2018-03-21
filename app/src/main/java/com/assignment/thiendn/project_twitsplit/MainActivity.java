package com.assignment.thiendn.project_twitsplit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] aa = MessageUtils.splitMessage("I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself.");
        System.out.println(aa);
        MessageUtils.splitMessage("123451234512345123451234512345123451234512345123451234512345 1234512345");

    }
}
