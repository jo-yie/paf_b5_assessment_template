package vttp.batch5.paf.movies.bootstrap;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.paf.movies.repositories.MongoMovieRepository;
import vttp.batch5.paf.movies.repositories.MySQLMovieRepository;
import vttp.batch5.paf.movies.services.MovieService;

@Component
public class Dataloader implements CommandLineRunner {

  @Autowired
  private MovieService movieService;

  @Autowired
  private MySQLMovieRepository mySQLMovieRepository;

  @Autowired 
  private MongoMovieRepository mongoMovieRepository; 

  //TODO: Task 2

  @Override
  public void run(String... args) throws Exception {

    // determine if movies_post_2010.zip has been loaded 
    // count > 0 TODO 
    // configurable file path 

    if (mySQLMovieRepository.countMySQLEntries() == 0 &&
      mongoMovieRepository.countMongoDBEntries() == 0) {

      File file = new File("../data/movies_post_2010.zip");

      System.out.println(">>>>FILE EXISTS: " + file.exists());
      ZipFile zf = new ZipFile(file);
  
      ZipEntry ze = zf.getEntry("movies_post_2010.json");
      System.out.println(">>>>GET NAME OF FILE: " + ze.getName());
      
      InputStream is = zf.getInputStream(ze);
  
      BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
  
      String input; 
      List<String> inputList = new ArrayList<>(); 
  
      while ((input = br.readLine()) != null) {
  
        // process input 
        inputList.add(input);
  
      }

      is.close();
      br.close();
  
      movieService.insertMovies(inputList);
  

    }



  }



}
