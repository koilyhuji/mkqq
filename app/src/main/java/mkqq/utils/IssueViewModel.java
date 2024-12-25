package mkqq.utils;

public class IssueViewModel {
    String issueId;
    String date;
    String bookName;
    String memberName;

    public IssueViewModel(String issueId, String date, String bookName, String memberName) {
        this.issueId = issueId;
        this.date = date;
        this.bookName = bookName;
        this.memberName = memberName;
    }

    public IssueViewModel() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
