package com.mfk.ws.fileStorage;

import com.mfk.ws.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class SftpFileStorage extends FileBaseStorage {

    private String fileStorageUrl = PropertiesUtils.getPropertiesByKey("file.storage.url");
    private int fileStoragePort = Integer.parseInt(PropertiesUtils.getPropertiesByKey("file.storage.port"));
    private String fileStorageUserName= PropertiesUtils.getPropertiesByKey("file.storage.username");
    private String fileStoragePassword = PropertiesUtils.getPropertiesByKey("file.storage.password");

    private static final String SFTP_URL = "/upload/workorderfile";

    @Override
    void processUploadFile(String fileDirectory, String fileName, InputStream inputStream) throws IOException {

        SftpUtil sftpUtil = new SftpUtil(fileStorageUrl,fileStoragePort,fileStorageUserName,fileStoragePassword);
        sftpUtil.uploadExcelFile(fileDirectory,fileName,inputStream);
    }

    @Override
    void process(int type, FileStorageParams fileStorageParams) throws Exception {
        SftpUtil sftpUtil = new SftpUtil(fileStorageUrl,fileStoragePort,fileStorageUserName,fileStoragePassword);

        try {
//            sftpUtil.getChannetl();
            String filePath = SFTP_URL;

            if(!StringUtils.isEmpty(fileStorageParams.getFilePath())) {
                filePath = fileStorageParams.getFilePath();
            }
            if (type == TYPE_UPLOAD) {
                sftpUtil.uploadExcelFile(filePath,fileStorageParams.getFileName(),fileStorageParams.getFile().getInputStream());
            } else if (type == TYPE_DOWNLOAD) {
                sftpUtil.downloadFile(filePath,fileStorageParams.getFileName(),fileStorageParams.getOutfileName(),fileStorageParams.getRes());
            } else {
                sftpUtil.delFile(filePath,fileStorageParams.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sftpUtil.closeChannel();
        }
    }

    @Override
    FileStorageInputStream getFileStream(FileStorageParams fileStorageParams) throws Exception {
        SftpUtil sftpUtil = new SftpUtil(fileStorageUrl,fileStoragePort,fileStorageUserName,fileStoragePassword);
        InputStream inputStream = sftpUtil.getFileInputStream(fileStorageParams.getFilePath(),fileStorageParams.getFileName());
        FileStorageInputStream fileStorageInputStream = new FileStorageInputStream();
        fileStorageInputStream.setInputStream(inputStream);
        fileStorageInputStream.setSftpUtil(sftpUtil);
        return fileStorageInputStream;
    }

    @Override
    void closeFileStream(FileStorageInputStream fileInputStream) throws Exception {
        try {
            fileInputStream.getInputStream().close();
            fileInputStream.getSftpUtil().closeChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    Boolean isFile(FileStorageParams fileStorageParams) throws Exception {
        SftpUtil sftpUtil= new SftpUtil(fileStorageUrl,fileStoragePort,fileStorageUserName,fileStoragePassword);
        String filePath = fileStorageParams.getFilePath();
        String fileName = fileStorageParams.getFileName();
        boolean fileFlag = sftpUtil.isFile(filePath,fileName);
        return fileFlag;
    }
}
