package com.mfk.ws.fileStorage;

import com.mfk.ws.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class FileStorageOper {

//    @Value("${file.storage.type}")
    private String storageType = PropertiesUtils.getPropertiesByKey("file.storage.type");

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    private FileBaseStorage fileStorage;

    @Autowired
    OssFileStorage ossFileStorage;
    @Autowired
    SftpFileStorage sftpFileStorage;

    private void intStorage() {
        if (fileStorage == null) {
            fileStorage = "oss".equals(storageType)?ossFileStorage:sftpFileStorage;
        }
    }

    public void uploadFile(String fileDirectory, String fileName, InputStream inputStream) throws Exception{

        intStorage();
        fileStorage.processUploadFile(fileDirectory,fileName,inputStream);
    }

    public void uploadFile(FileStorageParams fileStorageParams) throws Exception{
        intStorage();
        fileStorage.uploadFile(fileStorageParams);
    }
    public void downloadFile(FileStorageParams fileStorageParams) throws Exception{
        intStorage();
        fileStorage.downloadFile(fileStorageParams);
    }

    public void deleteFile(FileStorageParams fileStorageParams) throws Exception{
        intStorage();
        fileStorage.deleteFile(fileStorageParams);
    }

    public FileStorageInputStream getInputStream(FileStorageParams fileStorageParams) throws Exception{
        intStorage();
        return  fileStorage.getFileStream(fileStorageParams);
    }

    public void closeInputStream(FileStorageInputStream fileStorageInputStream) throws Exception{
        intStorage();
        fileStorage.closeFileStream(fileStorageInputStream);
    }

    public Boolean isFile (FileStorageParams fileStorageParams) throws Exception{
        intStorage();
        return fileStorage.isFile(fileStorageParams);
    }



}




