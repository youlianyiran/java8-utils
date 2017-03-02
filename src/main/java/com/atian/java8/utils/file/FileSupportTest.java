package com.atian.java8.utils.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xutiantian on 16/1/6.
 */
public class FileSupportTest {

    public static void main(String[] args) {
        //System.out.println(FileUtils.getTempDirectoryPath());
        //System.out.println(FileUtils.getUserDirectoryPath());

        //selectUsermngProfile();

        createUsermngProfileHistory();
    }

    public static void selectUsermngProfile(){
        FileSupport fileSupport = new FileSupport();
        String fromFileName = "/Users/xutiantian/deleteid.txt";
        String toFileName = "/Users/xutiantian/usermng-profile-select.txt";

        File toFile = fileSupport.createFile(toFileName);

        List<String> lines = fileSupport.readLines(new File(fromFileName));

        StringBuffer buffer = new StringBuffer();
        buffer.append("select * from usermng_profile where user_id in (");
        for (String line : lines) {
            if(StringUtils.isNotBlank(line)){
                buffer.append("'");
                buffer.append(line);
                buffer.append("'");
                buffer.append(",");
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(");");
        fileSupport.writeStringToFile(toFile, buffer.toString());
    }

    public static void createUsermngProfileHistory(){
        FileSupport fileSupport = new FileSupport();
        String fromFileName = "/Users/xutiantian/1111.txt";
        String toFileName = "/Users/xutiantian/usermng-profile-history-insert.txt";

        File toFile = fileSupport.createFile(toFileName);

        List<String> lines = fileSupport.readLines(new File(fromFileName));

        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT into usermng_profile_history (original_id, user_id, id_card_no, full_name, email, mp, type) VALUES ");
        for (String line : lines) {
            if(StringUtils.isNotBlank(line)) {
                String[] properties = line.split("\\s+");
                if(properties.length == 7) {
                    buffer.append("(");
                    for (String property : properties) {
                        if (!property.equals("NULL")) {
                            buffer.append("'");
                            buffer.append(property);
                            buffer.append("'");
                        } else {
                            buffer.append(property);
                        }
                        buffer.append(",");
                    }
                    buffer.deleteCharAt(buffer.length() - 1);
                    buffer.append("),");
                }else{
                    System.out.println(line);
                }
            }else{
                System.out.println(line);
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(";");
        fileSupport.writeStringToFile(toFile, buffer.toString());
    }

    public static void testFindFiles(){
        String baseDIR = "/Users/xutiantian/filetest";
        //    找扩展名为txt的文件
        String fileName = "task_init_20160119_*.txt";
        List resultList = new ArrayList();
        FileSupport.findFiles(baseDIR, fileName, resultList);
        if (resultList.size() == 0) {
            System.out.println("No File Fount.");
        } else {
            for (int i = 0; i < resultList.size(); i++) {
                System.out.println(resultList.get(i));//显示查找结果。
            }
        }
    }

    public static void testSearchFiles(){
        String baseDIR = "/Users/xutiantian/filetest";
        String keyword = "task_init_20160119_";

        File folder = new File(baseDIR);// 默认目录
        File[] files = FileSupport.searchFile(folder,keyword);

        if (files.length == 0) {
            System.out.println("No File Fount.");
        } else {
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i]);//显示查找结果。
            }
        }
    }

}
