package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private Integer firstPin;
    private Integer secondPin;
    private TurnKey turnKey;

    public BowlingTurnEntityImpl(){
        this(null,null,null);
    }

    public BowlingTurnEntityImpl(Integer firstPin){
            this(firstPin,null,null);
    }

    public BowlingTurnEntityImpl(Integer firstPin,Integer secondPin){
           this(firstPin,secondPin,null);
    }

    public BowlingTurnEntityImpl(Integer firstPin,Integer secondPin,TurnKey turnKey){
            this.firstPin=firstPin;
            this.secondPin=secondPin;
            this.turnKey=turnKey;
    }


    @Override
    public Integer getFirstPin() {
       return firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return secondPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        this.firstPin=firstPin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin=secondPin;
    }

    @Override
    public TurnKey getId() {
        return turnKey;
    }

    @Override
    public void setId(TurnKey id) {
        this.turnKey=turnKey;
    }
}
