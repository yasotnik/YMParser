package ua.edu.ssu.sotnik.models;

/**
 *
 * This class is a model for product
 * which is parsed from <a href="market.yandex.ru">market.yandex.ru<a/>
 *
 */
public class Product {
    /**
     * Product id
     */
    private int id;
    /**
     * Category id of product
     */
    private int categoryId;
    /**
     * name of product
     */
    private String name;
    /**
     * Description of product
     */
    private String description;
    private String curName;

    private String imgURL;
    private String productURL;

    private int priceAvg;
    private float rating;

    private String noTxtInformation = "Нет информации";
    private int noNumInformation = 0;

    public String getNoCurInfo() {
        return noCurInfo;
    }

    public void setNoCurInfo(String noCurInfo) {
        this.noCurInfo = noCurInfo;
    }

    private String noCurInfo = "";

    public Product() {
    }

    public Product(int id, int categoryId, String name, String vendor, String description, String curName, String imgURL, String productURL, int priceAvg, float rating) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.curName = curName;
        this.imgURL = imgURL;
        this.productURL = productURL;
        this.priceAvg = priceAvg;
        this.rating = rating;
    }


    public void setAllNull() {
        this.setName(noTxtInformation);
        this.setRating(noNumInformation);
        this.setId(noNumInformation);
        this.setProductURL(noTxtInformation);
        this.setCategoryId(noNumInformation);
        this.setDescription(noTxtInformation);
        this.setCurName(noCurInfo);
        this.setImgURL(noTxtInformation);
        this.setPriceAvg(noNumInformation);
    }

    public String getNoTxtInformation() {
        return noTxtInformation;
    }

    public int getNoNumInformation() {
        return noNumInformation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", rating='").append(rating).append('\'');
        sb.append(", priceAvg=").append(priceAvg);
        sb.append(", curName='").append(curName).append('\'');
        sb.append(", imgURL='").append(imgURL).append('\'');
        sb.append(", productURL='").append(productURL).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    public float getPriceAvg() {
        return priceAvg;
    }

    public void setPriceAvg(int priceAvg) {
        this.priceAvg = priceAvg;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
