package com.hjy.dao;

import com.hjy.entity.FileInfo;

import java.util.Date;

public interface FileInfoMapper {
    int deleteByPrimaryKey(String fileHash);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(String fileHash);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);

}