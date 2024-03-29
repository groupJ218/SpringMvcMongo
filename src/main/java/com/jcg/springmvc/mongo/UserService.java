package com.jcg.springmvc.mongo;

import com.jcg.springmvc.mongo.factory.MongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

@Service("UserService")
@Transactional
public class UserService {
    static String db_collection = "mycollection";
    private static Logger log = Logger.getLogger(UserService.class);

    // Fetch all users from the mongo database.
    public List getAll() {
        List user_list = new ArrayList();
        MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

        // Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();

            User user = new User();
            user.setId(document.get("id").toString());
            user.setName(document.get("name").toString());

            // Adding the user details to the list.
            user_list.add(user);
        }
        log.debug("Total records fetched from the mongo database are= " + user_list.size());
        return user_list;
    }

    // Add a new user to the mongo database.
    public Boolean add(User user) {
        boolean output = false;
        Random ran = new Random();
        log.debug("Adding a new user to the mongo database; Entered user_name is= " + user.getName());
        try {
            MongoCollection coll = MongoFactory.getCollection(db_collection);

            // Create a new object and add the new user details to this object.
            Document doc = new Document();
            doc.put("id", String.valueOf(ran.nextInt(100)));
            doc.put("name", user.getName());

            // Save a new user to the mongo collection.
            coll.insertOne(doc);
            output = true;
        } catch (Exception e) {
            output = false;
            log.error("An error occurred while saving a new user to the mongo database", e);
        }
        return output;
    }

    // Update the selected user in the mongo database.
    public Boolean edit(User user) {
        System.out.println("User from update method "+ user.toString());
        boolean output = false;
        log.debug("Updating the existing user in the mongo database; Entered user_id is= " + user.getId());
        Bson filter;
        Bson query;
        try {
            // Fetching the user details.
            Document existing = getDocument(user.getId());
            System.out.println("Update Method" + existing.toString());

            MongoCollection<Document> coll = MongoFactory.getCollection(db_collection);

            // Create a new object and assign the updated details.


            Document edited = new Document();
            edited.put("id", user.getId());
            edited.put("name", user.getName());

            // Update the existing user to the mongo database.
            coll.replaceOne(eq("id", user.getId()), edited);

            output = true;
        } catch (Exception e) {
            output = false;
            log.error("An error has occurred while updating an existing user to the mongo database", e);
        }
        return output;
    }

    // Delete a user from the mongo database.
    public Boolean delete(String id) {
        boolean output = false;
        log.debug("Deleting an existing user from the mongo database; Entered user_id is= " + id);
        try {
            // Fetching the required user from the mongo database.
            Document item = (Document) getDocument(id);
            MongoCollection coll = MongoFactory.getCollection(db_collection);

            // Deleting the selected user from the mongo database.
            coll.deleteOne(eq("id", id));
            output = true;
        } catch (Exception e) {
            output = false;
            log.error("An error occurred while deleting an existing user from the mongo database", e);
        }
        return output;
    }

    // Fetching a particular record from the mongo database.
    private Document getDocument(String id) {
        MongoCollection coll = MongoFactory.getCollection(db_collection);

        // Fetching the record object from the mongo database.
        Document where_query = new Document();

        // Put the selected user_id to search.
//        where_query.put("id", id);
        return (Document) coll.find(eq("id", id)).first();
    }

    // Fetching a single user details from the mongo database.
    public User findUserId(String id) {
        User u = new User();
        MongoCollection coll = MongoFactory.getCollection(db_collection);

        // Fetching the record object from the mongo database.
        Document where_query = new Document();
//        where_query.put("id", id);

        Document dbo = (Document) coll.find(eq("id", id)).first();
        System.out.println(dbo.toString());
        u.setId(dbo.get("id").toString());
        u.setName(dbo.get("name").toString());

        // Return user object.
        return u;
    }
}
