/*******************************************************************************
 * Copyright (c) CASABOTS 2016
 * All Rights Reserved
 ******************************************************************************/

package com.casabots.pantry.controller;

import com.casabots.pantry.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private MongoOperations mongoOperation;

    @RequestMapping(method = RequestMethod.POST, path="/createUser")
    public void createUser(@RequestBody User user) {
        mongoOperation.save(user);
    }

    @RequestMapping(path="/getUser/{fName}")
    public User getPerson(@PathVariable String fName) {
        return mongoOperation.findOne(new Query(Criteria.where("firstName").is(fName)), User.class);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/updateUser")
    public void updateUser(@RequestBody User user) {
        User currentUser = mongoOperation.findOne(new Query(Criteria.where("firstName").is(user.getFirstName())), User.class);
        currentUser.updateUser(user);
        mongoOperation.save(currentUser);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteUser/{fName}")
    public void deleteUser(@PathVariable final String fName) {
        User userTobeDeleted = mongoOperation.findOne(new Query(Criteria.where("firstName").is(fName)), User.class);
        mongoOperation.remove(userTobeDeleted);
    }
}
