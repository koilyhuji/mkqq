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
    private List<UserDTO> userDTOs = new ArrayList();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

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
        String query = "SELECT * FROM  LIMIT ?";
        List<UserDTO> userDTOs = new ArrayList<>();

        try {
            conn = DBSingleton.getConnection();
            stmt = conn.prepareStatement(query);
            // Set the limit to the parameter n
            stmt.setInt(1, n);

            rs = stmt.executeQuery();

            while (rs.next()) {
                String userID = rs.getString("UserID");
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String passwordHash = rs.getString("PasswordHash");
                boolean idAdmin = rs.getBoolean("IsAdmin");

                userDTOs.add(new UserDTO(userID, fullName, email, passwordHash, idAdmin));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtils.closeResources(conn,stmt,rs);
        }

        return userDTOs;
    }


    public UserDTO getUserByID(String userID) {
        String query = "SELECT * From  where UserID = ?";

        try {
            conn = DBSingleton.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, userID);
            rs = stmt.executeQuery();

            if(rs.next()) {
                userDTO.setUserID(rs.getString("UserID"));
                userDTO.setName(rs.getString("FullName"));
                userDTO.setEmail(rs.getString("Email"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            DBUtils.closeResources(conn,stmt,rs);
        }
        return userDTO;
    }
    public UserDTO getCurrentUserifLoggedIn(){
        return this.userDTO;
    }

    public boolean login(String email, String plainPassword) {
        String passwordHash = CommonUtils.encodePassword(plainPassword);

        String sql = "SELECT COUNT(*) FROM  WHERE email = ? AND passwordHash = ?";
        try (Connection conn = DBSingleton.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, passwordHash);

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                userDTO.setUserID(rs.getString("UserID"));
                userDTO.setName(rs.getString("FullName"));
                userDTO.setEmail(rs.getString("Email"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtils.closeResources(conn,stmt,rs);
        }
        return false;
    }


}