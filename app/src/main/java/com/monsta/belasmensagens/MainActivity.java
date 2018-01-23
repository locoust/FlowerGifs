package com.monsta.belasmensagens;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by christianlopez on 10/7/16.
 */
public class MainActivity extends Activity
{
    final int  MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_2 =2;

    public static int NUM_IMAGES=39;

    private ImageButton bto_left;
    private ImageButton bto_save;
    private ImageButton bto_share;
    private ImageButton bto_right;
    private ImageView image;
    private int cont;
    private Bitmap bitmap;
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdRequest adRequest = new AdRequest.Builder().build();

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
        });


        bto_left = (ImageButton)findViewById(R.id.left);
        bto_save = (ImageButton)findViewById(R.id.save);
        bto_share = (ImageButton)findViewById(R.id.share);
        bto_right = (ImageButton)findViewById(R.id.right);
        image = (ImageView)findViewById(R.id.image);

        TextView cabezera =(TextView)findViewById(R.id.cabezera);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Baumans-Regular.ttf");
        cabezera.setTypeface(font);

        cont=0;

        LoagImage(cont);

        LoadListeners();
    }
    public void displayInterstitial()
    {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
    private void LoadListeners()
    {
        Right();
        Left();
        SaveImageListener();
        ShareImageListener();
    }
    private void ShareImageListener()
    {
        bto_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                } else {

                    Share();
                }

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    Share();
                }
                break;
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_2:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    SaveNoKitKatVersion();
                }

            default:
                break;
        }
    }
    private void Share()
    {

        // save bitmap to cache directory
        try {

            File cachePath = new File(MainActivity.this.getApplicationContext().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.jpg"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File imagePath = new File(MainActivity.this.getApplicationContext().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.jpg");
        Uri contentUri = FileProvider.getUriForFile(this, "com.monsta.belasmensagens", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }

    }
    private void SaveImageListener()
    {
        bto_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveImage();
            }
        });

    }
    private void Right()
    {
        bto_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cont < NUM_IMAGES) {
                    cont++;
                } else {
                    cont = 0;
                }

                if (cont % 20 == 0|| cont%30==0) {

                    AdRequest adRequest = new AdRequest.Builder().build();

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
                    });

                }

                LoagImage(cont);
            }
        });
    }
    private void Left()
    {
        bto_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cont > 0) {
                    cont--;
                } else {
                    cont = NUM_IMAGES;
                }

                LoagImage(cont);
            }
        });

    }
    private void SaveImage()
    {
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            Toast.makeText(MainActivity.this, "Salvar a imagem para a sua galeria", Toast.LENGTH_LONG).show();
            File sdcard = Environment.getExternalStorageDirectory();
            if (sdcard != null) {
                File mediaDir = new File(sdcard, "DCIM/Camera");
                if (!mediaDir.exists()) {
                    mediaDir.mkdirs();
                }

                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "lindas", null);
            }

        }
        else {

            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_2);
            }
            else {

                SaveNoKitKatVersion();

            }
        }

    }
    private void SaveNoKitKatVersion()
    {
        try {
            saveImageToExternal(getResources().getString(R.string.cabecera)+" "+cont+".jpg",bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveImageToExternal(String imgName, Bitmap bm) throws IOException {

        Toast.makeText(MainActivity.this, "Salvar a imagem para a sua galeria", Toast.LENGTH_LONG).show();
        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //Creates app specific folder
        path.mkdirs();
        File imageFile = new File(path, imgName); // Imagename.png
        FileOutputStream out = new FileOutputStream(imageFile);
        try{
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out); // Compress Image
            out.flush();
            out.close();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(this.getApplicationContext(),
                    new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener()
                    {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
        } catch(Exception e) {
            throw new IOException();
        }
    }
    private void LoagImage(int cont)
    {
        Bitmap bm = getBitmapFromAssets(cont+".jpg");
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
}
