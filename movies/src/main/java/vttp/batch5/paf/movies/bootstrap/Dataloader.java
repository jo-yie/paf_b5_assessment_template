package vttp.batch5.paf.movies.bootstrap;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Dataloader implements CommandLineRunner {

  //TODO: Task 2

  @Override
  public void run(String... args) throws Exception {

    // determine if movies_post_2010.zip has been loaded 
    // count > 0

    File file = new File("../data/movies_post_2010.zip");

    System.out.println(">>>>FILE EXISTS: " + file.exists());
    ZipFile zf = new ZipFile(file);

    ZipEntry ze = zf.getEntry("movies_post_2010.json");
    System.out.println(">>>>GET NAME OF FILE: " + ze.getName());
    
    System.out.println(">>>>FILE ENTRIES: " + zf.entries());

    // try {
    //   InputStream is = zf.getInputStream(ze);

    //   String result = IOUtils.toString(is, StandardCharsets.UTF_8);

    //   System.out.println(result);

    // } catch (Exception e) {

    //   System.out.println(e.getMessage());

    // }

  }



}
