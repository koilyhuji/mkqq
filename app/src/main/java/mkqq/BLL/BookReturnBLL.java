package mkqq.BLL;

import java.util.ArrayList;
import java.util.List;

import mkqq.DAO.BookReturnDAO;
import mkqq.DTO.BookIssueDTO;
import mkqq.DTO.BookReturnDTO;


public class BookReturnBLL {
    private BookReturnDAO bookReturnDAO;

    public BookReturnBLL() {
        bookReturnDAO = new BookReturnDAO();
    }

    public boolean createBookReturn(BookReturnDTO bookReturn) {
        return bookReturnDAO.createBookReturn(bookReturn);
    }

    public boolean insertBookReturn(BookReturnDTO bookReturn) {
        return bookReturnDAO.insertBookReturn(bookReturn);
    }

    public boolean updateBookReturn(BookReturnDTO bookReturn) {
        return bookReturnDAO.updateBookReturn(bookReturn);

    }

    public boolean deleteBookReturn(int bookReturn) {
        return bookReturnDAO.deleteBookReturn(bookReturn);
    }


    public List<BookReturnDTO> getBookReturnDTOS(){
        return bookReturnDAO.getBookReturnDTOS();
    }
    public BookReturnDTO getBookReturnFromId(int id){
        return bookReturnDAO.getBookReturnFromId(id);
    }

    public List<BookReturnDTO> getReturnsByBookId(String bookId) {
        List<BookReturnDTO> list = new ArrayList<>();
        for (var bookreturn: bookReturnDAO.getBookReturnDTOS()
             ) {
            BookIssueDTO issue = new BookIssueBLL().getBookIssueFromId(bookreturn.getIssueid());
            if(issue.getBookId().equals(bookId)){
                list.add(bookreturn);
            }
        }
        return list;
    }
}
