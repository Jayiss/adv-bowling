package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameEntityImpl implements BowlingGameEntity {

    private Integer maxPin;
    private Integer maxTurn;
    private BowlingTurn turn;
    private Integer id;

    public BowlingGameEntityImpl(Integer maxPin,Integer maxTurn,Integer id){
        this.maxPin=maxPin;
        this.maxTurn=maxTurn;
        this.turn=null;
        this.id=id;
    }

    public BowlingGameEntityImpl(){

    }

    @Override
    public Integer getMaxPin() {
        return maxPin;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        Integer length=turns.length;
        for(int i=0;i<length;i++){
            BowlingTurn turn=new BowlingTurnImpl(turns)
            turns[i]
        }
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return turns;
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
        this.id=id;
    }
}
