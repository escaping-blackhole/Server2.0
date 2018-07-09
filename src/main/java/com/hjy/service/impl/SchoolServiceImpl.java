package com.hjy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.hjy.common.ResponseCode;
import com.hjy.common.ServerResponse;
import com.hjy.dao.SchoolMapper;
import com.hjy.entity.School;
import com.hjy.service.SchoolService;
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
@Service("SchoolService")
public class SchoolServiceImpl implements SchoolService {

	private Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);

	@Autowired
	private SchoolMapper schoolMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse insertSchool(School school) {

		int resultCount = schoolMapper.insert(school);
		if(resultCount == 0){
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	//如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。
	public ServerResponse deleteSchool(String schoolIds) {
		List<String> schoolList = Splitter.on(",").splitToList(schoolIds);
		if(CollectionUtils.isEmpty(schoolList)){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.BAD_REQUEST.getCode(),ResponseCode.BAD_REQUEST.getDesc());
		}

		schoolMapper.deleteBySchoolIds(schoolList);
		return ServerResponse.createBySuccessMessage("删除成功");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse updateSchool(School school) {
		int i = schoolMapper.updateByPrimaryKeySelective(school);
		if (i > 0) {
			return ServerResponse.createBySuccessMessage("更新成功");
		} else {
			return ServerResponse.createByErrorMessage("更新失败");
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ServerResponse<PageInfo> getSchoolList(int pageNum, int pageSize) {
		//使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum,pageSize);
		// 获取
		List<School> schoolList = schoolMapper.getList();
		PageInfo<School> pageInfo = new PageInfo<>(schoolList);
		return ServerResponse.createBySuccess(pageInfo);
	}
}
