package mouseart.com.github.tabletennisscoring.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static mouseart.com.github.tabletennisscoring.data.GameDbHelper.DATABASE_NAME;
import static mouseart.com.github.tabletennisscoring.data.GameDbHelper.DATABASE_VERSION;

/**
 * Created by mouse on 2017/11/16.
 */
public class GameLogDbHelper extends SQLiteOpenHelper {

    /*public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "TableTennis.db";*/

    private SQLiteDatabase db;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTOINCREMENT = " AUTOINCREMENT";
    private static final String NOT_NULL = " NOT NULL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GameLogContract.GameLogEntry.TABLE_GAMELOG_NAME + " (" +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_ID + INTEGER_TYPE + PRIMARY_KEY + AUTOINCREMENT + COMMA_SEP +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMEID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTIME + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTYPE + INTEGER_TYPE + COMMA_SEP +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMESCORE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GameLogContract.GameLogEntry.TABLE_GAMELOG_NAME;

    //SQLiteOpenHelper子类必须要的一个构造函数
    public GameLogDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //必须通过super 调用父类的构造函数
        super(context, name, null,version);
    }

    //数据库的构造函数，传递三个参数的
    public GameLogDbHelper(Context context, String name, int version){
        this(context, name, null, version);
    }

    public GameLogDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // 回调函数，第一次创建时才会调用此函数，创建一个数据库
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = db;
        System.out.println("Create Database");
        db.execSQL(SQL_CREATE_ENTRIES);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        //再次创建新的表
        onCreate(sqLiteDatabase);

    }

    //关闭数据库
    public void close(){
        if(db != null){
            db.close();
        }
    }
}
