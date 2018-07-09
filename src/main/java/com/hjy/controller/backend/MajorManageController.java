package com.hjy.controller.backend;

import com.hjy.common.ServerResponse;
import com.hjy.entity.Major;
import com.hjy.service.MajorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author hjy
 * @create 2018/06/24
 **/

@RestController
@RequestMapping("/console/major/")
public class MajorManageController {

	private Logger logger = LoggerFactory.getLogger(MajorManageController.class);

	@Autowired
	private MajorService majorService;

	@GetMapping(value = "list")
	public ServerResponse getList(HttpSession session,
								  @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
								  @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){

		return majorService.getMajorList(pageNum,pageSize);
	}

	@DeleteMapping("{majorIds}")
	public ServerResponse deleteMajorByMajorId(@PathVariable("majorIds")String majorIds) {
		return  majorService.deleteMajor(majorIds);
	}

	@PutMapping("{id}")
	public ServerResponse updateMajor(@PathVariable("id") String id ,
									  @RequestBody Major major) {
		if (major == null) {
			return ServerResponse.createByErrorMessage("输入参数有误！");
		}
		major.setMajorId(id);
		return majorService.updateMajor(major);
	}

	@PostMapping
	public ServerResponse insertMajor(Major major) {
		return majorService.insertMajor(major);
	}

}
