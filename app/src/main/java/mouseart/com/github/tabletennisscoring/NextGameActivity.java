package mouseart.com.github.tabletennisscoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mouseart.com.github.tabletennisscoring.data.GameDao;
import mouseart.com.github.tabletennisscoring.data.GameLogDao;


/**
 * NextGameActivity class
 * 开始下一局
 * @author Leon Feng
 * @date 2017/11/16
 */
public class NextGameActivity extends AppCompatActivity {

    GameDao mGameDao = new GameDao(this);
    GameLogDao mGameLogDao = new GameLogDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_game);
    }


}
