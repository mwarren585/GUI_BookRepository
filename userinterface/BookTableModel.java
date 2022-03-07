package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

public class BookTableModel {
    private final SimpleStringProperty bookId;
    private final SimpleStringProperty author;
    private final SimpleStringProperty title;
    private final SimpleStringProperty publicationYear;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public BookTableModel(Vector<String> bookData)
    {
        bookId =  new SimpleStringProperty(bookData.elementAt(0));
        author =  new SimpleStringProperty(bookData.elementAt(1));
        title =  new SimpleStringProperty(bookData.elementAt(2));
        publicationYear =  new SimpleStringProperty(bookData.elementAt(3));
        status = new SimpleStringProperty(bookData.elementAt(4));
    }

    //----------------------------------------------------------------------------
    public String getBookId() {
        return bookId.get();
    }

    //----------------------------------------------------------------------------
    public void setBookId(String number) { bookId.set(number); }

    //----------------------------------------------------------------------------
    public String getAuthor() {
        return author.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor(String auth) {
        author.set(auth);
    }

    //----------------------------------------------------------------------------
    public String getTitle() {
        return title.get();
    }

    //----------------------------------------------------------------------------
    public void setTitle(String ti) {
        title.set(ti);
    }

    //----------------------------------------------------------------------------
    public String getPublicationYear() { return publicationYear.get(); }

    //----------------------------------------------------------------------------
    public void setPublicationYear(String pubyear)
    {
        publicationYear.set(pubyear);
    }
    //----------------------------------------------------------------------------
    public String getStatus() { return status.get(); }

    //----------------------------------------------------------------------------
    public void setStatus(String stat)
    {
        status.set(stat);
    }
}
