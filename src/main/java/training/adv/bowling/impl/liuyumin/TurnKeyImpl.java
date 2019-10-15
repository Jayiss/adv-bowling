package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private Integer id;
    private Integer foreignId;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Integer getForeignId() {
        return this.foreignId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
    }
}
