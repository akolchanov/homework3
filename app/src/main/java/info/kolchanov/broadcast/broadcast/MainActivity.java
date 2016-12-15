package info.kolchanov.broadcast.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private File imageFile;
    private BroadcastReceiver ImageDownloaded;
    private BroadcastReceiver KeyPressed;

    private TextView errorField;
    private ImageView imageField;

    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageField = (ImageView) findViewById(R.id.image);
        errorField = (TextView) findViewById(R.id.errorText);



        KeyPressed = new MyBroadcastReceiver();
        ImageDownloaded = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {



                imageFile = new File(getFilesDir(), "56c270c35b10c.jpg");

                if (imageFile.exists()) {
                    imageField.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
                    errorField.setVisibility(View.INVISIBLE);
                    imageField.setVisibility(View.VISIBLE);

                }
            }
        };

        registerReceiver(KeyPressed, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(ImageDownloaded, new IntentFilter("Downloaded"));
    }

    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(KeyPressed);
        unregisterReceiver(ImageDownloaded);

    }
}
