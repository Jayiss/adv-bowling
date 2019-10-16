package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private  BowlingTurnEntity[] bowlingTurnEntities;
    private Integer id;
    private Integer MAX_TURN;
    private Integer MAX_PIN;
    public BowlingGameEntityImpl(int id,int maxTurn,int maxPin){
        this.id = id;
        this.MAX_PIN = maxPin;
        this.MAX_TURN = maxTurn;
    }
    @Override
    public Integer getMaxPin() {
        return this.MAX_PIN;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.bowlingTurnEntities = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return bowlingTurnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}