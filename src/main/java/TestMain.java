import com.jcg.springmvc.mongo.User;
import com.jcg.springmvc.mongo.UserService;
import com.jcg.springmvc.mongo.factory.MongoFactory;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {

        UserService userService = new UserService();

        User newUser = new User();
        newUser.setName("MainUserName");
        boolean isAdded =  userService.add(newUser);
        System.out.println(isAdded);

        List user_list = new ArrayList();
        MongoCollection<Document> coll = MongoFactory.getCollection("mycollection");

        // Fetching cursor object for iterating on the database records.
        MongoCursor<Document> cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document dbObject = cursor.next();

            User user = new User();
            user.setId(dbObject.get("id").toString());
            user.setName(dbObject.get("name").toString());

            // Adding the user details to the list.
            user_list.add(user);
        }
        System.out.println("Total records fetched from the mongo database are= " + user_list.size());

        for (Object o : user_list) {
            User user = (User) o;
            System.out.println(user.toString());
        }

    }
}
