package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {

    private Integer id;
    private Integer foreignId;

    public TurnKeyImpl(Integer id,Integer foreignId){
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
}
