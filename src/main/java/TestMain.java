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

//        User newUser = new User();
//        newUser.setName("MainUserName");
//        boolean isAdded = userService.add(newUser);
//        System.out.println(isAdded);
//        User dbUser = userService.findUserId("2");
//        dbUser.setName("MainUser2");
//        boolean isUpdated = userService.edit(dbUser);
//        System.out.println(isUpdated);

        userService.delete("97");


        List user_list = userService.getAll();

        System.out.println("Total records fetched from the mongo database are= " + user_list.size());

        for (Object o : user_list) {
            User user = (User) o;
            System.out.println(user.toString());
        }

    }
}
