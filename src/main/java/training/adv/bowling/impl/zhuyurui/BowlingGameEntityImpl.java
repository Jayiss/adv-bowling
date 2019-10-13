package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private BowlingTurnEntity[] turnEntities;
    private Integer id;
    private int maxPin;
    private int maxTurn;

    public BowlingGameEntityImpl(Integer maxPin,Integer maxTurn){
        this.maxPin=maxPin;
        this.maxTurn=maxTurn;
    }

    public BowlingGameEntityImpl(Integer id,Integer maxPin,Integer maxTurn){
        this(maxPin,maxTurn);
        this.id=id;
    }




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