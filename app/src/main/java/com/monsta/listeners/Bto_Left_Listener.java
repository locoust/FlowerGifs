package com.monsta.listeners;

import android.view.View;
import android.widget.ImageButton;

import com.monsta.belasmensagens.MainActivity;
import com.monsta.belasmensagens.R;


/**
 * Created by christianlopez on 24/1/18.
 */
public class Bto_Left_Listener implements View.OnClickListener
{
    private ImageButton btoLeft;
    private MainActivity main;

    public Bto_Left_Listener(MainActivity main)
    {
        this.main = main;

        btoLeft = (ImageButton)this.main.findViewById(R.id.left);
        btoLeft.setOnClickListener(this);

    }
    @Override
    public void onClick(View v)
    {
        if (main.getCont() > 0) {
            main.setCont(main.getCont()-1);
        } else {
            main.setCont(main.getNumImages());
        }

        main.LoagImage(main.getCont());
    }
}
