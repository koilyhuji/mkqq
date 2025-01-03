package mkqq.utils;

import java.util.List;

public class BookOnlineViewModel {
    private String title;
    private List<String> authors;
    private String publisher;
    private String descriptionLink;
    private String coverURL;

    // Constructor
    public BookOnlineViewModel(String title, List<String> authors, String publisher, String description, String coverURL) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.descriptionLink = description;
        this.coverURL = coverURL;
    }

    // Getter for all the members

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescriptionLink() {
        return descriptionLink;
    }

    public String getCoverURL() {
        return coverURL;
    }

    // Setter if needed

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setDescriptionLink(String descriptionLink) {
        this.descriptionLink = descriptionLink;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Authors: %s", this.title, this.authors);
    }
}
