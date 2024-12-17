package mkqq.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import mkqq.DTO.BookDTO;
import mkqq.DTO.BookDTO;
import mkqq.utils.DBSingleton;
import mkqq.utils.DBUtils;

public class BookDAO {
    private BookDTO BookDTO = new BookDTO();
    private List<BookDTO> BookDTOS;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public BookDAO(){
        this.BookDTOS = DBUtils.getAll("bookdetail",BookDTO.class);
        conn = DBSingleton.getInstance().getConnection();
    }

    public List<BookDTO> getBookDTOS() {
        this.BookDTOS = DBUtils.getAll("bookdetail",BookDTO.class);
        return BookDTOS;
    }
    public boolean createBook(BookDTO Book){
        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("INSERT INTO bookdetail values(?,?,?,?)");
            stmt.setString(1,Book.getId());
            stmt.setString(2,Book.getTitle());
            stmt.setString(3,Book.getAuthor());
            stmt.setString(4,Book.getStatus());

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
    public boolean insertBook(BookDTO Book) {

        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("INSERT INTO bookdetail values(?,?,?,?)");
            stmt.setString(1,Book.getId());
            stmt.setString(2,Book.getTitle());
            stmt.setString(3,Book.getAuthor());
            stmt.setString(4,Book.getStatus());

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

    public boolean updateBook(BookDTO Book) {
        try{
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement("UPDATE bookdetail SET title=? , author=? , status=? where id=?");
            stmt.setString(4,Book.getId());
            stmt.setString(1,Book.getAuthor());
            stmt.setString(2,Book.getTitle());
            stmt.setString(3,Book.getStatus());

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

    public boolean deleteBook(String Book) {
            try {
                conn = DBSingleton.getInstance().getConnection();
                stmt = conn.prepareStatement("DELETE from bookdetail where id=?");
                stmt.setString(1,Book);
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

        String query = "SELECT id FROM bookdetail";
        String id = "";
        int maxId = 0;
        String ids = null;
        try {
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ids = rs.getString(1);

                int idint = Integer.parseInt(ids.replace("B", ""));
                if (idint > maxId) {
                    maxId = idint;
                }
            }
            maxId = maxId + 1;

            if (maxId < 10) {
                id = "B00" + maxId;
            } else if (maxId < 100) {
                id = "B0" + maxId;
            } else {
                id = "B" + maxId;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return id;
    }
    public BookDTO getBookfromId(String id){
        for (var book: BookDTOS
             ) {
            if(book.getId().equals(id)){
                return book;
            }
        }
        return null;
    }
}
