package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.TurnKey;

public class BowlingTurnKeyImpl implements TurnKey {
    private Integer id;
    private Integer foreignId;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getForeignId() {
        return foreignId;
    }
}
