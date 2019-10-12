package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame,Integer>{

    private static Connection conn;
    public BowlingGameDaoImpl(Connection connection){
        this.conn= connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        if(null!=doLoad(entity.getId())){
            return;
        }
        String sql="insert into game_table(game_id,maxTurn,maxPin) values(?,?,?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setObject(1, entity.getId());
            pstm.setObject(2, entity.getMaxTurn());
            pstm.setObject(3, entity.getMaxPin());
            pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        String sql="select * from game_table where game_id=?";
        BowlingGameEntityImpl bowlingGameEntity=null;
        try{
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setObject(1, id);
            ResultSet rs=pstm.executeQuery();
            while(rs.next()){
                bowlingGameEntity=new BowlingGameEntityImpl(id,rs.getInt("maxTurn"),rs.getInt("maxPin"));
                bowlingGameEntity.setId(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bowlingGameEntity;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingGame game=new BowlingGameImpl(entity);
        return game;
    }

    @Override
    public boolean remove(Integer key) {
        String sql="delete from game_table where game_id=?";
        try{
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setObject(1, key);
            return pstm.executeUpdate()>=0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
