package com.hjy.service;

import com.github.pagehelper.PageInfo;
import com.hjy.common.ServerResponse;
import com.hjy.entity.Course;

/**
 * @author hjy
 * @create 2018/06/24
 **/
public interface CourseService {

	ServerResponse insertCourse(Course course);

	ServerResponse deleteCourseById(String  courseIds);

	ServerResponse updateCourse(Course course);

	ServerResponse<PageInfo> getCourseList(int pageNum, int pageSize,String majorName,String courseName);


}
