package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame,Integer> implements BowlingGameDao {
    private Connection connection;
    public BowlingGameDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int maxTurn = entity.getMaxTurn();
        int id = entity.getId();
        int maxPin = entity.getMaxPin();
        try {
            stmt.executeUpdate("insert into game(id,maxTurn,maxPin) values("+id+","+maxTurn+","+maxPin+")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected BowlingGameEntity doLoad(Integer id){
        BowlingGameEntity bowlingGameEntity = null;

        Integer maxTurn = null;
        Integer maxPin = null;

        try {
            Statement stmt = connection.createStatement();
            //ResultSet rs = stmt.executeQuery("select * from game,turn where game.id="+id+" and game.id=turn.id");
            ResultSet rs = stmt.executeQuery("select * from game where id="+id);
            if(rs.next()) {
                bowlingGameEntity=new BowlingGameEntityImpl(id);
                bowlingGameEntity.setId(id);
                return bowlingGameEntity;
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity){

        return null;
    }

    @Override
    public boolean remove(Integer key) {
        int flag = 0;
        try {
            Statement stmt = connection.createStatement();
            flag = stmt.executeUpdate("delete from game where id="+key);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag>0?true:false;
    }
}

