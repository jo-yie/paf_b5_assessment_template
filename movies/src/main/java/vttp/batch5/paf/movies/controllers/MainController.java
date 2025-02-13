package vttp.batch5.paf.movies.controllers;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vttp.batch5.paf.movies.services.MovieService;

@Controller
public class MainController {

  @Autowired
  private MovieService movieService; 

  // TODO: Task 3
  @GetMapping("/api/summary")
  public void getSummary(@RequestParam int count) {


  }

  @GetMapping("/test")
  @ResponseBody
  public List<Document> test() {

    return movieService.test();

  }

  // TODO: Task 4



}
