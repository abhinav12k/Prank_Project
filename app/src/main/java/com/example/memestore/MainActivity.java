package com.example.memestore;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Ringtone currentRingtone;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWallpaper();
        if (Settings.System.canWrite(this)) {
            setRingtone();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Permission Required")
                    .setMessage("Enable the modify setting permission for the app to function properly")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            startActivityForResult(intent,1);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            setRingtone();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setRingtone() {

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/lullu");
        RingtoneManager.setActualDefaultRingtoneUri(MainActivity.this, RingtoneManager.TYPE_ALARM, uri);
        RingtoneManager.setActualDefaultRingtoneUri(MainActivity.this, RingtoneManager.TYPE_NOTIFICATION, uri);
        RingtoneManager.setActualDefaultRingtoneUri(MainActivity.this, RingtoneManager.TYPE_RINGTONE, uri);
//        Toast.makeText(this, "Set as ringtoon successfully ", Toast.LENGTH_SHORT).show();
//
//        Uri currentRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(MainActivity.this, RingtoneManager.TYPE_RINGTONE);
//        currentRingtone = RingtoneManager.getRingtone(MainActivity.this, currentRintoneUri);
//        currentRingtone.play();

    }

    private void setWallpaper() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.memecat);
        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
        try{
            manager.setBitmap(bitmap);
//            Toast.makeText(this, "Wallpaper set!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

}