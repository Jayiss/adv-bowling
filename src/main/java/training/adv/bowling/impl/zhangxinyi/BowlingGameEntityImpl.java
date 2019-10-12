package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private Integer id;
    private Integer maxPin = 10;
    private Integer maxTurn = 10;

    @Override
    public Integer getMaxPin() {
        return maxPin;
    }

    @Override
    // Only used in DB
    public void setTurnEntities(BowlingTurnEntity[] turns) {

    }

    @Override
    // Only used in DB
    public BowlingTurnEntity[] getTurnEntities() {
        return new BowlingTurnEntity[0];
    }

    @Override
    public Integer getMaxTurn() {
        return maxTurn;
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
