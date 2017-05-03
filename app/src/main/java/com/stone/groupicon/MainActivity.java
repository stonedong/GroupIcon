package com.stone.groupicon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stone.mylibrary.widget.GroupIcon;

public class MainActivity extends AppCompatActivity {

    private GroupIcon mGroupIcon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGroupIcon = (GroupIcon) findViewById(R.id.icon_group);

        Bitmap[] bitmaps = new Bitmap[13];
        for (int i = 0; i < bitmaps.length; i++){
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), R.drawable.k006);
        }

        mGroupIcon.setmBitmaps(bitmaps);
    }
}
