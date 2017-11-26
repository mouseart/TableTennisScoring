package mouseart.com.github.tabletennisscoring.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import static mouseart.com.github.tabletennisscoring.data.GameContract.GameEntry.TABLE_GAME_NAME;

/**
 * Created by mouse on 2017/11/19.
 */

public class GameDao {

    private static final String TAG = "GameDao";

    private Context context;
    private GameDbHelper gameDbHelper;

    private NowGameApplication mNowGameApplication ;


    public GameDao(Context context) {
        this.context = context;
        gameDbHelper = new GameDbHelper(context);
    }


    /**
     * 返回最后一条数据的id
     */
    public int returnLastId(){
        SQLiteDatabase db = null;
        db = gameDbHelper.getWritableDatabase();

        String sql = "select last_insert_rowid() from " + TABLE_GAME_NAME ;
        Cursor cursor = db.rawQuery(sql, null);
        int a = -1;
        if(cursor.moveToFirst()){
            a = cursor.getInt(0);
        }
        return a;
    }

    /**
     * 新增一场比赛
     */
    public boolean insertNewGame(int mTeamAplayerId, int mTeamBplayerId, int mSinglesDoubles, int mGameSets) {

        SQLiteDatabase db = null;

        try {
            db = gameDbHelper.getWritableDatabase();
            db.beginTransaction();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(GameContract.GameEntry.COLUMN_GAME_GAMETITLE,"AAA");
            values.put(GameContract.GameEntry.COLUMN_GAME_GAMESTARTTIME, System.currentTimeMillis());
            values.put(GameContract.GameEntry.COLUMN_GAME_GAMEENDTIME,0);
            values.put(GameContract.GameEntry.COLUMN_GAME_TEAMAPLAYERID1,mTeamAplayerId);
            values.put(GameContract.GameEntry.COLUMN_GAME_TEAMBPLAYERID1,mTeamBplayerId);
            values.put(GameContract.GameEntry.COLUMN_GAME_SINGLESDOUBLES,mSinglesDoubles);
            values.put(GameContract.GameEntry.COLUMN_GAME_GAMESETS,mGameSets);
            values.put(GameContract.GameEntry.COLUMN_GAME_AGGREGATESCOREA,0);
            values.put(GameContract.GameEntry.COLUMN_GAME_AGGREGATESCOREB,0);

            // Insert the new row, returning the primary key value of the new row
            db.insert(TABLE_GAME_NAME, null, values);

            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "insertNewGame:", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;

    }

    /**
     * 修改一场比赛的总比分scoreAllA和scoreAllB
     */
    public boolean updateGameScoreAll(int scoreAllA,int scoreAllB){
        SQLiteDatabase db = null;

        try {
            db = gameDbHelper.getWritableDatabase();
            db.beginTransaction();

            // update Orders set OrderPrice = 800 where Id = 6
            ContentValues cv = new ContentValues();
            cv.put(GameContract.GameEntry.COLUMN_GAME_AGGREGATESCOREA, scoreAllA);
            cv.put(GameContract.GameEntry.COLUMN_GAME_AGGREGATESCOREB, scoreAllB);
            db.update(TABLE_GAME_NAME,cv,GameContract.GameEntry.COLUMN_GAME_ID+"= ?",
                    new String[]{String.valueOf(mNowGameApplication.getNowGameID())});
            db.setTransactionSuccessful();
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "updateGameScoreAll:", e);
        }
        finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return false;
    }

}
