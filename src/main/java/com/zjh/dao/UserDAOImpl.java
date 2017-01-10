package com.zjh.dao;

import com.zjh.db.DB;
import com.zjh.vo.User;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class UserDAOImpl implements IUserDAO {
	public User login(User user) {
		Connection conn=DB.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		User user2=null;
		try {
			String sql="select * from user where username=? and password=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,user.getUsername());
			pstmt.setString(2,user.getPassword());
			rs=pstmt.executeQuery();
			if(rs.next()){
				user2=new User();
				user2.setId(rs.getInt("id"));
				user2.setUsername(rs.getString("username"));
				user2.setPassword(rs.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
				pstmt.close();
				DB.closeConnection();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return user2;
	}

	public boolean register(String filePath, User user) {
		Connection conn=DB.getConnection();
		PreparedStatement pstmt=null;
		boolean flag=false;
		try{
			String sql="insert into user(username,password,pic)values(?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,user.getUsername());
			pstmt.setString(2,user.getPassword());
			
			FileInputStream fis=new FileInputStream(new File(filePath));
			//pstmt.setBinaryStream(3, fis, fis.available());
			pstmt.setBlob(3,fis);
			
			flag=pstmt.executeUpdate()>0?true:false;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			DB.closeConnection();
		}
		return flag;
	}

}
