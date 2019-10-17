package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;


public class BowlingGameEntityImpl implements BowlingGameEntity {
    private  BowlingTurnEntity[] bowlingTurnEntities;
    private Integer id;
    private Integer MaxPin = 10;
    private Integer MaxTurn = 10;
    public BowlingGameEntityImpl(int id){
        this.id = id;
    }
    @Override
    public Integer getMaxPin() {
        return this.MaxPin;
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
        return MaxTurn;
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