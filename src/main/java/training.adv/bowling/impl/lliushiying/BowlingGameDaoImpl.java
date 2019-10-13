package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
        BowlingTurnEntity[] entities=entity.getTurnEntities();
        String sql1="insert into game_table(game_id,maxTurn,maxPin) values(?,?,?)";
        String sql2="insert into turns_table(turn_id,firstpin,secondpin,game_id)" +
                "values(?,?,?,?)";
        try {
            //insert game_table
            PreparedStatement pstm1 = conn.prepareStatement(sql1);
            pstm1.setObject(1, entity.getId());
            pstm1.setObject(2, entity.getMaxTurn());
            pstm1.setObject(3, entity.getMaxPin());
            pstm1.executeUpdate();

            //insert turns_table
            for(BowlingTurnEntity turnEntity:entities){
                if(checkForTurnEntity(entity.getId(),turnEntity.getId().getId())){
                    return;
                }
                PreparedStatement pstm2 = conn.prepareStatement(sql2);
                pstm2.setObject(1, turnEntity.getId().getId());
                pstm2.setObject(2, turnEntity.getFirstPin());
                pstm2.setObject(3, turnEntity.getSecondPin());
                pstm2.setObject(4, turnEntity.getId().getForeignId());
                pstm2.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkForTurnEntity(Integer foreignId,Integer turnId){
        String sql="select * from turns_table where turn_id=? and game_id=?";
        BowlingTurnEntity bowlingTurnEntity=null;
        try{
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setObject(1, turnId);
            pstm.setObject(2, foreignId);
            ResultSet rs=pstm.executeQuery();
            while(rs.next()){
                return true;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        String sql1="select  g.game_id,turn_id,firstpin,secondpin,maxturn,maxpin  " +
                "from game_table g left join turns_table t on g.game_id=t.game_id " +
                " where g.game_id=?;";
        BowlingGameEntity bowlingGameEntity=null;
        ArrayList<BowlingTurnEntity> turnEntities=new ArrayList<>();
        try{
            PreparedStatement pstm1 = conn.prepareStatement(sql1);
            pstm1.setObject(1, id);
            ResultSet rs=pstm1.executeQuery();
            while(rs.next()){
                bowlingGameEntity=new BowlingGameEntityImpl(id,rs.getInt("maxTurn"),rs.getInt("maxPin"));
                Integer firstPin=rs.getInt("firstpin");
                Integer secondPin=rs.getInt("secondpin");
                TurnKey turnKey=new TurnKeyImpl(rs.getInt("turn_id"),rs.getInt("game_id"));
                BowlingTurnEntity turnEntity=new BowlingTurnEntityImpl(firstPin,secondPin,turnKey);
                turnEntities.add(turnEntity);
            }
            if(bowlingGameEntity==null){
                return bowlingGameEntity;
            }
            if(turnEntities==null||turnEntities.size()==0){
                bowlingGameEntity.setTurnEntities(null);
            }
            bowlingGameEntity.setTurnEntities(turnEntities.toArray(new BowlingTurnEntity[0]));

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
        //ON DELETE CASCADE
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
