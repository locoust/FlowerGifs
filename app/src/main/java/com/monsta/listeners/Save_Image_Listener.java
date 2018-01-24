package com.monsta.listeners;

import android.view.View;
import android.widget.ImageButton;

import com.monsta.belasmensagens.MainActivity;
import com.monsta.belasmensagens.R;

/**
 * Created by christianlopez on 24/1/18.
 */
public class Save_Image_Listener implements View.OnClickListener
{
    private ImageButton bto_save;
    private MainActivity main;

    public Save_Image_Listener(MainActivity main)
    {
        this.main = main;
        bto_save = (ImageButton)this.main.findViewById(R.id.save);
        bto_save.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        main.getSave().Save();
    }
}
