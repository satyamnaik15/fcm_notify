package notify.fcm.com.myapplication.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.RingtoneManager;

import android.os.AsyncTask;

import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import notify.fcm.com.myapplication.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private int uniqueID = 0;
    private SharedPreferences sharedpreferences;

    private String refreshedToken;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("MSG RECEIVED", "==GENERATE NOTIFICATION ");

//        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Random random = new Random();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        uniqueID = random.nextInt(9999 - 1000) + 1000;
        try {

//            String title = remoteMessage.getNotification().getTitle();
//            String message = remoteMessage.getNotification().getBody();

            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("desc");
            String image = remoteMessage.getData().get("image");
            Log.e("MSG RECEIVED VALUES", "== "+title+" - "+message+" - "+image);

                send(image,title,message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void send(String imageUri, String title, String message) {
        if (imageUri != null && imageUri.trim().length() > 0) {
            withImageNotification(title, message, imageUri);
        } else {
            noImageNotification(title, message);
        }
    }


    private void noImageNotification(String tittle, String description) {
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(tittle)
                .setContentText(description)
                .setAutoCancel(true)
                .setContentIntent(null)
                .build();

        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationManager.notify(uniqueID, notification);
    }

    private void withImageNotification(String tittle, String description, String imageUri) {
        new sendNotification(tittle, description, imageUri, this).execute();
    }

    private Bitmap setlargicon(int ic_swalekh, Bitmap remote_picture) {

        try {


            return BitmapFactory.decodeResource(getResources(), ic_swalekh);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return remote_picture;
    }

    private class sendNotification extends AsyncTask<String, Void, Bitmap> {


        Context ctx;
        String imageUri, description, tittle;


        sendNotification(String tittle, String description, String imageUri,  MyFirebaseMessagingService myFirebaseMessagingService) {
            this.description = description;
            this.tittle = tittle;
            this.imageUri = imageUri;
            ctx = myFirebaseMessagingService;
        }

        Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();

            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        }

        @Override
        protected Bitmap doInBackground(String... params) {


            InputStream in;

            try {

                URL url = new URL(imageUri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                connection.setConnectTimeout(50000);
                connection.setReadTimeout(50000);
                connection.setInstanceFollowRedirects(true);
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);


                if ((myBitmap != null) && (myBitmap.getByteCount() > 3754640)) {
                    myBitmap = getResizedBitmap(myBitmap, 1024, 860);


                }

                return myBitmap;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap remote_picture) {

            super.onPostExecute(remote_picture);

            if (remote_picture != null) {

                try {
                    NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();


                    // Add the big picture to the style.
                    notiStyle.bigPicture(remote_picture);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ctx)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(tittle)
                            .setContentText(description)
                            .setAutoCancel(true)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setContentIntent(null)
                            .setLargeIcon(setlargicon(R.mipmap.ic_launcher, remote_picture))
                            .setStyle(notiStyle);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(uniqueID, notificationBuilder.build());
                   putShare(imageUri,tittle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                noImageNotification(tittle, description);
                putShare(imageUri,tittle);
            }
        }
    }

    private void putShare(String imageUri,String tittle) {
        final Intent intent = new Intent("notify");
        // You can also include some extra data.
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        intent.putExtra("valueFrom", 9);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("image7",sharedpreferences.getString("image6","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg6.jpg?alt=media&token=8cb77772-495b-4d2a-8c8f-b8f634f00115") );
        editor.apply();
        editor.putString("tittle7",sharedpreferences.getString("tittle6","tittle6") );
        editor.apply();
        editor.putString("image6",sharedpreferences.getString("image5","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg5.jpg?alt=media&token=bbe3e7ee-20e8-476b-8b06-07be7e097b56") );
        editor.apply();
        editor.putString("tittle6",sharedpreferences.getString("tittle5","tittle5") );
        editor.apply();

        editor.putString("image5",sharedpreferences.getString("image4","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg4.jpg?alt=media&token=1936fa9b-10c6-4043-8286-984b5e068d61") );
        editor.apply();
        editor.putString("tittle5",sharedpreferences.getString("tittle4","tittle4") );
        editor.apply();

        editor.putString("image4",sharedpreferences.getString("image3","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg3.jpg?alt=media&token=095b0441-ab22-43de-a455-2d2f98859fa1") );
        editor.apply();
        editor.putString("tittle4",sharedpreferences.getString("tittle3","tittle3") );
        editor.apply();

        editor.putString("image3",sharedpreferences.getString("image2","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg2.jpg?alt=media&token=f7f99661-1356-46ed-b574-96c0fb4bbdfd") );
        editor.apply();
        editor.putString("tittle3",sharedpreferences.getString("tittle2","tittle2") );
        editor.apply();

        editor.putString("image2",sharedpreferences.getString("image1","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg1.jpg?alt=media&token=fa40f957-bb5d-422a-b00f-c697ba8d93bb") );
        editor.apply();
        editor.putString("tittle2",sharedpreferences.getString("tittle1","tittle1") );
        editor.apply();

        editor.putString("image1",(imageUri) );
        editor.apply();
        editor.putString("tittle1",tittle);
        editor.apply();

        broadcastManager.sendBroadcast(intent);
    }


}
