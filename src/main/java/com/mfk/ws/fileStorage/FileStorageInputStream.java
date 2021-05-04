package com.mfk.ws.fileStorage;

import com.aliyun.oss.model.OSSObject;

import java.io.InputStream;

public class FileStorageInputStream {
    private InputStream inputStream;
    private OSSObject ossObject;
    SftpUtil sftpUtil;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OSSObject getOssObject() {
        return ossObject;
    }

    public void setOssObject(OSSObject ossObject) {
        this.ossObject = ossObject;
    }

    public SftpUtil getSftpUtil() {
        return sftpUtil;
    }

    public void setSftpUtil(SftpUtil sftpUtil) {
        this.sftpUtil = sftpUtil;
    }
}
