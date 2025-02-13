package vttp.batch5.paf.movies.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import vttp.batch5.paf.movies.utils.MongoDBConstants;

@Repository
public class MongoMovieRepository {

    @Autowired
    private MongoTemplate template;

    // TODO: Task 2.3
    // You can add any number of parameters and return any type from the method
    // You can throw any checked exceptions from the method
    // Write the native Mongo query you implement in the method in the comments
    //
    //    native MongoDB query here

        // db.imdb.insert(
        // {
        //     _id : "tt1259521", 
        //     title : "The Cabin in the Woods",
        //     director : "Drew Goddard",
        //     overview : "overview string",
        //     tagline : "You think you know the story.", 
        //     genres : "Horror, Mystery",
        //     imdb_rating : 7,
        //     imdb_votes : 465176
        // }
        // )

    //
    public void batchInsertMovies(List<JsonObject> toInsert) {

    for (JsonObject jo : toInsert) {

        Document doc = JsonObjectToDocument(jo);
        template.insert(doc, MongoDBConstants.C_IMDB);

    }

    }

    // helper method 
    // Json Object --> Document
    public Document JsonObjectToDocument(JsonObject jo) {

        Document doc = new Document(); 

        doc.put("_id", jo.getString("imdb_id")); 
        doc.put("title", jo.getString("title")); 
        doc.put("director", jo.get("director"));
        doc.put("overview", jo.getString("overview"));
        doc.put("tagline", jo.getString("tagline"));
        doc.put("genres", jo.getString("genres"));
        doc.put("imdb_rating", doc.getInteger("imdb_rating"));
        doc.put("imdb_votes", jo.getInt("imdb_votes"));

        return doc;

    }

    // helper method 
    // count number of entries 
    // db.imdb.count({})
    public long countMongoDBEntries() {

        return template.count(new Query(), MongoDBConstants.C_IMDB);

    }


    // TODO: Task 2.4
    // You can add any number of parameters and return any type from the method
    // You can throw any checked exceptions from the method
    // Write the native Mongo query you implement in the method in the comments
    //
    //    native MongoDB query here
    //
    public void logError() {

    }

    // TODO: Task 3
    // Write the native Mongo query you implement in the method in the comments
    //
    //    native MongoDB query here
    //


}
