package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {

    private Integer firstPin;
    private Integer secondPin;
    private TurnKey id;

    @Override
    public Integer getFirstPin() {
        return this.firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return this.secondPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        this.firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin = pin;
    }

    @Override
    public TurnKey getId() {
        return this.id;
    }

    @Override
    public void setId(TurnKey id) {
        this.id = id;
    }
}