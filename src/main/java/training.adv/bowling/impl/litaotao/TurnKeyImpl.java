package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private Integer id;
    private Integer foreignId;

    public TurnKeyImpl(Integer id,Integer foreignId){
        if (id==null||foreignId==null)
            throw new NullPointerException();

        this.id=id;
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
