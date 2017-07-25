/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dto.User;
import java.util.HashMap;

/**
 *
 * @author Shikhar Jain
 */
public class LoginService {
    
    HashMap<String, String> users = new HashMap<String, String>();
    
    public LoginService() {
    
    users.put("sarika", "Sarika jain");
    users.put("shikhar", "Shikhar jain");
    users.put("nishant", "Nishant NIT");
}
    public  boolean authenticate(String userId , String password){
        if(password == null || password.trim() == "") {
            return false;
        }
        return true;
    }
    public User getUsername(String userId) {
        User user = new User();
        user.setUserName(users.get(userId));
        user.setUserId(userId);
        return user;
    }
}
