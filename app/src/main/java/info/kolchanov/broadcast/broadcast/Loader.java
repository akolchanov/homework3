package info.kolchanov.broadcast.broadcast;

import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class Loader extends Service {


    public int onStartCommand(Intent intent, int flags, int startId) {

        final String link = "http://www.tisptech.com/Uploads/aizei/Picture/2016-02-16/56c270c35b10c.jpg";

        final String fileName = "56c270c35b10c.jpg";

        Log.d("app", "Called service");


        new AsyncTask<Void, Void, Void>() {

            protected Void doInBackground(Void... voids) {

                HttpURLConnection connection = null;
                InputStream networkRead = null;
                FileOutputStream fileWrite = null;

                File image = new File(getFilesDir(), fileName);

                if (fileExistOrDownloaded(image)) {

                    try {
                        connection = (HttpURLConnection) new URL(link).openConnection();
                        networkRead = new BufferedInputStream(connection.getInputStream());
                        fileWrite = new FileOutputStream(image);
                        byte[] bufer = new byte[20000];
                        int length;
                        while ((length = networkRead.read(bufer)) != -1) {
                            fileWrite.write(bufer, 0, length);
                        }
                    } catch (IOException e) {
                        Log.d("app", "Network error");
                    }

                }


                sendBroadcast(new Intent("Downloaded"));


                try {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (networkRead != null) {
                        networkRead.close();
                    }
                    if (fileWrite != null) {
                        fileWrite.close();
                    }
                } catch (IOException e) {
                    Log.d("app", "Error while closing connection");
                }


                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return 0;
    }


    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean fileExistOrDownloaded(File file) {
        return  BitmapFactory.decodeFile(file.getAbsolutePath()) == null || !file.exists();
    }
}