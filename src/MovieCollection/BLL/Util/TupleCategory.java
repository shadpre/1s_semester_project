package MovieCollection.BLL.Util;

import MovieCollection.BE.Category;

public class TupleCategory {

    private static Category category;

    public TupleCategory() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
