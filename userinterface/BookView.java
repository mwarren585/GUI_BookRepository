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


import java.util.Properties;


// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class BookView extends View
{

    // GUI components
    protected TextField bookTitle;
    protected TextField author;
    protected TextField pubYear;
    //protected ComboBox<String> comboBox;




    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BookView(IModel book)
    {
        super(book, "BookView");

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

        Text prompt = new Text("BOOK INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text accNumLabel = new Text(" Book Title : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        accNumLabel.setFont(myFont);
        accNumLabel.setWrappingWidth(150);
        accNumLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(accNumLabel, 0, 1);

        bookTitle = new TextField();
        bookTitle.setEditable(true);
        grid.add(bookTitle, 1, 1);

        Text acctTypeLabel = new Text(" Author : ");
        acctTypeLabel.setFont(myFont);
        acctTypeLabel.setWrappingWidth(150);
        acctTypeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(acctTypeLabel, 0, 2);

        author = new TextField();
        author.setEditable(true);
        grid.add(author, 1, 2);

        Text balLabel = new Text(" Published Year : ");
        balLabel.setFont(myFont);
        balLabel.setWrappingWidth(150);
        balLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(balLabel, 0, 3);

        pubYear = new TextField();
        pubYear.setEditable(true);
        grid.add(pubYear, 1, 3);




        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processBookData();


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
        bookTitle.setText((String)myModel.getState("bookTitle"));
        author.setText((String)myModel.getState("author"));
        pubYear.setText((String)myModel.getState("pubYear"));

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
    public void processBookData() {
        // DEBUG: System.out.println("DepositAmountView.processAction()");

        clearErrorMessage();

        String bookTitleEntered = bookTitle.getText();
        String authorEntered = author.getText();
        String pubYearEntered = pubYear.getText();


        if ((bookTitleEntered == null) || (bookTitleEntered.length() == 0)){
            displayErrorMessage("Please enter a book title to be entered.");
        }
        else
        if ((authorEntered == null) || (authorEntered.length() == 0)){
            displayErrorMessage("Please enter a author to be entered");
        }
        else
        {
            double pYear = Double.parseDouble(pubYearEntered);
            if((pYear < 1800) || (pYear > 2022)){
                displayErrorMessage("Please enter publisher year between 1800 and 2022");
            }
            Properties props = new Properties();
            props.setProperty("bookTitle", bookTitleEntered);
            props.setProperty("author", authorEntered);
            props.setProperty("pubYear", pubYearEntered);
            props.setProperty("status", "Active");
            myModel.stateChangeRequest("BookData", props);
        }




    }






}
