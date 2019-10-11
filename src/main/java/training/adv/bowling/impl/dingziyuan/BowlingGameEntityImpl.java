package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    @Override
    public Integer getMaxPin() {
        return null;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {

    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return new BowlingTurnEntity[0];
    }

    @Override
    public Integer getMaxTurn() {
        return null;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }
}
