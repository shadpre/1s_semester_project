package MovieCollection.BE;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Movie {
    private String MovieTittle;
    private ArrayList<Category> Categories;
    private float imdbRating;
    private float personalRating;
    private String localFilePath;
    private int iD;
    private LocalDateTime lastPlayDate;

    /**
     *
     * @return
     */
    public LocalDateTime getLastPlayDate() {
        return lastPlayDate;
    }

    /**
     *
     * @param lastPlayDate
     */
    public void setLastPlayDate(LocalDateTime lastPlayDate) {
        this.lastPlayDate = lastPlayDate;
    }

    /**
     *
     * @param movieTittle
     * @param imdbRating
     * @param personalRating
     * @param localFilePath
     * @param iD
     */
    public Movie(String movieTittle, float imdbRating, float personalRating, String localFilePath, int iD) {
        MovieTittle = movieTittle;
        this.imdbRating = imdbRating;
        this.personalRating = personalRating;
        this.localFilePath = localFilePath;
        this.iD = iD;
    }

    public void setId(int iD) {
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

    @Override
    public String toString() {
        return getMovieTittle();
    }
}
