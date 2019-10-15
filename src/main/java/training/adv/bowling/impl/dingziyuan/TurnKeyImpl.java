package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.TurnKey;

import java.util.Objects;

public class TurnKeyImpl implements TurnKey {
    private String id;
    private String foreignId;

    public TurnKeyImpl(String id, String foreignId) {
        this.id = id;
        this.foreignId = foreignId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getForeignId() {
        return foreignId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnKeyImpl turnKey = (TurnKeyImpl) o;
        return Objects.equals(id, turnKey.id) &&
                Objects.equals(foreignId, turnKey.foreignId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foreignId);
    }
}
