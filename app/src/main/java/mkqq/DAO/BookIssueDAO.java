package mkqq.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import mkqq.DTO.BookIssueDTO;
import mkqq.utils.DBSingleton;
import mkqq.utils.DBUtils;

public class BookIssueDAO {
    private BookIssueDTO bookIssueDTO = new BookIssueDTO();
    private List<BookIssueDTO> bookIssueDTOs;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public BookIssueDAO(){
        this.bookIssueDTOs = DBUtils.getAll("issuetb",BookIssueDTO.class);

    }

    public List<BookIssueDTO> getBookIssueDTOS() {
        this.bookIssueDTOs = DBUtils.getAll("issuetb",BookIssueDTO.class);
        return bookIssueDTOs;
    }
    public boolean createBookIssue(BookIssueDTO BookIssue){
        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("INSERT INTO issuetb values(?,?,?,?)");
            stmt.setString(1, BookIssue.getIssueId());
            stmt.setDate(2, BookIssue.getDate());
            stmt.setString(3, BookIssue.getMemberId());
            stmt.setString(4, BookIssue.getBookId());

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
    public boolean insertBookIssue(BookIssueDTO BookIssue) {

        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("INSERT INTO issuetb values(?,?,?,?)");
            stmt.setString(1, BookIssue.getIssueId());
            stmt.setDate(2, BookIssue.getDate());
            stmt.setString(3, BookIssue.getMemberId());
            stmt.setString(4, BookIssue.getBookId());

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

    public boolean updateBookIssue(BookIssueDTO BookIssue) {
        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("UPDATE issuetb SET date=? , memberId=? , bookid=? where issueId=?");
            stmt.setString(4,BookIssue.getIssueId());
            stmt.setDate(1,BookIssue.getDate());
            stmt.setString(2,BookIssue.getMemberId());
            stmt.setString(3,BookIssue.getBookId());

            int affectedRows =  stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Data update successful");
                return true;
            } else {
                System.out.println("Something went wrong");
                System.out.println(affectedRows);
            }
            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteBookIssue(String BookIssueID) {
        try {
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("DELETE from issuetb where id=?");
            stmt.setString(1,BookIssueID);
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

        String query = "SELECT issueId FROM issuetb";
        String id = "";
        int maxId = 0;
        String ids = null;
        try {
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ids = rs.getString(1);

                int idint = Integer.parseInt(ids.replace("I", ""));
                if (idint > maxId) {
                    maxId = idint;
                }
            }
            maxId = maxId + 1;

            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return id;
    }
    public BookIssueDTO getBookIssueFromId(String id){
        for (var bookIssue: bookIssueDTOs
        ) {
            if(bookIssue.getIssueId().equals(id)){
                return bookIssue;
            }
        }
        return null;
    }

}
