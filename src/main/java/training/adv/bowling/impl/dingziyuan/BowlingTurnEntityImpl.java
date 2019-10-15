package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private TurnKey id = new TurnKeyImpl("tid", "gid");
    private Integer[] pins = new Integer[2];

    public BowlingTurnEntityImpl(Integer... pins) {
        if (pins.length == 1)
            this.pins[0] = pins[0];
        if (pins.length == 2) {
            this.pins[0] = pins[0];
            this.pins[1] = pins[1];
        }
    }

    @Override
    public Integer getFirstPin() {
        return pins[0];
    }

    @Override
    public Integer getSecondPin() {
        return pins[1];
    }

    @Override
    public void setFirstPin(Integer pin) {
        pins[0] = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        pins[1] = pin;
    }

    @Override
    public TurnKey getId() {
        return id;
    }

    @Override
    public void setId(TurnKey id) {
        this.id = id;
    }
}
