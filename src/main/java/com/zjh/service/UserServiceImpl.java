package com.zjh.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zjh.vo.User;
import com.zjh.dao.*;

public class UserServiceImpl implements IUserService{
	private IUserDAO dao=new UserDAOImpl();
	public User login(User user) {
		return dao.login(user);
	}

	public boolean register(HttpServletRequest request) {
		String path=request.getServletContext().getRealPath("/");//项目目录
		
		DiskFileItemFactory factory=new DiskFileItemFactory();
		factory.setRepository(new File(path+"/temp/"));//缓存目录=项目目录/temp/
		factory.setSizeThreshold(1024*1024*100);//缓存目录大小
		
		ServletFileUpload upload=new ServletFileUpload(factory);
		upload.setFileSizeMax(1024*1024*5);//设置文件大小
		
		String filePath=null;
		User user=new User();
		try {
			List<FileItem> list=upload.parseRequest(request);//解析request，获取所有文件列表
			for(FileItem f:list){
				if(!f.isFormField()){
					String filename=f.getName();
					int point=filename.lastIndexOf(".");
					String type=filename.substring(point);
					filePath=path+"/upload/"+UUID.randomUUID()+type;
					f.write(new File(filePath));//保存文件
				}else{
					String fieldname=f.getFieldName();//获取表单域中的name值
					switch(fieldname){//判断name，并获取对应value
					case "username":
						String username=new String(f.getString().getBytes("iso-8859-1"),"utf-8");
						user.setUsername(username);
						break;
					case "password":
						String password=new String(f.getString().getBytes("iso-8859-1"),"utf-8");
						user.setPassword(password);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao.register(filePath, user);
	}

}
