package com.hjy.dao;

import com.hjy.entity.School;

import java.util.List;

public interface SchoolMapper {
    int deleteByPrimaryKey(String schoolId);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(String schoolId);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);

	void deleteBySchoolIds(List<String> schoolList);

    List<School> getList();

}