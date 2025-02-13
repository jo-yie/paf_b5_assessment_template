package vttp.batch5.paf.movies.controllers;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import net.sf.jasperreports.engine.JRException;
import vttp.batch5.paf.movies.models.Director;
import vttp.batch5.paf.movies.services.MovieService;

@Controller
public class MainController {

  @Autowired
  private MovieService movieService; 

  // TODO: Task 3
  @GetMapping("/api/summary")
  public ResponseEntity<List<Director>> getSummary(@RequestParam("count") int count) {

    return ResponseEntity.ok()
      .body(movieService.getProlificDirectors(count));

  }

  // @GetMapping("/test")
  // @ResponseBody
  // public JsonArray test() {

  //   return movieService.test();

  // }

  // TODO: Task 4
  @GetMapping("/api/summary/pdf")
  public void generatePDFReport(@RequestParam("count") int count) throws JRException {

    movieService.generatePDFReport(count);

  }


}
