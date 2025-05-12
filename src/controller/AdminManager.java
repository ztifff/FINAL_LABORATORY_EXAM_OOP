package controller;

import java.util.ArrayList;
import java.util.List;

import model.Admin;

public class AdminManager {
    private static AdminManager instance;
    private List<Admin> admins;

    private AdminManager() {
        admins = new ArrayList<>();
        admins.add(new Admin("admin", "admin123"));
    }

    public static AdminManager getInstance() {
        if (instance == null) {
            instance = new AdminManager();
        }
        return instance;
    }

    public Admin validate(String username, String password) {
        for (Admin admin : admins) {
            if (admin.getName().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }
}
