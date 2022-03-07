// specify the package
package userinterface;

// system imports

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Date;
import java.text.*;


// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class PatronView extends View
{

    // GUI components
    protected TextField patronName;
    protected TextField address;
    protected TextField city;
    protected TextField stateCode;
    protected TextField zip;
    protected TextField email;
    protected TextField dateOfBirth;
    //protected ComboBox<String> comboBox;




    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public PatronView(IModel patron)
    {
        super(patron, "PatronView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();


        myModel.subscribe("TransactionError", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Library System ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("PATRON INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text accNumLabel = new Text(" Patron Name : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        accNumLabel.setFont(myFont);
        accNumLabel.setWrappingWidth(150);
        accNumLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(accNumLabel, 0, 1);

        patronName = new TextField();
        patronName.setEditable(true);
        grid.add(patronName, 1, 1);

        Text acctTypeLabel = new Text(" Patron Address : ");
        acctTypeLabel.setFont(myFont);
        acctTypeLabel.setWrappingWidth(150);
        acctTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(acctTypeLabel, 0, 2);

        address = new TextField();
        address.setEditable(true);
        grid.add(address, 1, 2);

        Text balLabel = new Text(" Patron city : ");
        balLabel.setFont(myFont);
        balLabel.setWrappingWidth(150);
        balLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(balLabel, 0, 3);

        city = new TextField();
        city.setEditable(true);
        grid.add(city, 1, 3);

        Text stateLabel = new Text(" Patron State Code : ");
        stateLabel.setFont(myFont);
        stateLabel.setWrappingWidth(150);
        stateLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(stateLabel, 0, 4);

        stateCode = new TextField();
        stateCode.setEditable(true);
        grid.add(stateCode, 1, 4);

        Text zipLabel = new Text(" Patron ZIP : ");
        zipLabel.setFont(myFont);
        zipLabel.setWrappingWidth(150);
        zipLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(zipLabel, 0, 5);

        zip = new TextField();
        zip.setEditable(true);
        grid.add(zip, 1, 5);

        Text mailLabel = new Text(" Patron Email : ");
        mailLabel.setFont(myFont);
        mailLabel.setWrappingWidth(150);
        mailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(mailLabel, 0, 6);

        email = new TextField();
        email.setEditable(true);
        grid.add(email, 1, 6);

        Text dobLabel = new Text(" Patron DOB : ");
        dobLabel.setFont(myFont);
        dobLabel.setWrappingWidth(150);
        dobLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dobLabel, 0, 7);

        dateOfBirth = new TextField();
        dateOfBirth.setEditable(true);
        grid.add(dateOfBirth, 1, 7);


        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                try {
                    processPatronData();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }


            }
        });

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);
        doneButton = new Button("Done");
        doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });
        doneCont.getChildren().add(submitButton);
        doneCont.getChildren().add(doneButton);



        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }


    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        patronName.setText((String)myModel.getState("name"));
        address.setText((String)myModel.getState("address"));
        city.setText((String)myModel.getState("city"));
        stateCode.setText((String)myModel.getState("stateCode"));
        zip.setText((String)myModel.getState("zip"));
        email.setText((String)myModel.getState("email"));
        dateOfBirth.setText((String)myModel.getState("dateOfBirth"));

    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("TransactionError") == true)
        {
            String val = (String)value;
            if (val.startsWith("Err") || (val.startsWith("ERR")))
                displayErrorMessage( val);
            else
                displayMessage(val);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    //---------------------------------------------------------
    public void processPatronData() throws ParseException{
        // DEBUG: System.out.println("DepositAmountView.processAction()");

        clearErrorMessage();

        String nameEntered = patronName.getText();
        String addressEntered = address.getText();
        String cityEntered = city.getText();
        String stateEntered = stateCode.getText();
        String zipEntered = zip.getText();
        String emailEntered = email.getText();
        String dobEntered = dateOfBirth.getText();


        if ((nameEntered == null) || (nameEntered.length() == 0)){
            displayErrorMessage("Please enter a name to be entered.");
        }
        else
        if ((addressEntered == null) || (addressEntered.length() == 0)){
            displayErrorMessage("Please enter an address to be entered");
        }
        else
        if ((cityEntered == null) || (cityEntered.length() == 0)){
            displayErrorMessage("Please enter a city to be entered");
        }
        else
        if ((stateEntered == null) || (stateEntered.length() == 0)){
            displayErrorMessage("Please enter a state to be entered");
        }
        else
        if ((zipEntered == null) || (zipEntered.length() == 0)){
            displayErrorMessage("Please enter a zip to be entered");
        }
        else
        if ((emailEntered == null) || (emailEntered.length() == 0)){
            displayErrorMessage("Please enter a email to be entered");
        }
        else
        if ((dobEntered == null) || (dobEntered.length() == 0)){
            displayErrorMessage("Please enter a date of birth to be entered");
        }

        else
        {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdformat.parse(dobEntered);
            Date d2 = sdformat.parse("1920-01-01");
            Date d3 = sdformat.parse("2004-01-01");

            if((d1.compareTo(d2) < 0) || (d1.compareTo(d3) > 0)){
                displayErrorMessage("Please enter publisher year between 1920-01-01 and 2004-01-01");
                }


                Properties props = new Properties();
                props.setProperty("name", nameEntered);
                props.setProperty("address", addressEntered);
                props.setProperty("city", cityEntered);
                props.setProperty("stateCode", stateEntered);
                props.setProperty("zip", zipEntered);
                props.setProperty("email", emailEntered);
                props.setProperty("dateOfBirth", dobEntered);
                props.setProperty("status", "Active");
                myModel.stateChangeRequest("PatronData", props);
            }
        }




    }






