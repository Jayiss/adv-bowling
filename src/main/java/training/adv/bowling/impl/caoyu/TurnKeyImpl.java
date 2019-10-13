package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.TurnKey;

import java.net.Inet4Address;

public class TurnKeyImpl implements TurnKey {
    private Integer id, foreignId;

    TurnKeyImpl(Integer foreignId) {
        this.foreignId = foreignId;
        this.id = UidUtil.getNewTurnId();
    }

    public TurnKeyImpl(Integer id, Integer foreignId) {
        this.id = id;
        this.foreignId = foreignId;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Integer getForeignId() {
        return this.foreignId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TurnKeyImpl) {
            return ((TurnKeyImpl) obj).getForeignId().equals(this.getForeignId()) || ((TurnKeyImpl) obj).getId().equals(this.getId());
        }
        return super.equals(obj);
    }
}

