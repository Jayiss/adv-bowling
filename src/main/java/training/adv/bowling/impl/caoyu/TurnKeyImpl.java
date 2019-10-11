package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private Integer id, foreignId;

    TurnKeyImpl(Integer foreignId) {
        this.foreignId = foreignId;
        this.id = UidUtil.getNewTurnId();
    }
    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public Integer getForeignId() {
        return null;
    }
}
