package com.atian.java8.utils.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xutiantian on 16/1/5.
 */
public class FileSupport {

    /**
     * 创建文件
     *
     * @param absoluteFileName
     * @return
     */
    public File createFile(String absoluteFileName) {
        File file = new File(absoluteFileName);

        if (file.exists()) {
            throw new FileException(String.format("创建文件时发生错误,因为此文件名[%s]已存在", absoluteFileName));
        }
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw new FileException(String.format("创建文件[%s]时出错", absoluteFileName), e);
        }
        return file;
    }

    /**
     * 向文件写入字符串
     *
     * @param file
     * @param data
     * @return
     */
    public File appendDataToFile(File file, String data) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            IOUtils.write(data, out);
        } catch (IOException e) {
            throw new FileException("写入文件时错误", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new FileException("写入文件时关闭输出流异常", e);
                }
            }
        }
        return file;
    }

    /**
     * 向文件写入字符串
     *
     * @param file
     * @param data
     */
    public void writeStringToFile(File file, String data) {
        try {
            FileUtils.writeStringToFile(file, data);
        } catch (IOException e) {
            throw new FileException("写入文件时错误", e);
        }
    }

    /**
     * 将字符串列表按行写入文件
     *
     * @param file
     * @param lines
     */
    public void writeLines(File file, List<String> lines) {
        try {
            FileUtils.writeLines(file, lines);
        } catch (IOException e) {
            throw new FileException("写入文件时错误", e);
        }
    }

    /**
     * 按行读文件
     *
     * @param file
     * @return
     */
    public List<String> readLines(File file) {
        try {
            return FileUtils.readLines(file);
        } catch (IOException e) {
            throw new FileException("读文件时错误", e);
        }
    }


    /**
     * 递归查找文件
     *
     * @param baseDirName    查找的文件夹路径
     * @param targetFileName 需要查找的文件名
     * @param fileList       查找到的文件集合
     */
    public static void findFiles(String baseDirName, String targetFileName, List fileList) {

        File baseDir = new File(baseDirName);        // 创建一个File对象
        if (!baseDir.exists() || !baseDir.isDirectory()) {    // 判断目录是否存在
            System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
        }
        String tempName = null;
        //判断目录是否存在
        File tempFile;
        File[] files = baseDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            tempFile = files[i];
            if (tempFile.isDirectory()) {
                findFiles(tempFile.getAbsolutePath(), targetFileName, fileList);
            } else if (tempFile.isFile()) {
                tempName = tempFile.getName();
                if (wildcardMatch(targetFileName, tempName)) {
                    // 匹配成功，将文件名添加到结果集
                    fileList.add(tempFile.getAbsoluteFile());
                }
            }
        }
    }

    /**
     * 通配符匹配
     *
     * @param pattern 通配符模式
     * @param str     待匹配的字符串
     * @return 匹配成功则返回true，否则返回false
     */
    private static boolean wildcardMatch(String pattern, String str) {
        int patternLength = pattern.length();
        int strLength = str.length();
        int strIndex = 0;
        char ch;
        for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
            ch = pattern.charAt(patternIndex);
            if (ch == '*') {
                //通配符星号*表示可以匹配任意多个字符
                while (strIndex < strLength) {
                    if (wildcardMatch(pattern.substring(patternIndex + 1),
                            str.substring(strIndex))) {
                        return true;
                    }
                    strIndex++;
                }
            } else if (ch == '?') {
                //通配符问号?表示匹配任意一个字符
                strIndex++;
                if (strIndex > strLength) {
                    //表示str中已经没有字符匹配?了。
                    return false;
                }
            } else {
                if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                    return false;
                }
                strIndex++;
            }
        }
        return (strIndex == strLength);
    }

    public static File[] searchFile(File folder, final String keyWord) {// 递归查找包含关键字的文件

        File[] subFolders = folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
            @Override
            public boolean accept(File pathname) {// 实现FileFilter类的accept方法
                // 目录或文件包含关键字
                if (pathname.isDirectory()
                        || (pathname.isFile() && pathname.getName().toLowerCase().contains(keyWord.toLowerCase()))){
                    return true;
                }
                return false;
            }
        });

        List<File> result = new ArrayList<File>();// 声明一个集合
        for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
            if (subFolders[i].isFile()) {// 如果是文件则将文件添加到结果列表中
                result.add(subFolders[i]);
            } else {// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                File[] foldResult = searchFile(subFolders[i], keyWord);
                for (int j = 0; j < foldResult.length; j++) {// 循环显示文件
                    result.add(foldResult[j]);// 文件保存到集合中
                }
            }
        }

        File files[] = new File[result.size()];// 声明文件数组，长度为集合的长度
        result.toArray(files);// 集合数组化
        return files;
    }


}
