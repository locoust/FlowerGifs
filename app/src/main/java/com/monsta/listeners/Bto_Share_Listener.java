package com.monsta.listeners;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.monsta.belasmensagens.MainActivity;
import com.monsta.belasmensagens.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by christianlopez on 24/1/18.
 */
public class Bto_Share_Listener implements OnClickListener {

    private MainActivity main;
    private ImageButton btoShare;


    public Bto_Share_Listener(MainActivity main)
    {
        this.main = main;
        btoShare = (ImageButton) this.main.findViewById(R.id.share);

        btoShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int permissionCheck = ContextCompat.checkSelfPermission(main, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    main, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    main.getMY_PERMISSIONS_WRITE_EXTERNAL_STORAGE());
        } else {

            Share();
        }
    }
    public void Share()
    {

        // save bitmap to cache directory
        try {

            File cachePath = new File(main.getApplicationContext().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.jpg"); // overwrites this image every time
            main.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File imagePath = new File(main.getApplicationContext().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.jpg");
        Uri contentUri = FileProvider.getUriForFile(main, main.getPackageName(), newFile);



        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, main.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            main.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }
}
