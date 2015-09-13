package ua.edu.ssu.sotnik.models;

/**
 * Created by Sotnik on 25.03.2015.
 */
public class Category {

    private int id;
    private int parentId;

    private int modelsNum;
    private int childCount;
    private String name;



    public Category() {
    }

    public Category(int id, int parentId, int modelsNum, int childCount, String name) {
        this.id = id;
        this.parentId = parentId;
        this.modelsNum = modelsNum;
        this.childCount = childCount;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public int getModelsNum() {
        return modelsNum;
    }

    public int getChildCount() {
        return childCount;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setModelsNum(int modelsNum) {
        this.modelsNum = modelsNum;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public void setName(String name) {
        this.name = name;
    }
}
