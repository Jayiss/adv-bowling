package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private Integer id;
    private Integer foreignId;

    public TurnKeyImpl(Integer id,Integer foreignId) {
        this.id=id;
        this.foreignId=foreignId;
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
    public boolean equals(Object anObject){
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof TurnKey) {
            TurnKey anotherObject= (TurnKey)anObject;
            int id=anotherObject.getId();
            int foreignId=anotherObject.getForeignId();
            if(id==this.getId()&&foreignId==this.getForeignId()){
                return true;
            }
        }
        return false;
    }
}
