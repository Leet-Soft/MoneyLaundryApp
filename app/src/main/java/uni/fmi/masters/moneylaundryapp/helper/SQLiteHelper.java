package uni.fmi.masters.moneylaundryapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import uni.fmi.masters.moneylaundryapp.entity.UserEntity;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "money.db";
    public static final int DB_VERSION = 1;

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_AVATAR = "avatar";

    public static final String USER_CREATE_QUERY = "CREATE TABLE Users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name varchar(255)," +
            "email varchar(255) UNIQUE NOT NULL," +
            "password varchar(255) NOT NULL," +
            "username varchar(255) UNIQUE NOT NULL," +
            "avatar varchar(255)" +
            ");";



    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(USER_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public UserEntity login(String username, String password){

        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT * FROM users WHERE "
                + COLUMN_USERNAME + " = '" + username +  "' "
                + " AND " + COLUMN_PASSWORD + " = '" + password + "'";

        Cursor c = db.rawQuery(rawQuery, null);

//        String rawQuery2 = "SELECT * FROM users WHERE "
//                + COLUMN_USERNAME + " = '?' AND " + COLUMN_PASSWORD
//                + " = '?'";
//
//        Cursor c2 = db.rawQuery(rawQuery2, new String[] {username, password});
//

        if(c.moveToFirst()){
            int id = c.getInt(c.getColumnIndexOrThrow(COLUMN_ID));
            String avatar = c.getString(c.getColumnIndexOrThrow(COLUMN_AVATAR));
            String email = c.getString(c.getColumnIndexOrThrow(COLUMN_EMAIL));
            String name = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME));

            UserEntity user = new UserEntity(username, password, email,name);
            user.setId(id);
            user.setAvatarPath(avatar);

            return user;
        }

        return null;
    }

    public boolean register(UserEntity user){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_AVATAR, user.getAvatarPath());
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_NAME, user.getFullName());

        if(db.insert("users",null,cv) != -1)
            return true;

        return false;
    }
}
