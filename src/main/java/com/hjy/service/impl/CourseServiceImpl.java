package com.hjy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.hjy.common.ResponseCode;
import com.hjy.common.ServerResponse;
import com.hjy.dao.CourseMapper;
import com.hjy.entity.Course;
import com.hjy.entity.Major;
import com.hjy.service.CourseService;
import com.hjy.vo.CourseVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author hjy
 * @create 2018/06/24
 **/
@Service("CourseService")
public class CourseServiceImpl implements CourseService {


	private Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

	@Autowired
	private CourseMapper courseMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse insertCourse(Course course) {
		int resultCount = courseMapper.insert(course);
		if(resultCount == 0){
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse deleteCourseById(String courseIds) {

		List<String> courseIdList = Splitter.on(",").splitToList(courseIds);

		if(CollectionUtils.isEmpty(courseIdList)){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.BAD_REQUEST.getCode(),ResponseCode.BAD_REQUEST.getDesc());
		}

		courseMapper.deleteByCourseIds(courseIdList);
		return ServerResponse.createBySuccessMessage("删除成功");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse updateCourse(Course course) {

		String courseName = course.getCourseName();

		if (StringUtils.isBlank(courseName)) {
			return ServerResponse.createByErrorMessage("输入参数有误！");
		}

		int i = courseMapper.updateByPrimaryKeySelective(course);
		if (i > 0) {
			return ServerResponse.createBySuccessMessage("更新成功");
		} else {
			return ServerResponse.createByErrorMessage("更新失败");
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ServerResponse<PageInfo> getCourseList(int pageNum, int pageSize,String majorName,String courseName) {
		//使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum,pageSize);
		// 获取
		List<CourseVo> courseList;
		if(StringUtils.isNotBlank(courseName)){
			courseName = new StringBuilder().append("%").append(courseName).append("%").toString();
		}
		courseList = courseMapper.getListByMajorName(majorName,courseName);

		PageInfo<CourseVo> pageInfo = new PageInfo<>(courseList);
		return ServerResponse.createBySuccess(pageInfo);
	}
}
