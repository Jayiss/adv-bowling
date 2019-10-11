package training.adv.bowling.impl.group2;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private BowlingTurnEntity[] turnEntities;
    private Integer id;
    private Integer maxTurn;
    private Integer maxPin;



    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {

        turnEntities = turns;
    }


    @Override
    public BowlingTurnEntity[] getTurnEntities() {

        return turnEntities;
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

    @Override
    public Integer getMaxPin() {
        return maxPin;
    }
}