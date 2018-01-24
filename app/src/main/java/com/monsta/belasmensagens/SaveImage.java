package com.monsta.belasmensagens;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by christianlopez on 24/1/18.
 */
public class SaveImage
{
    private MainActivity main;

    public SaveImage(MainActivity main)
    {
        this.main = main;
    }
    public void Save()
    {
        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)
        {
            Toast.makeText(main, "Salvar a imagem para a sua galeria", Toast.LENGTH_LONG).show();
            File sdcard = Environment.getExternalStorageDirectory();
            if (sdcard != null) {
                File mediaDir = new File(sdcard, "DCIM/Camera");
                if (!mediaDir.exists()) {
                    mediaDir.mkdirs();
                }

                MediaStore.Images.Media.insertImage(main.getContentResolver(), main.getBitmap(), "lindas", null);
            }

        }
        else {

            int permissionCheck = ContextCompat.checkSelfPermission(main, Manifest.permission.WRITE_EXTERNAL_STORAGE);


            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        main, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        main.getMY_PERMISSIONS_WRITE_EXTERNAL_STORAGE_2());
            }
            else {

                SaveNoKitKatVersion();

            }
        }

    }
    public void SaveNoKitKatVersion()
    {
        try {
            saveImageToExternal(main.getResources().getString(R.string.cabecera) + " " + main.getCont()
                    + ".jpg", main.getBitmap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveImageToExternal(String imgName, Bitmap bm) throws IOException {

        Toast.makeText(main, "Salvar a imagem para a sua galeria", Toast.LENGTH_LONG).show();
        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES); //Creates app specific folder
        path.mkdirs();
        File imageFile = new File(path, imgName); // Imagename.png
        FileOutputStream out = new FileOutputStream(imageFile);
        try{
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out); // Compress Image
            out.flush();
            out.close();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(main.getApplicationContext(),
                    new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
        } catch(Exception e) {
            throw new IOException();
        }
    }
}
