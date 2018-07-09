package com.hjy.controller.backend;

import com.hjy.common.ServerResponse;
import com.hjy.entity.FileInfo;
import com.hjy.service.CourseService;
import com.hjy.service.FileInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 *
 * @author hjy
 * @create 2018/06/29
 **/
@RestController
@RequestMapping("console/file/")
public class FileManageController {
	private Logger logger = LoggerFactory.getLogger(FileManageController.class);

	@Autowired
	private FileInfoService fileInfoService;


	@GetMapping(value = "list")
	public ServerResponse getList(
			@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
			@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
		//return  .getCourseList(pageNum,pageSize,majorName);
		return null;
	}


	@PutMapping(value = "{file_hash}")
	public ServerResponse update(@PathVariable("file_hash")String file_hash, FileInfo fileInfo) {
	    return null;
	}

	@DeleteMapping(value = "/{file_hashs}")
	public ServerResponse delete(@PathVariable("file_hashs")String file_hashs){
		return null;
	}

	@PostMapping(value = "/batch")
	public ServerResponse insert(FileInfo fileInfo) {
		return null;
	}



	@GetMapping(value = "filter")
	public ServerResponse search() {
		return null;
	}

}
