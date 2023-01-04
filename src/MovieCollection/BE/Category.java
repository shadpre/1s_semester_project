package MovieCollection.BE;

public class Category {
    String catName;
    int iD;

    public Category(String catName) {
        this.catName = catName;
    }

    public Category(String catName, int iD) {
        this.catName = catName;
        this.iD = iD;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getID() {
        return iD;
    }
}
