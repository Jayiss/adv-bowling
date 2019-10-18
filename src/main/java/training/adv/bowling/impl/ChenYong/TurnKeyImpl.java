package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    Integer id;
    Integer foreignId;
    public TurnKeyImpl(int id, Integer foreignId)
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
