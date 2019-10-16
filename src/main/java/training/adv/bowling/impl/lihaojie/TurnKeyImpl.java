package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
    private int id;
    private int game_id;
    public TurnKeyImpl(Integer id,Integer game_id){
        if (id==null||game_id==null){
            throw new NullPointerException();
        }
        this.id=id;
        this.game_id=game_id;
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getForeignId() {
        return game_id;
    }
}
