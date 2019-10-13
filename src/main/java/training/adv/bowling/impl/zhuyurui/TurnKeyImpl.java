package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.TurnKey;

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
}
