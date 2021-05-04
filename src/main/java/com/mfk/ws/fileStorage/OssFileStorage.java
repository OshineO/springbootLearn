package com.mfk.ws.fileStorage;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.mfk.ws.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Component
public class OssFileStorage extends FileBaseStorage {

    private static Logger logger = LoggerFactory.getLogger(OssFileStorage.class);

    private String endPoint = PropertiesUtils.getPropertiesByKey("OSS.ENDPOINT");
    private String accessKey = PropertiesUtils.getPropertiesByKey("OSS.ACCESS-KEY");
    private String secretKey = PropertiesUtils.getPropertiesByKey("OSS.SECRET-KEY");
    private String bucketName = PropertiesUtils.getPropertiesByKey("OSS.bucketName");

    @Override
    public void processUploadFile(String fileDirectory, String fileName, InputStream inputStream) throws IOException {
        String fileKey = fileDirectory+fileName;
        OSSClient ossClient = new OSSClient(endPoint,accessKey,secretKey);
        ossClient.putObject(bucketName,fileKey,inputStream);
        ossClient.shutdown();

    }

    @Override
    public void process(int type, FileStorageParams fileStorageParams) throws Exception {
        logger.info("endPoint+accessKey+secretKey---"+endPoint+"--"+accessKey+"--"+secretKey);
        OSSClient ossClient = new OSSClient(endPoint, accessKey, secretKey);
        try {
            if(!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
            }
            if(type == TYPE_UPLOAD) {
                processUpload(ossClient, fileStorageParams);
            } else if (type == TYPE_DOWNLOAD){
                processDownload(ossClient,fileStorageParams);
            } else {
                processDelete(ossClient,fileStorageParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            throw e;
        } finally {
            ossClient.shutdown();
        }

    }

    private void processDelete(OSSClient ossClient, FileStorageParams fileStorageParams) {
        ossClient.deleteObject(bucketName,fileStorageParams.getFileKey());
    }

    /**
     * 下载文件
     * @param ossClient
     * @param fileStorageParams
     */
    private void processDownload(OSSClient ossClient, FileStorageParams fileStorageParams) throws IOException {
        HttpServletResponse res = fileStorageParams.getRes();
        res.setHeader("content-type","application/octet-stream");
        res.setContentType("application/octet-stream;charset=UTF-8");
        String outFileNames = URLEncoder.encode(fileStorageParams.getOutfileName(),"UTF8");
        res.setHeader("Content-Disposition","attachment;filename="+outFileNames);
        OSSObject ossObject = ossClient.getObject(bucketName,fileStorageParams.getFileKey());
        OutputStream outputStream = fileStorageParams.getRes().getOutputStream();
        int len =0;
        byte[] buffer = new byte[1024];
        InputStream inputStream = null;

        try {
            inputStream = ossObject.getObjectContent();
            while ((len = inputStream.read(buffer))>0) {
                outputStream.write(buffer,0,len);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
                ossObject.close();
            }
        }

    }

    @Override
    FileStorageInputStream getFileStream(FileStorageParams fileStorageParams) throws Exception {

        OSSClient ossClient = new OSSClient(endPoint,accessKey,secretKey);
        OSSObject ossObject = ossClient.getObject(bucketName,fileStorageParams.getFileKey());
        InputStream inputStream = ossObject.getObjectContent();
        FileStorageInputStream fileStorageInputStream = new FileStorageInputStream();
        fileStorageInputStream.setInputStream(inputStream);
        fileStorageInputStream.setOssObject(ossObject);
        return fileStorageInputStream;

    }

    @Override
    public void closeFileStream(FileStorageInputStream fileInputStream) throws Exception {

        try {
            if (fileInputStream != null) {
                fileInputStream.getInputStream().close();
                fileInputStream.getOssObject().close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }

    }

    @Override
    Boolean isFile(FileStorageParams fileStorageParams) throws Exception {

        OSSClient ossClient = new OSSClient(endPoint,accessKey,secretKey);
        return ossClient.doesObjectExist(bucketName,fileStorageParams.getFileKey());
    }

    /**
     * 处理上传文件
     */
    private void processUpload(OSSClient ossClient, FileStorageParams fileStorageParams) throws IOException{
        ossClient.putObject(bucketName,fileStorageParams.getFileKey(),fileStorageParams.getFile().getInputStream());

    }
}
