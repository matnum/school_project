package com.example.a305.splashscreen;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class auroraActivity extends AppCompatActivity {


   ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aurora_main);
        

        if (hasPermissions(this, PERMISSIONS)) {
            enableCamera();
        } else {
            // IF USING INSIDE FRAGMENT:
            // requestPermissions(PERMISSIONS, APP_REQUEST_CODE);
            ActivityCompat.requestPermissions(this, PERMISSIONS, APP_REQUEST_CODE);
        }
    }

    final int APP_REQUEST_CODE = 1234;

    String[] PERMISSIONS = {
            Manifest.permission.CAMERA};

    public void enableCamera() {
        ImageButton btnCamera = (ImageButton) findViewById(R.id.imageButton4);
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, 0);
        /*btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/

                // do something if permissions are ok
                // for example, enable location on google map.
                // (as the name of this method implies)

            }



    // this request code should be unique for each permission request in your app
    // often apps only ask permissions only in one spot, so this can be pretty much anything

    public static boolean hasPermissions(Context context, String... permissions) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {

        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        try {
            switch (requestCode) {
                case APP_REQUEST_CODE: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        enableCamera();

                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("This application needs certain permissions to run. You can allow these permissions after restarting this application.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // do whatever you wish to do if user will not give permission
                                        finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(auroraActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        /*if (data != null) {
            if (data.getExtras() != null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }


        }*/
    }
}

