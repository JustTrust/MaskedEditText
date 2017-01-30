package com.belichenko.a.maskededittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaskedEditText mEditText1 = (MaskedEditText) findViewById(R.id.first);
        MaskedEditText mEditText2 = (MaskedEditText) findViewById(R.id.second);
        MaskedEditText mEditText3 = (MaskedEditText) findViewById(R.id.third);
        MaskedEditText mEditText4 = (MaskedEditText) findViewById(R.id.quart);

        mEditText4.setPointColor(R.color.colorPoint);
        mEditText2.setPointDrawable(R.drawable.piq_4);
    }
}
