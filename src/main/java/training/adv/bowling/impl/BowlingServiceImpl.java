package training.adv.bowling.impl;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.dingziyuan.BowlingTurnEntityImpl;
import training.adv.bowling.impl.dingziyuan.BowlingTurnImpl;
import training.adv.bowling.impl.dingziyuan.dao.BowlingGameDaoImpl;
import training.adv.bowling.impl.dingziyuan.dao.BowlingTurnDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BowlingServiceImpl implements BowlingService {
    //TODO: implement DBUtil
    private Connection connection = DBUtil.getConnection();

    private BowlingGameDao gameDao = new BowlingGameDaoImpl(connection);
    private BowlingTurnDao turnDao = new BowlingTurnDaoImpl(connection);

    @Override
    public void save(BowlingGame game) {
        gameDao.save(game);
        for (BowlingTurn turn : game.getTurns()) {
            turnDao.save(turn);
        }
        commit();
    }

    @Override
    public BowlingGame load(String id) {
        BowlingGame game = gameDao.load(id);
        List<BowlingTurnEntity> bowlingTurnEntities = turnDao.batchLoad(id);
        if(bowlingTurnEntities.size()==0)
            return game;
        Collections.sort(bowlingTurnEntities, new Comparator<BowlingTurnEntity>() {
            public int compare(BowlingTurnEntity arg0, BowlingTurnEntity arg1) {
                Integer arg0Id = Integer.parseInt(arg0.getId().getId());
                Integer arg1Id = Integer.parseInt(arg1.getId().getId());
                return arg0Id.compareTo(arg1Id);
            }
        });

        game.getEntity().setTurnEntities(bowlingTurnEntities.toArray(new BowlingTurnEntity[0]));



        BowlingTurn current = game.getFirstTurn();
        current.getEntity().setId(bowlingTurnEntities.get(0).getId());
        current.getEntity().setFirstPin(bowlingTurnEntities.get(0).getFirstPin());
        current.getEntity().setSecondPin(bowlingTurnEntities.get(0).getSecondPin());
        BowlingTurn pre = current;
        BowlingTurn cursor;
        for (int i = 1; i < bowlingTurnEntities.size(); i++) {
            cursor = new BowlingTurnImpl(game.getEntity().getMaxTurn(),
                    game.getEntity().getMaxPin(), null, pre);
            cursor.getEntity().setId(bowlingTurnEntities.get(i).getId());
            cursor.getEntity().setFirstPin(bowlingTurnEntities.get(i).getFirstPin());
            cursor.getEntity().setSecondPin(bowlingTurnEntities.get(i).getSecondPin());
            pre.getAsLinkedNode().setNextItem(cursor);
            pre = cursor;
        }
        BowlingTurn[] turns = game.getTurns();
        return game;
    }

    @Override
    public void remove(String id) {
        gameDao.remove(id);
        turnDao.batchRemove(id);
        commit();
    }

    private void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
