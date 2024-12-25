package mkqq.DTO;

import java.sql.Date;

public class BookIssueDTO {
    private String issueId;
    private Date date;
    private String memberId;
    private String bookId;

    public BookIssueDTO(String issueId, Date date, String memberId, String bookId) {
        this.issueId = issueId;
        this.date = date;
        this.memberId = memberId;
        this.bookId = bookId;
    }
    public  BookIssueDTO(){

    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
