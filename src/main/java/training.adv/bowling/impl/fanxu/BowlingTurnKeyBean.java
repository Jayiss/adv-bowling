package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.TurnKey;

public class BowlingTurnKeyBean implements TurnKey {
    Integer id;
    Integer foreignId;
    public BowlingTurnKeyBean(int id, Integer foreignId)
    {
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
