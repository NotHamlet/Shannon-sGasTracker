package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aaron on 5/3/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread lTimer = new Thread() {

            public void run() {

                try {
                    int lTimer1 = 0;
                    while (lTimer1 < 1750) {
                        sleep(100);
                        lTimer1 = lTimer1 + 100;
                    }
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finally {
                    finish();
                }
            }
        };
        lTimer.start();
    }
}