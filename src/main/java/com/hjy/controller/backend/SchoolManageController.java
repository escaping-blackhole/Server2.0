package com.hjy.controller.backend;

import com.hjy.common.ServerResponse;
import com.hjy.entity.School;
import com.hjy.service.SchoolService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

import javax.servlet.http.HttpSession;

/**
 * @author hjy
 * @create 2018/06/24
 **/
@RestController
@RequestMapping("/console/school/")
public class SchoolManageController {

	private Logger logger = LoggerFactory.getLogger(SchoolManageController.class);

	@Autowired
	private SchoolService schoolService;

	/**
	 * 显示学校列表
	 * @param session
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@PostMapping(value = "list")
	public ServerResponse getList(HttpSession session,
								  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
								  @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
		return schoolService.getSchoolList(pageNum,pageSize);
	}

	/**
	 * 删除
	 * @param schoolIds
	 * @return
	 */
	@DeleteMapping("{schoolIds}")
	public ServerResponse deleteSchoolBySchoolId(@PathVariable("schoolIds")String schoolIds) {
		return  schoolService.deleteSchool(schoolIds);
	}

	/**
	 * 更新
	 * @param school
	 * @return
	 */
	@PutMapping("{id}")
	public ServerResponse updateUser(@PathVariable("id") String id , School school) {
		if (school == null) {
			return ServerResponse.createByErrorMessage("输入参数有误！");
		}
		school.setSchoolId(id);
		return schoolService.updateSchool(school);
	}

	/**
	 * 学校注册
	 * @param school
	 * @return
	 */
	@PostMapping
	public ServerResponse register(School school) {

		return schoolService.insertSchool(school);
	}

}
