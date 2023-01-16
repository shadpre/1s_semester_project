package MovieCollection.BLL.Util;

import MovieCollection.BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class Searcher {
    public Searcher() {
    }

    /**
     * gets a arraylist of movies, and a query and returns a list of movies where the tittle contained the query value
     * @param fullScratchList
     * @param query
     * @return
     * @throws Exception
     */
    public List<Movie> search(List<Movie> fullScratchList, String query) throws Exception{
        List<Movie> result = new ArrayList<>();

        for (Movie movie : fullScratchList) {
            if(compareToMovieTitle(query, movie))
            {
                result.add(movie);
            }
        }

        return result;
    }

    /**
     * check movies tittle where the tittle contains query
     * @param query
     * @param movie
     * @return
     */
    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getMovieTittle().toLowerCase().contains(query.toLowerCase());
    }
}
