package training.adv.bowling.impl.group2;

import training.adv.bowling.api.TurnKey;

import java.util.Objects;

public class TurnKeyImpl implements TurnKey {
    private int id;
    private int foreignId;

    public TurnKeyImpl(int id,int foreignId){
        this.id=id;
        this.foreignId =foreignId;

    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getForeignId() {

        return foreignId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TurnKeyImpl)) return false;
        TurnKeyImpl turnKey = (TurnKeyImpl) o;
        return id == turnKey.id &&
                foreignId == turnKey.foreignId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, foreignId);
    }
}
