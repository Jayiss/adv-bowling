package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private Integer firstPin;
    private Integer secondPin;
    private TurnKey turnKey;

    public BowlingTurnEntityImpl(){

    }

    public BowlingTurnEntityImpl(Integer firstPin,Integer secondPin,TurnKey turnKey){
        this.turnKey=turnKey;
        this.firstPin=firstPin;
        this.secondPin=secondPin;
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
        firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        secondPin = pin;
    }

    @Override
    public TurnKey getId() {
        return turnKey;
    }

    @Override
    public void setId(TurnKey id) {
        turnKey = id;
    }

}