package notify.fcm.com.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import notify.fcm.com.myapplication.database.DBHandler;
import notify.fcm.com.myapplication.imagedata.ImageData;

public class MainActivity extends AppCompatActivity {

    private ImageView img1,img2,img3,img4,img5,img6,img7;
    private RelativeLayout rel1,rel2,rel3,rel4,rel5,rel6,rel7;
    private String[] imagLink = new String[10];
    private String[] tittlelist = new String[10];
    private SharedPreferences sharedpreferences;
    private BroadcastReceiver displayPlaceBroadcastReceiver;
    private TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        iniImageLinkInArray();
        initLoadImage();
        initSizeDefine();

        registerDisplayBroadcast();

        // INITIALIZING LOCAL DB
        db = new DBHandler(this);



        //PROVIDING TEMPORARY DATA TO APP
        ImageData imageData = new ImageData();
        imageData.setImageTitle("One");
        imageData.setImageLink("One_Link");
        imageData.setImageDesc("One_Desc");
        db.insertImageData(imageData);
        ArrayList<ImageData> data = db.getAllData();
        imageData = data.get(0);
        Toast.makeText(this,imageData.getId()+"\n"+imageData.getImageTitle()+"\n"+imageData.getImageLink()+"\n"+imageData.getImageDesc()+"\n",Toast.LENGTH_LONG).show();

    }

    private void registerDisplayBroadcast() {
        displayPlaceBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int valueFrom = intent.getIntExtra("valueFrom", 0);
                if (valueFrom == 9) {

                    iniImageLinkInArray();
                    initLoadImage();

                }

            }


        };
        LocalBroadcastManager.getInstance(this).registerReceiver(displayPlaceBroadcastReceiver,
                new IntentFilter("notify"));
    }


    private void iniImageLinkInArray() {

        imagLink[0]=sharedpreferences.getString("image1", "https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg1.jpg?alt=media&token=fa40f957-bb5d-422a-b00f-c697ba8d93bb");
        imagLink[1]=sharedpreferences.getString("image2","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg2.jpg?alt=media&token=f7f99661-1356-46ed-b574-96c0fb4bbdfd");
        imagLink[2]=sharedpreferences.getString("image3","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg3.jpg?alt=media&token=095b0441-ab22-43de-a455-2d2f98859fa1");
        imagLink[3]=sharedpreferences.getString("image4","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg4.jpg?alt=media&token=1936fa9b-10c6-4043-8286-984b5e068d61");
        imagLink[4]=sharedpreferences.getString("image5","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg5.jpg?alt=media&token=bbe3e7ee-20e8-476b-8b06-07be7e097b56");
        imagLink[5]=sharedpreferences.getString("image6","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg6.jpg?alt=media&token=8cb77772-495b-4d2a-8c8f-b8f634f00115");
        imagLink[6]=sharedpreferences.getString("image7","https://firebasestorage.googleapis.com/v0/b/notify-7797a.appspot.com/o/fcm.notify%2Fimg7.jpg?alt=media&token=03f4d9a5-7ac9-46c0-bd0d-1a7f08966ab3");

        tittlelist[0]=sharedpreferences.getString("tittle1", "tittle1");
        tittlelist[1]=sharedpreferences.getString("tittle2", "tittle2");
        tittlelist[2]=sharedpreferences.getString("tittle3", "tittle3");
        tittlelist[3]=sharedpreferences.getString("tittle4", "tittle4");
        tittlelist[4]=sharedpreferences.getString("tittle5", "tittle5");
        tittlelist[5]=sharedpreferences.getString("tittle6", "tittle6");
        tittlelist[6]=sharedpreferences.getString("tittle7", "tittle7");
    }

    private void initSizeDefine() {
        rel1.getLayoutParams().height = (int) PixelUtils.dpToPixel(MainActivity.this,200);
        rel1.requestLayout();
        rel2.getLayoutParams().height = (int) PixelUtils.dpToPixel(MainActivity.this,200);
        rel2.requestLayout();
        rel3.getLayoutParams().height = (int) PixelUtils.dpToPixel(MainActivity.this,200);
        rel3.requestLayout();
        rel4.getLayoutParams().height = (int) PixelUtils.dpToPixel(MainActivity.this,200);
        rel4.requestLayout();
        rel5.getLayoutParams().height = (int) PixelUtils.dpToPixel(MainActivity.this,200);
        rel5.requestLayout();
        rel6.getLayoutParams().height = (int) PixelUtils.dpToPixel(MainActivity.this,200);
        rel6.requestLayout();
        rel7.getLayoutParams().height = (int) PixelUtils.dpToPixel(MainActivity.this,200);
        rel7.requestLayout();

    }

    private void initLoadImage() {

        Picasso.with(this)
                .load(imagLink[0])
                .into(img1);
        txt1.setText(tittlelist[0]);
        txt1.invalidate();
        img1.refreshDrawableState();

        Picasso.with(this)
                .load(imagLink[1])
                .into(img2);
        txt2.setText(tittlelist[1]);
        txt2.invalidate();
        img2.refreshDrawableState();

        Picasso.with(this)
                .load(imagLink[2])
                .into(img3);
        txt3.setText(tittlelist[2]);
        txt3.invalidate();
        img3.refreshDrawableState();

        Picasso.with(this)
                .load(imagLink[3])
                .into(img4);
        txt4.setText(tittlelist[3]);
        txt4.invalidate();
        img4.refreshDrawableState();

        Picasso.with(this)
                .load(imagLink[4])
                .into(img5);
        img5.refreshDrawableState();
        txt5.setText(tittlelist[4]);
        txt5.invalidate();

        Picasso.with(this)
                .load(imagLink[5])
                .into(img6);
        img6.refreshDrawableState();
        txt6.setText(tittlelist[5]);
        txt6.invalidate();

        Picasso.with(this)
                .load(imagLink[6])
                .into(img7);
        txt7.setText(tittlelist[6]);
        txt7.invalidate();
        img7.refreshDrawableState();
    }

    private void initLayout() {

        img1=(ImageView)findViewById(R.id.imageView1);
        img2=(ImageView)findViewById(R.id.imageView2);
        img3=(ImageView)findViewById(R.id.imageView3);
        img4=(ImageView)findViewById(R.id.imageView4);
        img5=(ImageView)findViewById(R.id.imageView5);
        img6=(ImageView)findViewById(R.id.imageView6);
        img7=(ImageView)findViewById(R.id.imageView7);
        txt1=(TextView) findViewById(R.id.editText1);
        txt2=(TextView) findViewById(R.id.editText2);
        txt3=(TextView) findViewById(R.id.editText3);
        txt4=(TextView) findViewById(R.id.editText4);
        txt5=(TextView) findViewById(R.id.editText5);
        txt6=(TextView) findViewById(R.id.editText6);
        txt7=(TextView) findViewById(R.id.editText7);

        rel1=(RelativeLayout)findViewById(R.id.rel1);
        rel2=(RelativeLayout)findViewById(R.id.rel2);
        rel3=(RelativeLayout)findViewById(R.id.rel3);
        rel4=(RelativeLayout)findViewById(R.id.rel4);
        rel5=(RelativeLayout)findViewById(R.id.rel5);
        rel6=(RelativeLayout)findViewById(R.id.rel6);
        rel7=(RelativeLayout)findViewById(R.id.rel7);
    }
}
