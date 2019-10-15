package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private Integer id;
    private Integer foreignId;

    public TurnKeyImpl(Integer id,Integer foreignId){
        this.id=id;
        this.foreignId=foreignId;
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null)return false;
        if(obj instanceof TurnKey){
            TurnKey tmp=(TurnKey)obj;
            int id=tmp.getId();
            int foreignId=tmp.getForeignId();
            return this.id==id && this.foreignId==foreignId;
        }
        return false;
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
