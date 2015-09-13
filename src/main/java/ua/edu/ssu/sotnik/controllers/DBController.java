package ua.edu.ssu.sotnik.controllers;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import ua.edu.ssu.sotnik.models.Product;

import java.sql.*;

public class DBController {

    Connection connection = null;
    Statement stmt = null;

    public boolean connectToDB(String url, String username, String password) throws SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnectionToDB(){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch(SQLException se){
            se.printStackTrace();
        }
    }

    public void addProductToDB(Product product, String table){
        try {
            stmt = connection.createStatement();
            String sql;
            sql = "INSERT INTO `" + table + "`(`id`, `category_id`, `name`, `rating`, `price`, `url`, `desc`) VALUES (" + product.getId() + "," + product.getCategoryId() + ",\"" + product.getName() + "\"," + product.getRating() + "," + product.getPriceAvg() + ",\"" + product.getProductURL() + "\",\""+ product.getDescription() + "\")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int a;
    private ObservableList<ObservableList> data;
    private TableView tableView;

    public void buildData(String tableName) {
        data = FXCollections.observableArrayList();
        try {
            String select = "SELECT * from " + tableName;
            ResultSet resultSet = connection.createStatement().executeQuery(select);

            for(int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                final int j = 1;
                TableColumn tableColumn = new TableColumn(resultSet.getMetaData().getColumnClassName(i++));
                tableColumn.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tableView.getColumns().addAll(tableColumn);
            }
                while (resultSet.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        row.add(resultSet.getString(i));
                    }
                    data.add(row);
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

