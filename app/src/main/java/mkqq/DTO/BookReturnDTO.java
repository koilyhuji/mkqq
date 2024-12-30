package mkqq.DTO;

import java.sql.Date;

public class BookReturnDTO {
    private int id;
    private Date issuedDate;
    private Date returnedDate;
    private String fine;
    private String issueid;

    public BookReturnDTO( Date issuedDate, Date returnedDate, String fine, String issueid) {

        this.issuedDate = issuedDate;
        this.returnedDate = returnedDate;
        this.fine = fine;
        this.issueid = issueid;
    }

    public BookReturnDTO() {
    }

    public String getIssueid() {
        return issueid;
    }

    public void setIssueid(String issueid) {
        this.issueid = issueid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }
}
