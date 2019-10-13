package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.TurnKey;

public class BowlingTurnKeyImpl implements TurnKey {
    private Integer id;
    private Integer foreignId;

    public BowlingTurnKeyImpl(Integer id, Integer foreignId) {
        this.id = id;
        this.foreignId = foreignId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getForeignId() {
        return foreignId;
    }
}
