package com.hjy.dao;

import com.hjy.entity.FileType;

public interface FileTypeMapper {
    int deleteByPrimaryKey(Long fileTypeId);

    int insert(FileType record);

    int insertSelective(FileType record);

    FileType selectByPrimaryKey(Long fileTypeId);

    int updateByPrimaryKeySelective(FileType record);

    int updateByPrimaryKey(FileType record);
}