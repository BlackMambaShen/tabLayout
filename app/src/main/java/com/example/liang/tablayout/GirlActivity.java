package com.example.liang.tablayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GirlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);
        Intent intent = getIntent();
        String abs = intent.getStringExtra("abs");
        String image_url = intent.getStringExtra("image_url");
        TextView tv_des = (TextView) findViewById(R.id.tv_des);
        ImageView iv_content = (ImageView) findViewById(R.id.iv_content);

        tv_des.setText(abs);
        Glide.with(this).load(image_url).into(iv_content);

    }
}
