package vttp.batch5.paf.movies.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonString;
import vttp.batch5.paf.movies.repositories.MongoMovieRepository;
import vttp.batch5.paf.movies.repositories.MySQLMovieRepository;

@Service
public class MovieService {

  @Autowired
  private MySQLMovieRepository mySQLMovieRepository; 

  @Autowired
  private MongoMovieRepository mongoMovieRepository; 

  // TODO: Task 2
  @Transactional
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



    // insert into sql 
    mySQLMovieRepository.batchInsertMovies(toInsert);

    // insert into mongo 
    mongoMovieRepository.batchInsertMovies(toInsert);



  }

  // helper method 
  // clean null values 
  public JsonObject cleanJsonObject(JsonObject jo) {

    JsonString emptyString = Json.createValue("");
    JsonNumber zero = Json.createValue(0);

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
  public void getProlificDirectors() {
  }


  // TODO: Task 4
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public void generatePDFReport() {

  }

}
