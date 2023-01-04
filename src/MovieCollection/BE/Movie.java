package MovieCollection.BE;

import java.util.ArrayList;

public class Movie {
    String MovieTittle;
    ArrayList<Category> Categories;
    float imdbRating;
    int personalRating;
    String localFilePath;

    public Movie(String movieTittle, ArrayList<Category> categories, float imdbRating, int personalRating, String localFilePath) {
        MovieTittle = movieTittle;
        Categories = categories;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.localFilePath = localFilePath;
    }

    public String getMovieTittle() {
        return MovieTittle;
    }

    public void setMovieTittle(String movieTittle) {
        MovieTittle = movieTittle;
    }

    public ArrayList<Category> getCategories() {
        return Categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        Categories = categories;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(int personalRating) {
        this.personalRating = personalRating;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }
}
