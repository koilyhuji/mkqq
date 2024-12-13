package mkqq.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import mkqq.DTO.MemberDTO;

import mkqq.utils.DBSingleton;
import mkqq.utils.DBUtils;

public class MemberDAO {
    private MemberDTO memberDTO = new MemberDTO();
    private List<MemberDTO> memberDTOS;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public MemberDAO(){
        this.memberDTOS = DBUtils.getAll("memberdetail",MemberDTO.class);
        conn = DBSingleton.getInstance().getConnection();
    }

    public List<MemberDTO> getMemberDTOS() {
        return memberDTOS;
    }

    public boolean createMember(MemberDTO member){
        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("INSERT INTO memberdetail values(?,?,?,?)");
            stmt.setString(1,member.getId());
            stmt.setString(2,member.getName());
            stmt.setString(3,member.getAddress());
            stmt.setString(4,member.getContact());

            int affectedRows =  stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Data load successful");
                return true;
            } else {
                System.out.println("Something went wrong");
            }
            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
    public boolean insertMember(MemberDTO member) {

        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("INSERT INTO memberdetail values(?,?,?,?)");
            stmt.setString(1,member.getId());
            stmt.setString(2,member.getName());
            stmt.setString(3,member.getAddress());
            stmt.setString(4,member.getContact());

            int affectedRows =  stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Data insert successful");
                return true;
            } else {
                System.out.println("Something went wrong");
            }
            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateMember(MemberDTO member) {
        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("UPDATE memberdetail SET name=? , address=? , contact=? where id=?");
            stmt.setString(1,member.getId());
            stmt.setString(2,member.getName());
            stmt.setString(3,member.getAddress());
            stmt.setString(4,member.getContact());

            int affectedRows =  stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Data update successful");
                return true;
            } else {
                System.out.println("Something went wrong");
            }
            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteMember(String member) {
            try {
                conn = DBSingleton.getInstance().getConnection();
                stmt = conn.prepareStatement("DELETE from memberdetail where id=?");
                stmt.setString(1,member);
                int affected = stmt.executeUpdate();
                if (affected > 0) {
                   return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return false;

    }

    public String getNewId(){

        String query = "SELECT id FROM memberdetail";
        String id = "";
        int maxId = 0;
        String ids = null;
        try {
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ids = rs.getString(1);

                int idint = Integer.parseInt(ids.replace("M", ""));
                if (idint > maxId) {
                    maxId = idint;
                }
            }
            maxId = maxId + 1;

            if (maxId < 10) {
                id = "M00" + maxId;
            } else if (maxId < 100) {
                id = "M0" + maxId;
            } else {
                id = "M" + maxId;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return id;
    }
    public MemberDTO getMemberfromID(String id){
        for (var member: memberDTOS
             ) {
            if(member.getId().equals(id)){
                return member;
            }
        }
        return null;
    }
}
