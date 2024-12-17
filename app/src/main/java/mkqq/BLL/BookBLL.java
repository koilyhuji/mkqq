package mkqq.BLL;

import java.util.List;

import mkqq.DAO.BookDAO;
import mkqq.DTO.BookDTO;

public class BookBLL {
    private BookDAO bookDAO;

    public BookBLL() {
        bookDAO = new BookDAO();
    }

    public boolean createBook(BookDTO book) {
        return bookDAO.createBook(book);
    }

    public boolean insertBook(BookDTO book) {
        return bookDAO.insertBook(book);
    }

    public boolean updateBook(BookDTO book) {
        return bookDAO.updateBook(book);
    }

    public boolean deleteBook(String book) {
        return bookDAO.deleteBook(book);
    }

    public String getNewId() {
        return bookDAO.getNewId();
    }

    
    public List<BookDTO> getBookDTOS(){
        return bookDAO.getBookDTOS();
    }
    public BookDTO getBookFromId(String id){
        return bookDAO.getBookfromId(id);
    }
}
