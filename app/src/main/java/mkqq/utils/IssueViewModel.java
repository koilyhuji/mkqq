package mkqq.utils;

import java.sql.Date;

import javax.swing.MenuElement;

import mkqq.DTO.BookDTO;
import mkqq.DTO.MemberDTO;

public class IssueViewModel {
    String issueId;
    Date date;
    BookDTO book;
    MemberDTO member;
    String memberName;
    String bookName;


    public IssueViewModel(String issueId, Date date, BookDTO book, MemberDTO member, String memberName, String bookName) {
        this.issueId = issueId;
        this.date = date;
        this.book = book;
        this.member = member;
        this.memberName = memberName;
        this.bookName = bookName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public IssueViewModel() {
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

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO bookName) {
        this.book = bookName;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO memberName) {
        this.member = memberName;
    }
}
