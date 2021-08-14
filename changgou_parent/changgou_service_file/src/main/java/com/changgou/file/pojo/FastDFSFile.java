package com.changgou.file.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FastDFSFile {
    //文件的名字
    private String name;
    //文件的内容
    private byte[] content;
    //文件的扩展名
    private String ext;
    //文件MD5
    private String md5;
    //文件的作者
    private String author;

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public FastDFSFile(String name, byte[] content, String ext, String author) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.author = author;
    }
}
