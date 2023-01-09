package MovieCollection.BLL.Util;

import MovieCollection.BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class Searcher {
    public Searcher() {
    }

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

    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getMovieTittle().toLowerCase().contains(query.toLowerCase());
    }
}
