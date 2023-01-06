package MovieCollection.BE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Movie {
    String MovieTittle;
    ArrayList<Category> Categories;
    float imdbRating;
    float personalRating;
    String localFilePath;
    int iD;
    LocalDateTime lastPlayDate;

    public LocalDateTime getLastPlayDate() {
        return lastPlayDate;
    }

    public void setLastPlayDate(LocalDateTime lastPlayDate) {
        this.lastPlayDate = lastPlayDate;
    }

    public Movie(String movieTittle, float imdbRating, float personalRating, String localFilePath, int iD) {
        MovieTittle = movieTittle;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.localFilePath = localFilePath;
        this.iD = iD;
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

    public float getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(float personalRating) {
        this.personalRating = personalRating;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public int getiD() {
        return iD;
    }
}
