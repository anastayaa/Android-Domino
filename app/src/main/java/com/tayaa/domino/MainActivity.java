package com.tayaa.domino;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button rollButton;
    private ImageView leftImage;
    private ImageView rightImage;
    private int[] dominoArray={
          R.drawable.dice1,
          R.drawable.dice2,
          R.drawable.dice3,
          R.drawable.dice4,
          R.drawable.dice5,
          R.drawable.dice6,
    };
    private Random random;
    private AlertDialog alertDialog;
    // Ads
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ads
        MobileAds.initialize(this, "ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        rollButton=findViewById(R.id.roll_button);
        leftImage=findViewById(R.id.left_image);
        rightImage=findViewById(R.id.right_image);

        random=new Random();

        alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Random numbers
                int leftRandom=random.nextInt(6);
                int rightRandom=random.nextInt(6);

                // Roll Images
                rollImage(leftRandom, leftImage);
                rollImage(rightRandom, rightImage);

                // Display dialog
                alertDialog.setTitle("Domino");
                alertDialog.setMessage("Congratulations, You win!");
                alertDialog.setIcon(R.drawable.domino);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // Display Ads
                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                } else {
                                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                                }
                            }
                        }
                );
                if(leftRandom==rightRandom){
                    alertDialog.show();
                }
            }
        });
    }

    private void rollImage(int randomValue, ImageView image){
        image.setImageResource(dominoArray[randomValue]);
    }
}
