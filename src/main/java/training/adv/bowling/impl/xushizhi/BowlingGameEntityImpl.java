package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {

    private static int MAX_PIN = 10;  // Set default bowling game max pin to 10
    private static int MAX_TURN = 10;  // Set default bowling game max turn to 10
    private Integer id;  // DB Game id_game
    private BowlingTurnEntity[] bowlingTurnEntities;

    public BowlingGameEntityImpl(Integer id, int Max_Turn, int Max_Pin) {
        this.id = id;
        MAX_TURN = Max_Turn;
        MAX_PIN = Max_Pin;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return bowlingTurnEntities;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.bowlingTurnEntities = turns;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }
}
