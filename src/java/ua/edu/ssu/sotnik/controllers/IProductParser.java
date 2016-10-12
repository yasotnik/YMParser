package ua.edu.ssu.sotnik.controllers;

import ua.edu.ssu.sotnik.models.Category;
import ua.edu.ssu.sotnik.models.Product;


public interface IProductParser {

    Product getProductByName(String name);

    Product getProductById(int id);

    String openConnection(String urlAPIRequest);

    Category getCategories();
}
