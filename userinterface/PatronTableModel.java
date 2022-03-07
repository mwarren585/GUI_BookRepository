package userinterface;

import javafx.beans.property.SimpleStringProperty;

import java.util.Vector;

public class PatronTableModel {
    private final SimpleStringProperty patronId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty stateCode;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public PatronTableModel(Vector<String> accountData)
    {
        patronId =  new SimpleStringProperty(accountData.elementAt(0));
        name =  new SimpleStringProperty(accountData.elementAt(1));
        address =  new SimpleStringProperty(accountData.elementAt(2));
        city =  new SimpleStringProperty(accountData.elementAt(3));
        stateCode = new SimpleStringProperty(accountData.elementAt(4));
        zip = new SimpleStringProperty(accountData.elementAt(5));
        email = new SimpleStringProperty(accountData.elementAt(6));
        dateOfBirth = new SimpleStringProperty(accountData.elementAt(7));
        status = new SimpleStringProperty(accountData.elementAt(8));

    }

    //----------------------------------------------------------------------------
    public String getBookId() {
        return patronId.get();
    }

    //----------------------------------------------------------------------------
    public void setBookId(String number) { patronId.set(number); }

    //----------------------------------------------------------------------------
    public String getName() {
        return name.get();
    }

    //----------------------------------------------------------------------------
    public void setName(String auth) {
        name.set(auth);
    }

    //----------------------------------------------------------------------------
    public String getAddress() {
        return address.get();
    }

    //----------------------------------------------------------------------------
    public void setAddress(String ti) {
        address.set(ti);
    }

    //----------------------------------------------------------------------------
    public String getCity() { return city.get(); }

    //----------------------------------------------------------------------------
    public void setCity(String pubyear)
    {
        city.set(pubyear);
    }
    //----------------------------------------------------------------------------
    public String getStateCode() { return stateCode.get(); }

    //----------------------------------------------------------------------------
    public void setStateCode(String stat)
    {
        stateCode.set(stat);
    }

    //----------------------------------------------------------------------------
    public String getZip() { return zip.get(); }

    //----------------------------------------------------------------------------
    public void setZip(String stat)
    {
        zip.set(stat);
    }

    //----------------------------------------------------------------------------
    public String getEmail() { return email.get(); }

    //----------------------------------------------------------------------------
    public void setEmail(String stat)
    {
        email.set(stat);
    }
    //----------------------------------------------------------------------------
    public String getDateOfBirth() { return dateOfBirth.get(); }

    //----------------------------------------------------------------------------
    public void setDateOfBirth(String stat)
    {
        dateOfBirth.set(stat);
    }

    //----------------------------------------------------------------------------
    public String getStatus() { return status.get(); }

    //----------------------------------------------------------------------------
    public void setStatus(String stat)
    {
        status.set(stat);
    }
}

