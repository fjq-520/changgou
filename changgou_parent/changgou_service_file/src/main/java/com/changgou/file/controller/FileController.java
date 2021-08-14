package com.changgou.file.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.file.pojo.FastDFSFile;
import com.changgou.file.util.FastDFSClient;
import com.netflix.discovery.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.ResultSet;

@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/upload")
    public Result upload( MultipartFile file) {
        if (file==null){
            System.out.println("文件不存在！！！");
            throw new RuntimeException("文件不存在");
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isEmpty(filename)){
            throw new RuntimeException("文件ming不存在");
        }

        try {
            byte[] content = file.getBytes();
            String extName = filename.substring(filename.lastIndexOf(".") + 1);
            FastDFSFile fastDFSFile = new FastDFSFile(filename, content, extName);
            String[] upload = FastDFSClient.upload(fastDFSFile);
            String url =  FastDFSClient.getTrackerUrl()+upload[0]+"/"+upload[1];
            return new Result(true,StatusCode.OK,"文件上传成功！！！",url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false,StatusCode.ERROR,"文件上传失败！@！");

    }
    @PostMapping("/download")
    public Result download(@PathVariable("groupName") String groupName, @PathVariable("remoteFilename") String remoteFilename,String localFileName){
        InputStream download = FastDFSClient.download(groupName, remoteFilename);
        try {
            OutputStream outputStream = new FileOutputStream(localFileName);
            byte[] bytes = new byte[1024];
            int len;
            while ((len=download.read())!=-1){
                outputStream.write(bytes);
            }
            return new Result(true,StatusCode.OK,"文件下载成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false,StatusCode.ERROR,"文件下载失败");
    }
}
