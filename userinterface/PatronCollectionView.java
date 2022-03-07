package userinterface;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Book;
import model.BookCollection;
import model.Patron;
import model.PatronCollection;

import java.util.Enumeration;
import java.util.Vector;

public class PatronCollectionView extends View {
    // GUI components
    protected TableView<PatronTableModel> tableOfPatrons;
    protected Button doneButton;


    // For showing error message
    protected MessageView statusLog;

    public PatronCollectionView(IModel patron)
    {
        super(patron, "PatronCollectionView");

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
    //--------------------------------------------------------------------------
    protected void populateFields()
    {

        getEntryTableModelValues();
    }

    protected void getEntryTableModelValues()
    {

        ObservableList<PatronTableModel> tableData = FXCollections.observableArrayList();
        try
        {
            PatronCollection patronCollection = (PatronCollection)myModel.getState("PatronList");

            Vector entryList = (Vector)patronCollection.getState("Patrons");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements() == true)
            {
                Patron nextPatron = (Patron)entries.nextElement();
                System.out.println("Next patron for table" + nextPatron);
                Vector<String> view = nextPatron.getEntryListView();

                // add this list entry to the list
                PatronTableModel nextTableRowData = new PatronTableModel(view);
                tableData.add(nextTableRowData);

            }

            tableOfPatrons.setItems(tableData);
        }
        catch (Exception e) {//SQLException e) {
            // Need to handle this exception
        }
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

        Text prompt = new Text("LIST OF PATRONS");
        prompt.setWrappingWidth(350);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tableOfPatrons = new TableView<PatronTableModel>();
        tableOfPatrons.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn patronIdColumn = new TableColumn("Patron Id") ;
        patronIdColumn.setMinWidth(100);
        patronIdColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("patronId"));

        TableColumn nameColumn = new TableColumn("Name") ;
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("name"));

        TableColumn titleColumn = new TableColumn("Address") ;
        titleColumn.setMinWidth(100);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("address"));

        TableColumn publicationYearColumn = new TableColumn("City") ;
        publicationYearColumn.setMinWidth(100);
        publicationYearColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("city"));

        TableColumn stateColumn = new TableColumn("State Code") ;
        stateColumn.setMinWidth(100);
        stateColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("stateCode"));

        TableColumn zipColumn = new TableColumn("Zip") ;
        zipColumn.setMinWidth(100);
        zipColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("zip"));

        TableColumn emailColumn = new TableColumn("Email") ;
        emailColumn.setMinWidth(100);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("email"));

        TableColumn dateOfBirthColumn = new TableColumn("Date Of Birth") ;
        dateOfBirthColumn.setMinWidth(100);
        dateOfBirthColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("dateOfBirth"));

        TableColumn statusColumn = new TableColumn("Status") ;
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<PatronTableModel, String>("status"));

        tableOfPatrons.getColumns().addAll(patronIdColumn,
                nameColumn, titleColumn, publicationYearColumn, stateColumn, zipColumn, emailColumn, dateOfBirthColumn, statusColumn);

        tableOfPatrons.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if (event.isPrimaryButtonDown() && event.getClickCount() >=2 ){
                    //processAccountSelected();
                }
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(115, 150);
        scrollPane.setContent(tableOfPatrons);



        //TODO need to look into switching this
        doneButton = new Button("Back");
        doneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                /**
                 * Process the Cancel button.
                 * The ultimate result of this action is that the transaction will tell the teller to
                 * to switch to the transaction choice view. BUT THAT IS NOT THIS VIEW'S CONCERN.
                 * It simply tells its model (controller) that the transaction was canceled, and leaves it
                 * to the model to decide to tell the teller to do the switch back.
                 */
                //----------------------------------------------------------
                clearErrorMessage();
                myModel.stateChangeRequest("CancelTransaction", null);
            }
        });

        HBox btnContainer = new HBox(100);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.getChildren().add(doneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(scrollPane);
        vbox.getChildren().add(btnContainer);

        return vbox;
    }



    //--------------------------------------------------------------------------
    public void updateState(String key, Object value)
    {
    }

    //--------------------------------------------------------------------------
    /*protected void processAccountSelected()
    {
        BookTableModel selectedItem = tableOfBooks.getSelectionModel().getSelectedItem();

        if(selectedItem != null)
        {
            String selectedAcctNumber = selectedItem.getAccountNumber();

            myModel.stateChangeRequest("AccountSelected", selectedAcctNumber);
        }
    }*/

    //--------------------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
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


}
