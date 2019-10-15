package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {

    private Integer id_turn, id_game;  // PK - id_turn, FK - id_game

    TurnKeyImpl(Integer id_turn, Integer id_game) {
        this.id_turn = id_turn;
        this.id_game = id_game;
    }

    @Override
    public Integer getId() {
        return id_turn;
    }

    @Override
    public Integer getForeignId() {
        return id_game;
    }
}
