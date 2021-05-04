package com.mfk.ws.fileStorage;

import java.io.IOException;
import java.io.InputStream;

public abstract class FileBaseStorage {
    int TYPE_UPLOAD = 1;
    int TYPE_DOWNLOAD = 2;
    int TYPE_DELETE =3;

    public void uploadFile(FileStorageParams fileStorageParams) throws Exception{
        process(TYPE_UPLOAD,fileStorageParams);

    }
    public void downloadFile(FileStorageParams fileStorageParams) throws Exception{
        process(TYPE_DOWNLOAD,fileStorageParams);

    }
    public void deleteFile(FileStorageParams fileStorageParams) throws Exception{
        process(TYPE_DELETE,fileStorageParams);

    }
    abstract void processUploadFile(String fileDirectory, String fileName, InputStream inputStream) throws IOException;

    abstract void process(int type, FileStorageParams fileStorageParams) throws Exception;

    abstract FileStorageInputStream getFileStream(FileStorageParams fileStorageParams) throws Exception;
    abstract void closeFileStream(FileStorageInputStream fileInputStream) throws Exception;
    abstract Boolean isFile(FileStorageParams fileStorageParams) throws Exception;

}
