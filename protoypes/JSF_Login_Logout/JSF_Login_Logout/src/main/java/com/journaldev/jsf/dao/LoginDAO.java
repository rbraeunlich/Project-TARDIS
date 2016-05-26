package com.journaldev.jsf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAO {
    
	public static boolean validate(String user, String password) {
            if(user == "ksm2456" && password=="asdf")
		return true;
            else 
                return false;
	}
}