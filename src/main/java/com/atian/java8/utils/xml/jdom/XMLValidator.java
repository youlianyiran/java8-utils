/*
 * File Name: XMLValidator.java
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
import java.io.FileReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

/**
 * XML Validator
 * 
 * @author Xu Tiantian
 * @version DDAP V2.0.0, 2014-10-17
 * @see
 * @since DDAP V2.0.0
 */

public class XMLValidator
{
    public void validate(String xml, String schema)
    {
        try
        {
            SAXBuilder builder = new SAXBuilder(true);
            // 指定约束方式为XML schema
            builder.setFeature("http://apache.org/xml/features/validation/schema", true);
            // 导入schema文件
            builder.setProperty(
                    "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
                    schema);
            Document doc = builder.build(new FileReader(xml));
            System.out.println("恭喜您，验证通过！！！");

        }
        catch (Exception e)
        {
            System.out.println("验证失败:" + e);
        }
    }

    public void validateBySchemaFactory(String xml, String schemaPath)
    {
        try
        {
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance("http://www.w3.org/2001/XMLSchema");
            // 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
            File schemaFile = new File(schemaPath);
            // 利用schema工厂，接收验证文档文件对象生成Schema对象
            Schema schema = schemaFactory.newSchema(schemaFile);
            // 通过Schema产生针对于此Schema的验证器，利用schemaPath进行验证
            Validator validator = schema.newValidator();
            // 得到验证的数据源，就是xml
            Source source = new StreamSource(xml);
            // 开始验证，成功输出success!!!，失败输出fail

            validator.validate(source);
            System.out.println("success!!!");
        }
        catch (Exception ex)
        {
            System.out.println("fail" + ex);
        }
    }

    public static void main(String args[])
    {
       // new XMLValidator().validate("config/shiporder.xml", "config/shiporder.xsd");
        new XMLValidator().validateBySchemaFactory("config/shiporder.xml", "config/shiporder.xsd");
    }
}
