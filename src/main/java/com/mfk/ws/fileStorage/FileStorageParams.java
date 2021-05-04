package com.mfk.ws.fileStorage;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public class FileStorageParams {
    private String fileName; //保存文件名
    private String filePath; //保存文件路径
    private String fileKey; //保存文件ID

    // sftp
    private MultipartFile file;//上传文件对象
    private HttpServletResponse res;//下载reponse对象
    private String outfileName;// 下载文件名字


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public HttpServletResponse getRes() {
        return res;
    }

    public void setRes(HttpServletResponse res) {
        this.res = res;
    }

    public String getOutfileName() {
        if (outfileName== null) {
            outfileName= fileName;
        }
        return outfileName;
    }

    public void setOutfileName(String outfileName) {
        this.outfileName = outfileName;
    }

    @Override
    public String toString() {
        return "FileStorageParams{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", file=" + file +
                ", res=" + res +
                ", outfileName='" + outfileName + '\'' +
                '}';
    }
}
