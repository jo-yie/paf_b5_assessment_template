package vttp.batch5.paf.movies.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import vttp.batch5.paf.movies.models.Director;
import vttp.batch5.paf.movies.repositories.MongoMovieRepository;
import vttp.batch5.paf.movies.repositories.MySQLMovieRepository;

@Service
public class MovieService {

  @Autowired
  private MySQLMovieRepository mySQLMovieRepository; 

  @Autowired
  private MongoMovieRepository mongoMovieRepository; 

  // TODO: Task 2
  // @Transactional
  public void insertMovies(List<String> inputList) {

    List<JsonObject> toInsert = new ArrayList<>();

    for (String s : inputList) {

      StringReader sr = new StringReader(s);
      JsonReader jr = Json.createReader(sr); 
      JsonObject jo = jr.readObject();

      // clean missing data 
      cleanJsonObject(jo);

      // release date >= 2018
      String releaseDate = jo.getString("release_date");
      int releaseYear = Integer.valueOf(releaseDate.substring(0, 4));
      if (releaseYear >= 2018) {
        toInsert.add(jo);

      }

    }

    // for toInsert 
    // add 25 to a new list 
    while (toInsert.size() != 0) {

      List<JsonObject> batchOf25 = new ArrayList<>();
      // int first = 0;

      if (toInsert.size() < 25) {

        for (int i = 0; i < toInsert.size(); i++) {
          batchOf25.add(toInsert.get(i));
          toInsert.remove(i);

        }

      }

      else {
        for (int i = 0; i < 25; i++) {

          batchOf25.add(toInsert.get(i)); 
          toInsert.remove(i);
  
        }

        // first = first + 25;

      }


      try {

        mySQLMovieRepository.batchInsertMovies(batchOf25);
        mongoMovieRepository.batchInsertMovies(batchOf25);

      } catch (Exception e) {

        System.out.println(e.getMessage());

      }

    }

  }

  // helper method 
  // clean null values 
  public JsonObject cleanJsonObject(JsonObject jo) {

    JsonString emptyString = Json.createValue("");
    JsonValue zero = (JsonValue) Json.createValue(0);

    String[] fields = {"title",
      "vote_average",
      "vote_count", 
      "status", 
      "release_date", 
      "revenue",
      "runtime", 
      "budget", 
      "imdb_id", 
      "original_language",
      "overview", 
      "popularity", 
      "tagline", 
      "genres", 
      "spoken_languages", 
      "casts", 
      "director", 
      "imdb_rating", 
      "imdb_votes", 
      "poster_path"};

    String[] types = {"String",
    "int",
    "int", 
    "String", 
    "String", 
    "int",
    "int", 
    "int", 
    "String", 
    "String",
    "String", 
    "int", 
    "String", 
    "String", 
    "String", 
    "String", 
    "String", 
    "int", 
    "int", 
    "String"};

    for (int i = 0; i < fields.length; i++) {
      if (jo.get(fields[i]) == null) {
        if (types[i] == "String") {
          jo.replace(fields[i], emptyString);
        } else if (types[i] == "int") {
          jo.replace(fields[i], zero);
        }
      }

    }

    return jo;

  }

  // TODO: Task 3
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public List<Director> getProlificDirectors(int count) {

    List<Director> directors = new ArrayList<>(); 

    List<Document> fromMongo = mongoMovieRepository.getProlificDirectorsFromMongo(count);
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

    for (Document d : fromMongo) {
      List<String> imdbIds = d.getList("imdb_ids", String.class);
      
      int totalRevenue = 0;
      int totalBudget = 0;

      for (String imdbId : imdbIds) {
        Director director = mySQLMovieRepository.getProlificDirectorsFromMySQL(imdbId);
        totalRevenue = totalRevenue + (int) director.getTotal_revenue(); 
        totalBudget = totalBudget + (int) director.getTotal_budget();

      }

      Director dir = new Director(); 
      dir.setDirector_name(d.getString("_id"));
      dir.setMovies_count(d.getInteger("movies_count"));
      dir.setTotal_revenue(totalRevenue);
      dir.setTotal_budget(totalBudget);

      directors.add(dir);

      JsonObject jsonObject = Json.createObjectBuilder()
        .add("director_name", dir.getDirector_name())
        .add("movies_count", dir.getMovies_count())
        .add("total_revenue", dir.getTotal_revenue())
        .add("total_budget", dir.getTotal_budget())
        .build();

      jsonArrayBuilder.add(jsonObject);

    }

    // return jsonArrayBuilder.build();

    return directors;


  }




  // TODO: Task 4
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public void generatePDFReport() {

  }

}
