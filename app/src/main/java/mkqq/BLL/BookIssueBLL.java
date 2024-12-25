package mkqq.BLL;

import java.util.List;

import mkqq.DAO.BookIssueDAO;
import mkqq.DTO.BookIssueDTO;


public class BookIssueBLL {
    private BookIssueDAO bookIssueDao;

    public BookIssueBLL() {
        bookIssueDao = new BookIssueDAO();
    }

    public boolean createBookIssue(BookIssueDTO bookIssue) {
        return bookIssueDao.createBookIssue(bookIssue);
    }

    public boolean insertBookIssue(BookIssueDTO bookIssue) {
        return bookIssueDao.insertBookIssue(bookIssue);
    }

    public boolean updateBookIssue(BookIssueDTO bookIssue) {
        return bookIssueDao.updateBookIssue(bookIssue);

    }

    public boolean deleteBookIssue(String bookIssue) {
        return bookIssueDao.deleteBookIssue(bookIssue);
    }

    public String getNewId() {
        return bookIssueDao.getNewId();
    }


    public List<BookIssueDTO> getBookIssueDTOS(){
        return bookIssueDao.getBookIssueDTOS();
    }
    public BookIssueDTO getBookIssueFromId(String id){
        return bookIssueDao.getBookIssueFromId(id);
    }
}
