package MovieCollection.BE;

public class Category {
    private String catName;
    private int iD;

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

    @Override
    public String toString() {
        return  catName ;
    }

    public void setId(int iD) {
        this.iD = iD;
    }
}
