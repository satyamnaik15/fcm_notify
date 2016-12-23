package notify.fcm.com.myapplication.database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import notify.fcm.com.myapplication.MainActivity;
import notify.fcm.com.myapplication.imagedata.ImageData;

/**
 * Created by 1405473 on 23-12-2016.
 */

public class DBHandler extends SQLiteOpenHelper {

    private  static final String DATABASE_NAME = "DemoDatabase";
    private static final String NOTIFICATION_TABLE = "Notifications";
    private static final String NOTIFICATION_ID = "Id";
    private static final String IMAGE_TITLE = "ImageTitle";
    private static final String IMAGE_LINK = "ImageLink";
    private static final String IMAGE_DESCRIPTION = "ImageDesc";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + NOTIFICATION_TABLE + " ( "
        + NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IMAGE_TITLE + " TEXT, "
        + IMAGE_LINK + " TEXT, " + IMAGE_DESCRIPTION + " TEXT" + " );";

        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTIFICATION_TABLE +" ;");
        onCreate(sqLiteDatabase);
    }

    public void insertImageData(ImageData imageData) {
        SQLiteDatabase db = this.getWritableDatabase();
       // String sql = "INSERT INTO "+ NOTIFICATION_TABLE+"(Id,ImageTitle,ImageLink,ImageDesc)" +" VALUES ("+imageData.getId()+","+imageData.getImageTitle()+","+imageData.getImageLink()+","+imageData.getImageDesc()+")";
        //db.execSQL(sql);
        ContentValues values = new ContentValues(); 
        values.put(IMAGE_TITLE, imageData.getImageTitle());
        values.put(IMAGE_LINK, imageData.getImageLink());
        values.put(IMAGE_DESCRIPTION, imageData.getImageDesc());

        db.insert(NOTIFICATION_TABLE, null, values);
        db.close();
    }

    public ArrayList<ImageData> getAllData() {
        ArrayList<ImageData> shopList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + NOTIFICATION_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ImageData imageData = new ImageData();
            imageData.setId(Integer.parseInt(cursor.getString(0)));
            imageData.setImageTitle(cursor.getString(1));
            imageData.setImageLink(cursor.getString(2));
            imageData.setImageDesc(cursor.getString(3));
            shopList.add(imageData);
            cursor.moveToNext();
        }
        cursor.close();
        return shopList;
    }
}
