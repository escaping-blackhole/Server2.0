package com.hjy.dao;

import com.hjy.entity.Course;
import com.hjy.vo.CourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(String courseId);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(String courseId);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

	void deleteByCourseIds(List<String> courseIdList);

    List<CourseVo> getListByMajorName(@Param("majorName")String majorName,@Param("courseName")String courseName);

    List<Course> getList();

}