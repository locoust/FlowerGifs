package com.monsta.belasmensagens;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;
import com.monsta.listeners.Bto_Left_Listener;
import com.monsta.listeners.Bto_Right_Listener;
import com.monsta.listeners.Bto_Share_Listener;
import com.monsta.listeners.Save_Image_Listener;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by christianlopez on 10/7/16.
 */
public class MainActivity extends Activity
{
    final int  MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE   = 1;
    final int  MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_2 = 2;

    public static int NUM_IMAGES=39;

    private ImageView image;
    private int cont;
    private Bitmap bitmap;
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    private Bto_Left_Listener btoLeft;
    private Bto_Right_Listener btoRight;
    private SaveImage save;
    private Save_Image_Listener btoSave;
    private Bto_Share_Listener btoShare;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        image = (ImageView)findViewById(R.id.image);

        TextView cabezera =(TextView)findViewById(R.id.cabezera);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Baumans-Regular.ttf");
        cabezera.setTypeface(font);

        cont=0;

        LoagImage(cont);

        save = new SaveImage(this);
        btoLeft = new Bto_Left_Listener(this);
        btoRight = new Bto_Right_Listener(this);
        btoSave = new Save_Image_Listener(this);
        btoShare = new Bto_Share_Listener(this);

    }
    /*public void displayInterstitial()
    {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    btoShare.Share();
                }
                break;
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_2:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    save.SaveNoKitKatVersion();
                }

            default:
                break;
        }
    }
    public void LoagImage(int cont)
    {
        Bitmap bm = getBitmapFromAssets(cont + ".jpg");
        image.setImageBitmap(bm);
    }
    public Bitmap getBitmapFromAssets(String fileName)
    {
        AssetManager assetManager = getAssets();

        InputStream istr = null;
        try {
            istr = assetManager.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = BitmapFactory.decodeStream(istr);

        return bitmap;
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public int getCont() {
        return cont;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public InterstitialAd getInterstitial() {
        return interstitial;
    }

    public static int getNumImages() {
        return NUM_IMAGES;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public int getMY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_2() {
        return MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_2;
    }

    public int getMY_PERMISSIONS_WRITE_EXTERNAL_STORAGE() {
        return MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE;
    }

    public ImageView getImage() {
        return image;
    }

    public InterstitialAd getmInterstitialAd() {
        return mInterstitialAd;
    }
    public SaveImage getSave() {
        return save;
    }
}
