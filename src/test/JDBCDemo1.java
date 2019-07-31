import com.bittech.Riri.chatroom.utils.JDBCUtils;
import com.bittech.Riri.chatroom.utils.JDBCUtils1;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

//单元测试
public class JDBCDemo1 {
    @Test
    //select
    public void test(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try{
            connection=JDBCUtils.getConnection();
            String sql="select * from user";
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String userName=resultSet.getString("userName");
                String password=resultSet.getString("password");
                System.out.println("id为:"+id+",userName为:"+userName+",password为:"+password);
            }
        }catch (SQLException e){

        }finally {
            JDBCUtils.closeResources(connection,statement,resultSet);
        }
    }

    //指定查询
    @Test
    public void testQuery(){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            connection=JDBCUtils1.getConnection();
            String sql=" select * from user where id = ? and username= ? ";
            statement=connection.prepareStatement(sql);
            statement.setInt(1,8);
            statement.setString(2,"test");
            resultSet=statement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String userName=resultSet.getString("userName");
                String password=resultSet.getString("password");
                System.out.println("id为:"+id+",userName为:"+userName+",password为:"+password);
            }
        }catch (SQLException e) {

        }
//        }finally {
//            JDBCUtils1.closeResources(connection,statement,resultSet);
//        }
    }

    //insert
    @Test
    public void testInsert(){
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            connection=JDBCUtils.getConnection();
            String sql="insert into user(username, password) values (?,?)";
            statement=connection.prepareStatement(sql);
            statement.setString(1,"showlo");
            statement.setString(2,DigestUtils.md5Hex("790730"));
            int userRows=statement.executeUpdate();
            Assert.assertEquals(1,userRows);
        }catch (SQLException e){

        }finally {
            JDBCUtils.closeResources(connection,statement);
        }
    }
}
