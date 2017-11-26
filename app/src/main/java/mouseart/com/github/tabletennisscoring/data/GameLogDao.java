package mouseart.com.github.tabletennisscoring.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import mouseart.com.github.tabletennisscoring.R;

/**
 * Created by mouse on 2017/11/19.
 */

public class GameLogDao {

    private static final String TAG = "GameLogDao";

    // 列定义
    private final String[] GAMELOG_COLUMNS = new String[]{
            GameLogContract.GameLogEntry.COLUMN_GAMELOG_ID,
            GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMEID,
            GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTIME,
            GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTYPE,
            GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMESCORE};

    private Context context;
    private GameLogDbHelper gameLogDbHelper;

    public GameLogDao(Context context) {
        this.context = context;
        gameLogDbHelper = new GameLogDbHelper(context);
    }

    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist() {

        int count = 0;

        SQLiteDatabase db = null;
        db = gameLogDbHelper.getReadableDatabase();

        Cursor cursor = null;

        try {

            // select count(Id) from Orders
            cursor = db.query(GameLogContract.GameLogEntry.TABLE_GAMELOG_NAME, new String[]{"COUNT(Id)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) {
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }


    /**
     * 新增一条数据
     */
    public boolean insertData(int gameId, int eventType, String gameScore) {

        SQLiteDatabase db = null;

        try {
            db = gameLogDbHelper.getWritableDatabase();
            db.beginTransaction();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMEID, gameId);
            values.put(GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTIME, System.currentTimeMillis());
            values.put(GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTYPE, eventType);
            values.put(GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMESCORE, gameScore);

            // Insert the new row, returning the primary key value of the new row
            db.insert(GameLogContract.GameLogEntry.TABLE_GAMELOG_NAME, null, values);

            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;

    }

}
