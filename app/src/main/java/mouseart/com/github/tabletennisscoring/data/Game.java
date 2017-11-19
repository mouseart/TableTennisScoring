package mouseart.com.github.tabletennisscoring.data;

/**
 * Created by mouse on 2017/11/19.
 */

public class Game {
    public int id;
    public String gameTitle;
    public int gameStartTime;
    public int gameEndTime;
    public int teamAPlayerId1;
    public int teamAPlayerId2;
    public int teamBPlayerId1;
    public int teamBPlayerId2;
    public int singlesDoubles;
    public int gameSets;
    public String gameScoreAB;
    public String aggregateScore;


    public Game() {
    }

    public Game(int id,
                String gameTitle,
                int gameStartTime,
                int gameEndTime,
                int teamAPlayerId1,
                int teamAPlayerId2,
                int teamBPlayerId1,
                int teamBPlayerId2,
                int singlesDoubles,
                int gameSets,
                String gameScoreAB,
                String aggregateScore) {
        this.id = id;
        this.gameTitle = gameTitle;
        this.gameStartTime = gameStartTime;
        this.gameEndTime = gameEndTime;
        this.teamAPlayerId1 = teamAPlayerId1;
        this.teamAPlayerId2 = teamAPlayerId2;
        this.teamBPlayerId1 = teamBPlayerId1;
        this.teamBPlayerId2 = teamBPlayerId2;
        this.teamBPlayerId2 = teamBPlayerId2;
        this.singlesDoubles = singlesDoubles;
        this.gameSets = gameSets;
        this.gameScoreAB = gameScoreAB;
        this.aggregateScore = aggregateScore;
    }
}
