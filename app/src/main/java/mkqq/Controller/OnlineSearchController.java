package mkqq.Controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import mkqq.utils.BookOnlineViewModel;

public class OnlineSearchController {
    @FXML private TextField searchTextField;
    @FXML private ListView<BookOnlineViewModel> searchResultsListView;
    @FXML private Label titleLabel, authorsLabel, descriptionLabel;
    @FXML private ImageView coverImageView;

    public void initialize() {
        searchResultsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(BookOnlineViewModel book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.toString());
                }
            }
        });
        searchResultsListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        showBookDetails(newSelection);
                    }
                });
    }

    @FXML
    private void performSearch() {
        String query = searchTextField.getText().trim();
        if (!query.isEmpty()) {
            try {
                searchBooks(query);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void searchBooks(String query) throws UnsupportedEncodingException {
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String urlString = "https://openlibrary.org/search.json?q=" + encodedQuery;
        try {

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            System.out.println(url.toString());
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {

                    response.append(line);
                }
                reader.close();

                List<BookOnlineViewModel> books = parseBookJson(response.toString());
                ObservableList<BookOnlineViewModel> observableBooks = FXCollections.observableArrayList(books);
                searchResultsListView.setItems(observableBooks);
                for (BookOnlineViewModel book: observableBooks
                     ) {
                    System.out.println(book.getTitle());
                }
                Platform.runLater(() -> {
                    if (books.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No book found");
                        alert.showAndWait();
                    }

                });

            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Request failed: " + responseCode);
                    alert.show();
                    System.err.println("Request failed: " + responseCode);
                });

            }
            connection.disconnect();


        } catch (IOException e) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error during search: " + e.getMessage());
                alert.show();
                System.err.println("Error during search: " + e.getMessage());
            });
            e.printStackTrace();
        }

    }

    private List<BookOnlineViewModel> parseBookJson(String responseBody) {

        List<BookOnlineViewModel> books = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(responseBody);

        JSONArray docs = jsonObject.getJSONArray("docs");
        for (int i = 0; i < docs.length(); i++) {
            JSONObject doc = docs.getJSONObject(i);
            String title = doc.optString("title", "N/A");

            JSONArray authorNamesArray = doc.optJSONArray("author_name");
            List<String> authorNames = new ArrayList<>();
            if (authorNamesArray != null) {
                for (int j = 0; j < authorNamesArray.length(); j++) {
                    authorNames.add(authorNamesArray.getString(j));
                }
            }

            JSONArray publisherArray = doc.optJSONArray("publisher");
            String publisher = publisherArray != null && !publisherArray.isEmpty() ? publisherArray.getString(0) : "N/A";

            String coverKey = doc.optString("cover_i", null);
            String coverURL = coverKey != null ?  "https://covers.openlibrary.org/b/id/" + coverKey + "-M.jpg" : null;
            String descriptionURL = "https://openlibrary.org/works/" + doc.optString("key").replace("/works/", "") + ".json";
            String description = descriptionURL;
            BookOnlineViewModel book = new BookOnlineViewModel(title, authorNames, publisher, description, coverURL);
            books.add(book);
        }

        return books;
    }

    private String fetchDescription(String descriptionURL) {
        try {
            URL url = new URL(descriptionURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                JSONObject descriptionObject = jsonObject.optJSONObject("description");
                if (descriptionObject != null) {
                    if (descriptionObject.has("value")) {
                        return descriptionObject.getString("value");
                    } else {
                        return descriptionObject.optString("description", "N/A");
                    }
                }
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    private void showBookDetails(BookOnlineViewModel book) {
        titleLabel.setText(book.getTitle());
        authorsLabel.setText(String.join(", ", book.getAuthors()));

        if (book.getCoverURL() != null) {
            Image image = new Image(book.getCoverURL(), true);
            coverImageView.setImage(image);
        } else {
            String imagePath = getClass().getResource("/mkqq/images/book.png").toExternalForm();
            Image placeholder = new Image(imagePath);
            coverImageView.setImage(placeholder);
        }
        String description = fetchDescription(book.getDescriptionLink());
        descriptionLabel.setText(description);
    }



}
