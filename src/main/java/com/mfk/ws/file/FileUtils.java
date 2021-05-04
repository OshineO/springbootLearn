package com.mfk.ws.file;

import com.zhuozhengsoft.pageoffice.wordwriter.*;

import java.awt.*;
import java.io.*;

/**
 * 文件操作工具
 */
public class FileUtils {

    /**
     * 读取文件中的sql,将其拼成java字符串
     */
    public static void fileContentToSql(File file) {

        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                tempStr = "\""+tempStr.trim()+" \\n\"+"+"\n";
                sbf.append(tempStr);
            }
            saveAsFileWriter(sbf.toString(), file);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }
    public static void dirFile(String dir) {

        File file = new File(dir);
        File[] files = file.listFiles();
        for (File tempFile : files) {
            if (tempFile.isFile()) {
                fileContentToSql(tempFile);
            } else {
                dirFile(tempFile.getAbsolutePath());
            }
        }
    }

    private static void saveAsFileWriter(String content, File file) {
        FileWriter fwriter = null;
        try {
            File tarFile = new File(file.getParent(),"filecCpy");
            if (!tarFile.exists()) {
                tarFile.mkdirs();
            }

            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(new File(tarFile,file.getName()), false);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void  createDocFile() {
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

    }

    public static void main(String[] args) {
        createDocFile();
      //  dirFile("C:\\Users\\heliyijie_zhouliang\\Desktop\\智能化运维工具取数20191203\\智能化运维工具取数\\PMS");
    }
}
