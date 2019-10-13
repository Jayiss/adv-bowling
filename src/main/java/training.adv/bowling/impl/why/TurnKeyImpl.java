package training.adv.bowling.impl.why;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private Integer id;
    private Integer gameId;
    public TurnKeyImpl(Integer id,Integer gameId){
        if (id==null||gameId==null)throw new NullPointerException();
        this.id=id;
        this.gameId=gameId;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getForeignId() {
        return gameId;
    }
}
