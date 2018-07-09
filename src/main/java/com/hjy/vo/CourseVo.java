package com.hjy.vo;

import java.io.Serializable;

/**
 * @author hjy
 * @create 2018/07/03
 **/
public class CourseVo implements Serializable {

	private String courseId;

	private String courseName;

	private String majorName;


	@Override
	public String toString() {
		return "CourseVo{" +
				"courseId='" + courseId + '\'' +
				", courseName='" + courseName + '\'' +
				", majorName='" + majorName + '\'' +
				'}';
	}

	public CourseVo() {
	}

	public CourseVo(String courseId, String courseName, String majorName) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.majorName = majorName;
	}



	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
}
