package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ServiceGeneratorToolsBySplit {
	private static final String PATH_DAO = "E:\\workspace\\cs-service\\src\\main\\java\\com\\leweiyou\\service\\mybatis\\dao";
	private static final String PATH_SERVICE = "E:\\workspace\\cs-service\\src\\main\\java\\com\\leweiyou\\service\\service";
	
	
	private static final String PREX_ABSTRACT = "_";
	public static void main(String[] args) {
		 try {
			List<File> fileList = getFileList(new File(PATH_DAO));
		    for (int i = 0; i < fileList.size(); i++) {
		    	createFile(fileList.get(i));
		    }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  
	private static void createFile(File file) {
		String fileName = file.getName();
		if(!fileName.endsWith("Mapper.java")){
			return;
		}
		String entityName = fileName.substring(0, fileName.length() - 11);
		String entityNameLowerCase = entityName.substring(0,1).toLowerCase() + entityName.substring(1);
		File _serviceFile = new File(PATH_SERVICE, PREX_ABSTRACT + entityName + "Service.java");
		if(_serviceFile.exists()){
			_serviceFile.delete();
		}
		
		
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(_serviceFile)));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				//特殊处理
				if(line.startsWith("package")){
					line = "package com.leweiyou.service.service;\r\n\r\n";
					line += "import org.springframework.beans.factory.annotation.Autowired;\r\n";
					line += "import com.github.pagehelper.PageHelper;";
					line += "import com.github.pagehelper.PageInfo;";
					line += "import com.leweiyou.service.mybatis.dao." + entityName + "Mapper;\r\n";
				}else if(line.startsWith("public interface")){
					String es = "";
					if(line.trim().endsWith("{")){
						es = "{";
					}
					line = "public abstract class " + PREX_ABSTRACT + entityName + "Service" + es + "\r\n\r\n";
					
					//添加属性和方法
					line += "	@Autowired\r\n";
					line += "	private " + entityName + "Mapper " + entityNameLowerCase + "Mapper;\r\n";
					
					line += addMethod(entityName,entityNameLowerCase);
				
				}else if(!line.startsWith("import") && line.contains(";")){
					//对于方法判定
					String ms = line.trim().split(" ")[1].split("\\(")[0];
					String ps = line.trim().split(" ")[2].split("\\)")[0];
					if(line.contains("@Param")){
						line = "	public " + line.split("@Param")[0] + entityName + " record, " + entityName + "Example example){\r\n";
						line += "         return " + entityNameLowerCase + "Mapper." + ms + "(record, example);\r\n    }";
					}else{
						//int updateByPrimaryKeySelective(TUser record);
						line = "	public " + line.replace(";", "{\r\n        return " + entityNameLowerCase + "Mapper." + ms + "(" + ps + ");\r\n    }");
					}
				}
			   bw.write(line + "\r\n");               
			}
			System.out.println("Success:" + _serviceFile.getName());
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fail:" + _serviceFile.getName());
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {}
			}
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {}
			}
		}
		
		
		//判定继承类是否存在
		File serviceFile = new File(PATH_SERVICE, entityName + "Service.java");
		if(!serviceFile.exists()){
			try {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFile)));
				String line = "";
				line += "package com.leweiyou.service.service;\r\n\r\n";
				line += "import org.springframework.stereotype.Component;\r\n\r\n";
				line += "@Component\r\n";
				line += "public class " + entityName + "Service extends " + PREX_ABSTRACT + entityName + "Service{\r\n\r\n";
				line += "}";
				bw.write(line);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(bw != null){
					try {
						bw.close();
					} catch (IOException e) {}
				}
			}
		}
		
	}

	private static String addMethod(String entityName,String entityNameLowerCase) {
		String line = "\r\n";
		line += "	public PageInfo<" + entityName + "> selectByExample(" + entityName + "Example e,int offer, int limit) {\r\n";
		line += "		PageHelper.startPage(offer, limit);\r\n";
		line += "		PageInfo<" + entityName + "> page = new PageInfo<" + entityName + ">(" + entityNameLowerCase + "Mapper.selectByExample(e));\r\n";
		line += "		return page;\r\n";
		line += "	}\r\n\r\n";
		
		line +=" 	public " + entityName + " selectOne(" + entityName + "Example example){\r\n";
		line +="	 	List<" + entityName + "> list = " + entityNameLowerCase + "Mapper.selectByExample(example);\r\n";
		line +="     	return (list == null || list.size() == 0) ? null : list.get(0);\r\n";
		line +=" 	}\r\n\r\n";
		
		
		return line;
	}

	private static List<File> getFileList(File file) throws IOException {
		List<File> fileList = new ArrayList<File>();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					fileList.addAll(getFileList(files[i]));
				} else {
					fileList.add(files[i]);
				}
			}
		}else{
			fileList.add(file);
		}
		return fileList;
	}
	   
}
