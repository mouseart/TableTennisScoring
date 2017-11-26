package mouseart.com.github.tabletennisscoring.data;

import android.app.Application;

/**
 * Created by mouse on 2017/11/26.
 */

public class NowGameApplication extends Application {

    private int nowGameID;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public int getNowGameID() {
        return nowGameID;
    }

    public void setNowGameID(int number) {
        this.nowGameID = number;
    }
}