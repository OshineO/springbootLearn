package com.mfk.ws.controller;

import com.zhuozhengsoft.pageoffice.DocumentVersion;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
@Controller
public class WordController {

    @GetMapping("/createDocFile")
    public void  createDocFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WordDocument doc = new WordDocument();

        //设置内容标题
        //创建DataRegion对象，PO_title为自动添加的书签名称,书签名称需以“PO_”为前缀，切书签名称不能重复
        //三个参数分别为要新插入书签的名称、新书签的插入位置、相关联的书签名称（“[home]”代表Word文档的页首）
        DataRegion title = doc.createDataRegion("PO_title", DataRegionInsertType.After, "[home]");
        //给DataRegion对象赋值
        title.setValue("JAVA中编程实例\n");
        //设置字体：粗细、大小、字体名称、是否是斜体
        title.getFont().setBold(true);
        title.getFont().setSize(20);
        title.getFont().setName("黑体");
        title.getFont().setItalic(false);
        //定义段落对象
        ParagraphFormat titlePara = title.getParagraphFormat();
        //设置段落对齐方式
        titlePara.setAlignment(WdParagraphAlignment.wdAlignParagraphCenter);
        //设置段落行间距
        titlePara.setLineSpacingRule(WdLineSpacing.wdLineSpaceMultiple);

        //设置内容
        //第一段
        //创建DataRegion对象，PO_body为自动添加的书签名称
        DataRegion body = doc.createDataRegion("PO_body", DataRegionInsertType.After, "PO_title");
        //设置字体：粗细、是否是斜体、大小、字体名称、字体颜色
        body.getFont().setBold(true);
        body.getFont().setItalic(true);
        body.getFont().setSize(10);
        //设置中文字体名称
        body.getFont().setName("楷体");
        //设置英文字体名称
        body.getFont().setNameAscii("Times New Roman");
        body.getFont().setColor(Color.red);
        //给DataRegion对象赋值
        body.setValue("首先，我向大家介绍一下套接字的概念。\n");
        //创建ParagraphFormat对象
        ParagraphFormat bodyPara = body.getParagraphFormat();
        //设置段落的行间距、对齐方式、首行缩进
        bodyPara.setLineSpacingRule(WdLineSpacing.wdLineSpaceAtLeast);
        bodyPara.setAlignment(WdParagraphAlignment.wdAlignParagraphLeft);
        bodyPara.setFirstLineIndent(21);
        Writer writer = response.getWriter();
        //创建PageOfficeCtrl对象打开文件
        PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
        poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
        //获取数据对象
        poCtrl1.setWriter(doc);
        // 打开文档
//        poCtrl1.webCreateNew("/template.doc", DocumentVersion.Word2007);
        poCtrl1.webOpen("doc/template.doc", OpenModeType.docReadOnly, "Tom");


    }
}
