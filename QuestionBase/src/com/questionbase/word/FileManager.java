package com.questionbase.word;

import java.util.*; 
import java.io.*;

public class FileManager {
	
private int getRandom(int max){
		//Radom
		Random   rand   =   new   Random();
		int randID = rand.nextInt(max-1)+1;
		return randID;
}
	
public String getRandomFileName(){
		Date now = new Date();
		String fileName = "file" + now.getTime() + "a" + getRandom(10000);
		return fileName;
}
	
public String getRandomFilePath(String parentPath){
		
		String path = getRandom(200) + File.separator  + getRandom(200) + File.separator ;
		File file = new File(parentPath + File.separator + path);
		file.mkdirs();
		return path;
}
	
public boolean deleteFile(String filePath){
		
		File file = new File(filePath);
		return file.delete();
}

public boolean CreateDocHtmFolder(String rootFolder)
{
	//create doc folder and htm folder
		//String rootFolder = request.getSession().getServletContext().getRealPath("/");
		if(!rootFolder.endsWith("/")){
			rootFolder = rootFolder + File.separator;
		}
		String newDocFolder = rootFolder + "doc";
		String newHtmFolder = rootFolder + "htm";
		File newfDocFolder = new File(newDocFolder);
		File newfHtmFolder = new File(newHtmFolder);
		if(!newfDocFolder.exists()){
			newfDocFolder.mkdir();
		}
		if(!newfHtmFolder.exists()){
			newfHtmFolder.mkdir();
		}
		return true;
}
}
