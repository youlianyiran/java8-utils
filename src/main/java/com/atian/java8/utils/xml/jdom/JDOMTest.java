/*
 * File Name: JDOMTest.java
 * Copyright: Copyright 2012-2014 CETC52 CETITI All Rights Reserved.
 * Description: 
 * Author: Xu Tiantian
 * Create Date: 2014-10-17

 * Modifier: Xu Tiantian
 * Modify Date: 2014-10-17
 * Bugzilla Id: 
 * Modify Content: 
 */
package com.atian.java8.utils.xml.jdom;

import java.io.File;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.ProcessingInstruction;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author Xu Tiantian
 * @version DDAP V2.0.0, 2014-10-17
 * @see
 * @since DDAP V2.0.0
 */

public class JDOMTest
{
    public void creatXML()
    {
        // 1.先创建一个空的文档对象
        Document doc = new Document();
        // 2.得到处理指令对象
        ProcessingInstruction pi = new ProcessingInstruction("xml-stylesheet",
                "type='text/xsl' href='student.xsl'");
        // 3.添加到文档对象中
        doc.addContent(pi);
        // 4.设置根节点
        Element root = new Element("students");
        doc.setRootElement(root);
        // 5.设置文本节点
        Element eltStu1 = new Element("student");
        Element eltName1 = new Element("name");
        Element eltAge1 = new Element("age");
        eltName1.setText("张三");
        eltAge1.setText("18");
        // 6.将文本节点添加到元素节点<student>下
        eltStu1.addContent(eltName1);
        eltStu1.addContent(eltAge1);
        eltStu1.setAttribute("sn", "01");
        // 7.将<student>添加到<students>下
        root.addContent(eltStu1);
        // 8.构造输出流
        XMLOutputter xmlOut = new XMLOutputter();
        // 9.设置XML输出的外观
        Format fmt = Format.getPrettyFormat();
        fmt.setIndent("    ");// 四个空格
        fmt.setEncoding("gb2312");
        // 10.设置格式与输出相关
        xmlOut.setFormat(fmt);
        try
        {
            xmlOut.output(doc, System.out);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void xmlOperate()
    {
        // 1.构件SAXBuilder
        SAXBuilder saxBuilder = new SAXBuilder();
        try
        {
            // 2.返回DOC对象
            Document doc = saxBuilder.build(new File("config/students.xml"));
            // 3.构建元素对象
            Element eltStu = new Element("student");
            Element eltName = new Element("name");
            Element eltAge = new Element("age");
            // 4.设置文本节点值
            eltName.setText("王五");
            eltAge.setText("11");
            // 5.把名称跟年龄添加到<student>下
            eltStu.addContent(eltName);
            eltStu.addContent(eltAge);
            eltStu.setAttribute("sn", "03");
            // 6.构建根元素
            Element root = doc.getRootElement();
            // 7.把<student>添加到根元素下
            root.addContent(eltStu);
            // 删除功能
            root.removeChild("student");
            // 修改功能
            root.getChild("student").getChild("age").setText("33");
            // 输出
            XMLOutputter xmlOut = new XMLOutputter();
            Format fmt = Format.getPrettyFormat();
            fmt.setIndent("    ");// 四个空格
            fmt.setEncoding("gb2312");
            // 设置格式与输出相关
            xmlOut.setFormat(fmt);
            try
            {
                xmlOut.output(doc, System.out);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (JDOMException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        JDOMTest test = new JDOMTest();
        test.xmlOperate();
    }
}
