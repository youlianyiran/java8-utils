/*
 * File Name: DocumentTest.java
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
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * JDOM操作XML
 * 
 * @author Xu Tiantian
 * @version DDAP V2.0.0, 2014-10-17
 * @see
 * @since DDAP V2.0.0
 */

public class NameSpaceTest
{
    private XMLOutputter out = new XMLOutputter();

    public void createXMLDoc()
    {
        // 创建一个car的元素
        Namespace ns = Namespace.getNamespace("xtt",
                "http://www.w3.org/TR/html4/");
        Element carEl = new Element("car",ns);
        // 创建vin属性，并设置值
        carEl.setAttribute("vin", "123fhg5869705iop90");

        // 创建注释
        carEl.addContent(new Comment("Description of a car"));

        // 创建一个make元素，设置文本内容
        carEl.addContent(new Element("make",ns).setText("Toyota"));

        // 创建一个model元素，添加一个文本元素
        carEl.addContent(new Element("model",ns).setContent(new Text("Celica")));

        // 创建一个year元素，添加文本内容
        carEl.addContent(new Element("year",ns).addContent("1997"));

        // 创建一个color元素，文本内容是green
        carEl.addContent(new Element("color",ns).setText("green"));

        // 创建一个license的元素
        Element licenseEl = new Element("license",ns);
        // 为license元素添加文本内容
        licenseEl.addContent("1ABC234");
        // 创建一个state的属性，值为CA
        licenseEl.setAttribute("state", "CA");
        // 将licenseEl添加到根元素中
        carEl.addContent(licenseEl);

        // 将car元素设置为根元素
        Document doc = new Document(carEl);
        print(doc);
    }

    public void readXMLContent()
    {
        SAXBuilder builder = new SAXBuilder();
        try
        {
            Document doc = builder.build(new File("config/disk.xml"));
            Element rootEl = doc.getRootElement();
            // 获得所有子元素
            List<Element> list = rootEl.getChildren();
            //temp = root.getChild("ReaderEventNotificationData", ns);
            // List<Element> list = rootEl.getChildren("disk");

            for (Element el : list)
            {
                // 获取name属性值
                String name = el.getAttributeValue("name");
                // 获取子元素capacity文本值
                String capacity = el.getChildText("capacity");
                // 获取子元素directories文本值
                String directories = el.getChildText("directories");
                String files = el.getChildText("files");
                System.out.println("磁盘信息:");
                System.out.println("分区盘符:" + name);
                System.out.println("分区容量:" + capacity);
                System.out.println("目录数:" + directories);
                System.out.println("文件数:" + files);
                System.out.println("-----------------------------------");
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

    /**
     * 显示当前节点所有Element的属性信息
     * 
     * @param el
     * @return
     */
    private String getAttrInfo(Element el)
    {
        List<Attribute> attrs = el.getAttributes();
        return getAttrInfo(attrs);
    }

    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param attrs
     * @return
     */
    private String getAttrInfo(List<Attribute> attrs)
    {
        StringBuilder info = new StringBuilder();
        for (Attribute attr : attrs)
        {
            info.append(attr.getName()).append("=").append(attr.getValue()).append(", ");
        }
        if (info.length() > 0)
        {
            return "[" + info.substring(0, info.length() - 2) + "]";
        }
        return "";
    }
    
    private void print(Document doc)
    {
        // 设置XML文件编码格式
        // out.setFormat(Format.getCompactFormat().setEncoding("gb2132"));
        // System.out.println(out.outputString(doc));
        try
        {
            out.output(doc, System.out);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void fail(Object o)
    {
        if (o != null)
        {
            System.out.println(o);
        }
    }

    public static void main(String[] args)
    {
        NameSpaceTest test = new NameSpaceTest();
        test.createXMLDoc();
        
        
    }
}
