package com.changgou.file.util;

import com.changgou.file.pojo.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FastDFSClient {
    //该文件日志
    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
    static {
        ClassPathResource pathResource = new ClassPathResource("fdfs_client.conf");
        try {
            String filePath = pathResource.getFile().getAbsolutePath();
                ClientGlobal.init(filePath);
            } catch (Exception e) {
                logger.error("文件初始化失败！！！",e.getMessage());
            }
        }

    /**
     * 文件上传
     * @param file
     * @return
     */
    public static String[] upload(FastDFSFile file){
            NameValuePair[] nameValuePair = new NameValuePair[1];
             nameValuePair[0] = new NameValuePair("author",file.getAuthor());
             String[] uploadResults = null;
            StorageClient storageClient = null;
            try {
                 storageClient = getStorageClient();
                 uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), nameValuePair);
            } catch (Exception e) {
                logger.error("Exception when uploadind the file:"+file.getName(),e.getMessage());
            }
            if (uploadResults == null && storageClient!=null) {
                logger.error("upload file fail, error code:" + storageClient.getErrorCode());
            }
            //获取文件组名
            String groupFile = uploadResults[0];
            //获取文件存储路径
            String remoteFileName = uploadResults[1];
            return uploadResults;
        }

        public static InputStream download(String groupName,String remoteFileName){
            try {
                StorageClient storageClient = getStorageClient();
                byte[] bytes = storageClient.download_file(groupName, remoteFileName);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                return byteArrayInputStream;

            } catch (Exception e) {
                logger.error("Exception: Get File from Fast DFS failed",e);
            }
            return null;
        }

    /**
     * 获取文件的绝对路径
     * @return
     * @throws IOException
     */
    public static String getTrackerUrl() throws IOException {
            return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port()+"/";
        }

    /**
     * 获取storage客户端
     * @return
     * @throws IOException
     */
    public static StorageClient getStorageClient() throws IOException {
            TrackerServer trackerServer = getTrackerServer();
            StorageClient storageClient = new StorageClient(trackerServer,null);
            return storageClient;
        }

    /**
     * 获取Tracker
     * @return
     * @throws IOException
     */
    public static TrackerServer getTrackerServer() throws IOException {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            return trackerServer;
        }
    }

