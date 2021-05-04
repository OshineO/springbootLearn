package com.mfk.ws.fileStorage;

import com.jcraft.jsch.*;
import jdk.internal.util.xml.impl.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;


public class SftpUtil {

    Logger logger = LoggerFactory.getLogger(SftpUtil.class);
    private String host;
    private Integer port;
    private String username;
    private String password;
    private Session session;
    private ChannelSftp sftp;

    public SftpUtil (String host,Integer port, String username, String password) {
        this.host=host;
        this.password=password;
        this.port=port;
        this.username=username;

    }
    /**
     * 建立通道
     */
    public ChannelSftp getChannetl () {
        JSch jSch = new JSch();

        try {
            session = jSch.getSession(username,host,port);
            session.setPassword(password);
            Properties configTemp = new Properties();
            configTemp.put("StrictHostKeyChecking","no");
            session.setConfig(configTemp);
            // 建立连接
            session.connect();
            // 打开 SFTP通道
            Channel channel = session.openChannel("sftp");
            // 建立SFTP通道连接
            channel.connect();

            sftp = (ChannelSftp)channel;
            // 设置文件名编码
            setFileName();
            logger.info("success to connect to host="+host+","+"username="+username);
        } catch (JSchException e) {
            logger.error(e.getMessage(),e);
        }

        return sftp;
    }
    /**
     * 设置文件名编码（解决中文名上传后乱码）
     */
    private void setFileName() {
        try {
            Class cl = ChannelSftp.class;
            Field f = cl.getDeclaredField("servet_version");
            f.setAccessible(true);
            f.set(sftp,2);
            sftp.setFilenameEncoding("GBK");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 断开连接
     */
    public void closeChannel() throws IOException {
        if (sftp != null) {
            sftp.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
        logger.info("disconnect SFTP successfully");
    }

    /**
     * 上传单个文件
     */
    public boolean uploadFile (String filePath, String fileName, String content) {
        boolean result = false;
        InputStream ins = null;

        try {
            ins = new ByteArrayInputStream(content.getBytes());
            ChannelSftp sftp = getChannetl();
            createDir(filePath, sftp);
            sftp.put(ins, fileName);
            result = true;
        } catch (SftpException e) {
            logger.error("文件上传错误", e);
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    closeChannel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;

        }
    }
    /**
     * 上传单个文件
     */
    public boolean uploadExcelFile (String filePath, String fileName, InputStream ins) {
        boolean result = false;
        //InputStream ins = null;

        try {
            // ins = new ByteArrayInputStream(content.getBytes());
            ChannelSftp sftp = getChannetl();
            createDir (filePath, sftp);
            sftp.put(ins,fileName);
            result = true;
        } catch (SftpException e) {
            logger.error("文件上传错误",e);
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    closeChannel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 读取sftp上的文件
     */
    public List<String> readFile(String fileName){
        List<String> list = new ArrayList<>();
        ChannelSftp sftp = getChannetl();
        InputStream inputStream = null;

        try {
            inputStream = sftp.get(fileName);
            BufferedReader  reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
        } catch (SftpException e) {
            logger.error(e.getMessage(),e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
        return list;
    }
    /**
     * 判断目标路径是否存在，不存在则创建
     */
    public void createDir (String createpath, ChannelSftp sftp) throws SftpException{


        if(isDirExist(createpath)){
            sftp.cd(createpath);
            return;
        }
        String pathArray[] = createpath.split("/");
        StringBuffer filePath = new StringBuffer("/");
        for (String path: pathArray) {
            if ("".equals(path)) {
                continue;
            }
            filePath.append(path+"/");
            if (isDirExist(filePath.toString())) {
                sftp.cd(filePath.toString());
            } else {
                //建立目录
                sftp.mkdir(filePath.toString());
                // 进入设置为当前目录
                sftp.cd(filePath.toString());
            }
        }
    }
    /**
     * 判断目录是否存在
     */
    public boolean isDirExist(String directory) {

        boolean isDirExistFlag = false;

        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory); // 检索文件或目录的文件属性
            isDirExistFlag = true;

            return sftpATTRS.isDir();
        } catch (Exception e) {
            if ("no such file".equals(e.getMessage().toLowerCase())) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }
    public static void setResHeader (HttpServletResponse res, String outFileName) throws IOException {
        res.setHeader("content-type","application/octet-stream");
        res.setContentType("application/octet-stream;charset=UTF-8");
        String outFileNames = URLEncoder.encode(outFileName,"UTF8");
        res.setHeader("Content-Disposition","attachment;filename="+outFileNames);
    }
    public InputStream getFileInputStream (String directory, String saveFileName) throws Exception{
        this.getChannetl();
        sftp.cd(directory);
        InputStream ins = sftp.get(saveFileName);
        return ins;

    }
    /**
     * 下载单个文件
     */
    public boolean downloadFile (String directory, String saveFileName,String outFileName,HttpServletResponse res) throws IOException {

        SftpUtil.setResHeader(res,outFileName);
        boolean result = false;
        InputStream ins = null;
        OutputStream outputStream = null;

        try {
            ChannelSftp sftp = getChannetl();
            sftp.cd(directory);
            ins = sftp.get(saveFileName);
            outputStream = res.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];

            while ((len= ins.read(buffer))>0) {
                outputStream.write(buffer,0,len);
            }
            result = true;
        } catch (SftpException e) {
            logger.error(e.getMessage(),e);
        } finally {
            if (ins != null) {
                ins.close();
            }
            closeChannel();
        }

        return result;

    }
    /**
     * 删除文件
     */

    public boolean delFile(String directory,String fileName) {
        boolean result = false;

        try {
            ChannelSftp sftp = getChannetl();
            sftp.cd(directory);
            sftp.rm(fileName);
            result = true;
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 判断SFTP文件是否存在
     */
    public boolean isFile (String directory, String name) {

        ChannelSftp sftp = getChannetl();
        boolean isFileFlag = false;

        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isFileFlag = sftpATTRS.isDir();

            // 文件夹存在
            if (isFileFlag) {
                sftp.cd(directory);

                Vector vector = sftp.ls(name);

                if (vector == null || vector.size() == 0) {
                    isFileFlag = false;
                } else {
                    isFileFlag = true;
                }

            }
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            try {
                closeChannel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isFileFlag;
    }

}
