package com.example.a305.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;


public class settingsActivity extends AppCompatActivity {

    private ImageView mBackbutton;
    public SharedPreferences sharedpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);

        NumberPicker np = (NumberPicker)findViewById(R.id.np);

        final TextView tv = (TextView)findViewById(R.id.tvago);
        np.setMinValue(1);
        np.setMaxValue(24);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
            tv.setText(+newVal+"H Ago");

            sharedpref = PreferenceManager.getDefaultSharedPreferences(settingsActivity.this);
            SharedPreferences.Editor editor = sharedpref.edit();

            editor.putInt("usehdata", newVal);

            editor.commit();



            }


    });
        sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
        int hour = sharedpref.getInt("usehdata", 1);
        np.setValue(hour);

        mBackbutton = (ImageView) findViewById(R.id.ic_backb);

        mBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Switch sw = (Switch)findViewById(R.id.switch1);
        final boolean use4h = sharedpref.getBoolean("use4haurora", false);

        if (use4h){
            sw.setChecked(true);
        }
    }

    public void setaurora4h (View v) {

        Switch sw = (Switch)findViewById(R.id.switch1);

        SharedPreferences sharepref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharepref.edit();

        if (sw.isChecked())
        {
            editor.putBoolean("use4haurora", true);
        }
        else {
            editor.putBoolean("use4haurora", false);
        }
        editor.commit();
    }

}
