package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Freemarker;

public class ServiceGeneratorToolsByFreemark {
	private static final String Parent_Package = "com.chinanetcenter.mapper.itop";
	private static final String PATH_Entry = "E:\\workspace\\cs-mapper-itop\\trunk\\src\\main\\java\\com\\chinanetcenter\\mapper\\itop\\mybatis\\dao";
	private static final String PATH_SERVICE = "E:\\workspace\\cs-mapper-itop\\trunk\\src\\main\\java\\com\\chinanetcenter\\mapper\\itop\\service";
	
	
	private static final String PREX_ABSTRACT = "_";
	public static void main(String[] args) {
		 try {
			List<File> fileList = getFileList(new File(PATH_Entry));
		    for (int i = 0; i < fileList.size(); i++) {
		    	createFile(fileList.get(i));
		    }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  
	private static void createFile(File file) {
		String path = file.getParent();
		String morePath = path.replace(PATH_Entry, "");

		String fullPath = PATH_SERVICE + morePath;
		
		String morePackage = morePath.replace("\\", ".");
		
		File f = new File(fullPath);
		if(!f.exists()){
			f.mkdirs();
		}
		
		String text = txt2String(file);
		String primaryKeyClass = "";
		
		boolean isExistBLOBs = false;
		if(text.contains("WithBLOBs")){
			isExistBLOBs = true;
		}
		primaryKeyClass = text.split("deleteByPrimaryKey\\(")[1].split(" ")[0];
		
		String entryName = file.getName().replace("Mapper.java", "");
		String entityNameLowerCase = entryName.substring(0,1).toLowerCase() + entryName.substring(1);
		
		Map<String, Object> root = new HashMap<String,Object>();
		root.put("entryName", entryName);
		root.put("entityNameLowerCase", entityNameLowerCase);
		root.put("primaryKeyClass", primaryKeyClass);
		root.put("isExistBLOBs", isExistBLOBs + "");
		root.put("morePackage", morePackage);
		root.put("parentPackage", Parent_Package);
		
		try {
			Freemarker.printFile("_Service.ftl", root, fullPath, PREX_ABSTRACT + entryName + "Service.java");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//判定继承类是否存在
		File serviceFile = new File(fullPath, entryName + "Service.java");
		BufferedWriter bw = null;
		if(!serviceFile.exists()){
			try {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(serviceFile)));
				String line = "";
				line += "package " + Parent_Package + ".service" + morePackage + ";\r\n\r\n";
				line += "import org.springframework.stereotype.Component;\r\n\r\n";
				line += "@Component\r\n";
				line += "public class " + entryName + "Service extends " + PREX_ABSTRACT + entryName + "Service{\r\n\r\n";
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
		System.out.println("生成完毕");
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
	
    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file){
        String result = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result = result + "\n" +s;
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
	   
}
