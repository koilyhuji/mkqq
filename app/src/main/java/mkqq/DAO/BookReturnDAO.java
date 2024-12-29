package mkqq.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import mkqq.DTO.BookReturnDTO;
import mkqq.utils.DBSingleton;
import mkqq.utils.DBUtils;

public class BookReturnDAO {
    private BookReturnDTO bookReturnDTO = new BookReturnDTO();
    private List<BookReturnDTO> bookReturnDTOs;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public BookReturnDAO(){
        this.bookReturnDTOs = DBUtils.getAll("returndetail",BookReturnDTO.class);

    }

    public List<BookReturnDTO> getBookReturnDTOS() {
        this.bookReturnDTOs = DBUtils.getAll("returndetail",BookReturnDTO.class);
        return bookReturnDTOs;
    }
    public boolean createBookReturn(BookReturnDTO BookReturn){
        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("INSERT INTO returndetail (issuedDate, returnedDate, fine, issueid) values(?,?,?,?)");

            stmt.setDate(1, BookReturn.getIssuedDate());
            stmt.setDate(2, BookReturn.getReturnedDate());
            stmt.setString(3, BookReturn.getFine());
            stmt.setString(4,BookReturn.getIssueid());

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
    public boolean insertBookReturn(BookReturnDTO BookReturn) {

        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("INSERT INTO returndetail (issuedDate, returnedDate, fine, issueid) values(?,?,?,?)");

            stmt.setDate(1, BookReturn.getIssuedDate());
            stmt.setDate(2, BookReturn.getReturnedDate());
            stmt.setString(3, BookReturn.getFine());
            stmt.setString(4,BookReturn.getIssueid());


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

    public boolean updateBookReturn(BookReturnDTO BookReturn) {
        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("UPDATE returndetail SET issuedDate=? , returnedDate=? , fine=?, issueid=? where id=?");

            stmt.setInt(5, BookReturn.getId());
            stmt.setDate(1, BookReturn.getIssuedDate());
            stmt.setDate(2, BookReturn.getReturnedDate());
            stmt.setString(3,BookReturn.getFine());
            stmt.setString(4,BookReturn.getIssueid());
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

    public boolean deleteBookReturn(int BookReturnID) {
        try {
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("DELETE from returndetail where id=?");
            stmt.setInt(1,BookReturnID);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    public BookReturnDTO getBookReturnFromId(int id){
        for (var bookReturn: bookReturnDTOs
        ) {
            if(bookReturn.getId() == id){
                return bookReturn;
            }
        }
        return null;
    }

}
