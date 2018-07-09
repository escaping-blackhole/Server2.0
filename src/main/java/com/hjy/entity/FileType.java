package com.hjy.entity;

import java.io.Serializable;

public class FileType implements Serializable {
    private Long fileTypeId;

    private String fileType;

    @Override
    public String toString() {
        return "FileType{" +
                "fileTypeId=" + fileTypeId +
                ", fileType='" + fileType + '\'' +
                '}';
    }

    public FileType(Long fileTypeId, String fileType) {
        this.fileTypeId = fileTypeId;
        this.fileType = fileType;
    }

    public FileType() {
        super();
    }

    public Long getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(Long fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }
}