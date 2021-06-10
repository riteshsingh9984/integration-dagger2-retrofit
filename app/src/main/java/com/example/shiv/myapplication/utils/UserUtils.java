package com.example.shiv.myapplication.utils;

import com.example.shiv.myapplication.modals.User;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    public static List<User> getUsers(int start){
        List<User> users = new ArrayList<>();
        int end = start+10;
        while(start <= end){
            users.add(new User("test"+start+"@shrim.com", "username"+start));
            start++;
        }

        return users;
    }
}
