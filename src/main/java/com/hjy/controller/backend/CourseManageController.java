package com.hjy.controller.backend;

import com.hjy.common.ServerResponse;
import com.hjy.entity.Course;
import com.hjy.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;



/**
 * @author hjy
 * @create 2018/06/24
 **/
@RestController
@RequestMapping("/console/course/")
public class CourseManageController {

	private Logger logger = LoggerFactory.getLogger(CourseManageController.class);

	@Autowired
	private CourseService courseService;


	@GetMapping(value = "list")
	public ServerResponse getList(
								  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
								  @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
								  @RequestParam(value = "majorName",defaultValue = "") String majorName,
								  @RequestParam(value = "courseName",defaultValue = "") String courseName){
		return courseService.getCourseList(pageNum,pageSize,majorName,courseName);

	}
	@DeleteMapping("/{courseIds}")
	public ServerResponse deleteCourseByCourseId(@PathVariable("courseIds")String courseIds) {
		return  courseService.deleteCourseById(courseIds);
	}


	@PutMapping("{courseId}")
	public ServerResponse updateCourse(@PathVariable("courseId")String courseId,
									   @RequestBody Course course) {
		course.setCourseId(courseId);
		return courseService.updateCourse(course);
	}

	@PostMapping
	public ServerResponse register(@RequestBody Course course) {

		return  courseService.insertCourse(course);
	}

}
