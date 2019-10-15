package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {

    private Integer firstPin;
    private Integer secondPin;
    TurnKey turnKey;

    public BowlingTurnEntityImpl(Integer firstPin,Integer secondPin,Integer id,Integer foreignId){
        this.firstPin=firstPin;
        this.secondPin=secondPin;
        this.turnKey=new TurnKeyImpl(id,foreignId);
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
        this.firstPin=pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin=pin;
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
