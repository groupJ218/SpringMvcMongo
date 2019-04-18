package com.jcg.springmvc.mongo.factory;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

@SuppressWarnings("deprecation")
public class MongoFactory {

    private static Logger log = Logger.getLogger(MongoFactory.class);

    private static Mongo mongo;

    public MongoFactory() {
    }

    // Returns a mongo instance.
    public static MongoDatabase getMongo() {
//        int port_no = 27017 ;
//        String hostname = "127.0.0.1";

        // Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname
//
//        MongoClientURI uri  = new MongoClientURI("mongodb://user:pass@host:port/db");
//        MongoClient client = new MongoClient(uri);
//        MongoDatabase db = client.getDatabase(uri.getDatabase());

        MongoDatabase db = null;
        try {

            // Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname

            MongoClientURI uri = new MongoClientURI("mongodb://test123:test123@ds161136.mlab.com:61136/mydb");
            MongoClient client = new MongoClient(uri);
            db = client.getDatabase(uri.getDatabase());


        } catch (MongoException ex) {
            log.error(ex);
        }


        return db;
    }


    // Fetches the collection from the mongo database.
    public static MongoCollection getCollection(String db_collection) {
        return getMongo().getCollection(db_collection) ;
    }

}
