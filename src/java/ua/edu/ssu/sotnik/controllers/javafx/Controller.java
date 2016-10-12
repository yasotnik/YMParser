package ua.edu.ssu.sotnik.controllers.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import ua.edu.ssu.sotnik.controllers.DBController;
import ua.edu.ssu.sotnik.controllers.ParserFactory;
import ua.edu.ssu.sotnik.controllers.YandexParserAPI;
import ua.edu.ssu.sotnik.models.Product;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public MenuItem closeW;
    Product product = new Product();
    DBController connection = new DBController();

    @FXML
    private TextField requestString;
    @FXML
    private ImageView resultIMG;
    @FXML
    private StackPane sp = new StackPane();
    @FXML
    private Label resultId;
    @FXML
    private Label resultCategoryId;
    @FXML
    private Label resultName;
    @FXML
    private Label resultRating;
    @FXML
    private Label resultPrice;
    @FXML
    private Label resultURL;
    @FXML
    private Label resultDesc;
    @FXML
    private Button searchButton;
    @FXML
    private ChoiceBox cbRequestType;
    ObservableList requestType = FXCollections.observableArrayList(new String[]{"Id", "Name"});
    @FXML
    private Label statusBarText;
    @FXML
    private ProgressBar statusBarProgressBar;

    @FXML
    private TextField dbHost;
    @FXML
    private TextField dbUser;
    @FXML
    private PasswordField dbPass;
    @FXML Button connectButton;
    @FXML
    private TextField dbName;
    @FXML
    private TextField dbTableName;

    @FXML
    private TableView tableData;

    public Controller() {

    }



    @FXML
    public void addProduct(){
        String tableName;
        tableName = dbTableName.getText();

        connection.addProductToDB(product,tableName);
        System.out.println("#LOG: Add...");
    }

    @FXML
    public void connectDB(){
        String host;
        String user;
        String pass;
        String db;
        String tableName = dbTableName.getText();
        db = dbName.getText();
        user = dbUser.getText();
        pass = dbPass.getText();

        host = "jdbc:mysql://" + dbHost.getText() + "/" + db;

        try {
            connection.connectToDB(host, user, pass);
            connection.buildData(tableName);
            System.out.println("#LOG: Connecting...");
            System.out.println("#LOG: host: " + host + ", user: " + user + ", pass: " + pass);
        } catch (SQLException e) {
            progressBarStatus("Connection problem!",2,0);
            System.out.println("#LOG: Connection error!");
            e.printStackTrace();
        } finally {
            progressBarStatus("Connected!",1,1);
            System.out.println("#LOG: Connected!");
        }
    }

    public void progressBarStatus(String message, int type, int progress) {
        statusBarText.setText(message);
        statusBarProgressBar.setProgress(progress);
        if( type == 1) {
            statusBarText.setTextFill(Color.web("#009900"));
        }
        else if ( type == 2) {
            statusBarText.setTextFill(Color.web("#700000"));
        }
    }

    @FXML
    public void searchProduct() {
        try {
            progressBarStatus("",1,0);
            String e = getRequest(requestString.getText());
            YandexParserAPI parser = ParserFactory.getParser("YANDEX");
            progressBarStatus("Parsing JSON",1,0);
            if(cbRequestType.getValue() == "Name") {
                product = parser.getProductByName(e);
            } else {
                product = parser.getProductById(Integer.parseInt(e));

            }
        } catch (Exception var7) {
            var7.printStackTrace();
        } finally {
            setResult(product);
            progressBarStatus("Done!",1,1);
        }

    }

    public void initialize(URL location, ResourceBundle resources) {
        requestString.setPromptText("Input your request...");
        resultId.setText("");
        resultURL.setText("");
        resultPrice.setText("");
        resultRating.setText("");
        resultCategoryId.setText("");
        resultName.setText("");
        resultDesc.setText("");
        cbRequestType.setItems(requestType);
        progressBarStatus("Ready!",1,0);
    }

    private String getRequest(String request) {
        int length = request.length();
        return request != null && length >= 3 && length <= 50?request:null;
    }


    private void setResult(Product product) {
        this.resultId.setText(String.valueOf(product.getId()));
        this.resultCategoryId.setText(String.valueOf(product.getCategoryId()));
        this.resultName.setText(product.getName());
        this.resultRating.setText(String.valueOf(product.getRating()));
        this.resultPrice.setText(product.getPriceAvg() + " " + product.getCurName());
        this.resultURL.setText(product.getProductURL());
        this.resultDesc.setText(product.getDescription());
        this.resultIMG.setImage(null);
        Image productIMG = new Image(product.getImgURL(), 175.0D, 195.0D, true, false);
        this.resultIMG = new ImageView(productIMG);
        this.sp.getChildren().add(this.resultIMG);
        this.sp.setMaxSize(200.0D, 180.0D);
    }

}

