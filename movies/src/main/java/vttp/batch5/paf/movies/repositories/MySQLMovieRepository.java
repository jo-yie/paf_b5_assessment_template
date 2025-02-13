package vttp.batch5.paf.movies.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import vttp.batch5.paf.movies.models.Director;
import vttp.batch5.paf.movies.utils.MySQLQueries;

@Repository
public class MySQLMovieRepository {

  @Autowired
  JdbcTemplate template; 

  // TODO: Task 2.3
  // You can add any number of parameters and return any type from the method
  public void batchInsertMovies(List<JsonObject> toInsert) {

    for (JsonObject jo : toInsert) {

      template.update(MySQLQueries.SQL_INSERT_MOVIE,
        jo.getString("imdb_id"),
        jo.getInt("vote_average"), 
        jo.getInt("vote_count"), 
        jo.getString("release_date"), 
        jo.getInt("revenue"), 
        jo.getInt("budget"),
        jo.getInt("runtime"));


    }

  }
  
  // helper method 
  // count number of entries 
  public int countMySQLEntries() {

    return template.queryForObject(MySQLQueries.SQL_COUNT_ENTRIES,
     Integer.class);

  }

  // TODO: Task 3
  public Director getProlificDirectorsFromMySQL(String imdbId) {

    SqlRowSet rs = template.queryForRowSet(MySQLQueries.SQL_GET_MOVIE_BY_ID, 
      imdbId);

    Director d = new Director();

    if (rs.next()) {
      d.setTotal_revenue(rs.getFloat("revenue"));
      d.setTotal_budget(rs.getFloat("budget"));

    }
    
    return d;

  }

}
