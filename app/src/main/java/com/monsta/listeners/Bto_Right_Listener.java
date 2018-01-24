package com.monsta.listeners;

import android.view.View;
import android.widget.ImageButton;

import com.monsta.belasmensagens.MainActivity;
import com.monsta.belasmensagens.R;


/**
 * Created by christianlopez on 24/1/18.
 */
public class Bto_Right_Listener implements View.OnClickListener
{

    private MainActivity main;
    private ImageButton bto_right;

    public Bto_Right_Listener(MainActivity main)
    {
        this.main = main;
        bto_right = (ImageButton)this.main.findViewById(R.id.right);
        bto_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (main.getCont() < main.getNumImages()) {
            main.setCont(main.getCont()+1);
        } else {
            main.setCont(0);
        }

        if (main.getCont() % 20 == 0|| main.getCont()%30==0) {

                    /*AdRequest adRequest = new AdRequest.Builder().build();

                    // Prepare the Interstitial Ad
                    interstitial = new InterstitialAd(MainActivity.this);
                    // Insert the Ad Unit ID
                    interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

                    interstitial.loadAd(adRequest);
                    // Prepare an Interstitial Ad Listener
                    interstitial.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            // Call displayInterstitial() function
                            displayInterstitial();
                        }
                    });*/

        }

        main.LoagImage(main.getCont());
    }

}
