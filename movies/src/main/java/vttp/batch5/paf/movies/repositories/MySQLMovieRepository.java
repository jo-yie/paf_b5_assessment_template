package vttp.batch5.paf.movies.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
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


}
