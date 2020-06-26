package com.Service;

import com.Entity.Users;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersService {

    public boolean QueryUser(Users user)
    {
        String sql = "select * from tb_user where username='"+user.getUsername()+"'and password='"+user.getPassword()+"'";
        ResultSet rs = DAL.SqlHelper.executeQuery(sql);
        try{
            if (rs.next())
            {
                return true;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;

    }
}
