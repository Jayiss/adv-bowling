package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {

    private Integer maxPin;
    private Integer maxTurn;
    private Integer id;
    private BowlingTurnEntity[] bowlingTurnEntities;

    @Override
    public Integer getMaxPin() {
        return this.maxPin;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.bowlingTurnEntities = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return this.bowlingTurnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return this.maxTurn;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
