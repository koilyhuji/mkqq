package mkqq.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mkqq.DTO.UserDTO;
import mkqq.utils.CommonUtils;
import mkqq.utils.DBSingleton;
import mkqq.utils.DBUtils;


public class UserDAO {
    private UserDTO userDTO = new UserDTO();
    private List<UserDTO> userDTOs;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public UserDAO() {
        this.userDTOs = DBUtils.getAll("users", UserDTO.class);
    }

    public boolean insertUser(UserDTO user) {
        return true;
    }

    public boolean updateUser(UserDTO user) {
        return true;
    }

    public boolean deleteUser(UserDTO user) {
        return true;
    }

    public List<UserDTO> get(int n) {
        List<UserDTO> returnlist = userDTOs.subList(0,n);
        return returnlist;
    }


    public UserDTO getUserByID(String userID) {
        String query = "SELECT * From  where UserID = ?";

        try {
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, userID);
            rs = stmt.executeQuery();

            if(rs.next()) {
                userDTO.setUserID(rs.getString("UserID"));
                userDTO.setName(rs.getString("name"));
                userDTO.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userDTO;
    }
    public UserDTO getCurrentUserifLoggedIn(){
        return this.userDTO;
    }

    public boolean login(String name, String plainPassword) {
        String passwordHash = CommonUtils.encodePassword(plainPassword);
        //StringBuilder presql = new StringBuilder("SELECT * FROM users WHERE name = "+ name +" AND password = "+ passwordHash);
        String sql = "SELECT * FROM users WHERE name = ? AND passwordHash = ?";
        //String sql = presql.toString();
        try (Connection conn = DBSingleton.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, passwordHash);

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                userDTO.setUserID(rs.getString("UserID"));
                userDTO.setName(rs.getString("name"));
                userDTO.setEmail(rs.getString("email"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean createUser(String id, String name, String email, String pass, boolean isadmin){
        String passwordHash = CommonUtils.encodePassword(pass);
        String userID = id;
        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?)");
            stmt.setString(1,userID);
            stmt.setString(2,name);
            stmt.setString(3,email);
            stmt.setString(4,passwordHash);
            stmt.setBoolean(5,isadmin);
            stmt.execute();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }


}