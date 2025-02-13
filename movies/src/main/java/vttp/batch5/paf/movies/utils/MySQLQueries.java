package vttp.batch5.paf.movies.utils;

public class MySQLQueries {

    public static final String SQL_INSERT_MOVIE = 
    """
        INSERT INTO imdb (imdb_id, vote_average, vote_count, release_date, revenue, budget, runtime)     
            VALUES (?, ?, ?, ?, ?, ?, ?)   
    """;

    // INSERT INTO X VALUES(Y,Z)    WHERE Y  NOT IN (SELECT Y FROM X)


    public static final String SQL_COUNT_ENTRIES = 
    """
        SELECT COUNT(*) FROM imdb        
    """;

    public static final String SQL_GET_MOVIE_BY_ID = 
    """
        SELECT imdb_id, revenue, budget FROM imdb
            WHERE imdb_id = ?        
    """;
    
}
