package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private Integer id;
    private BowlingTurnEntity[] bowlingTurnEntities;
    private Integer maxTurn;
    private Integer maxPin;

    public BowlingGameEntityImpl(Integer id,Integer maxTurn,Integer maxPin){
        this.id=id;
        this.maxTurn=maxTurn;
        this.maxPin=maxPin;
    }


    @Override
    public Integer getMaxPin() {
        return 10;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] bowlingTurnEntities) {
        this.bowlingTurnEntities=bowlingTurnEntities;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return bowlingTurnEntities;
    }

    @Override
    public Integer getMaxTurn() {
       return 10;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
