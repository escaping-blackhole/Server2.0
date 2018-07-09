package com.hjy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.hjy.common.ResponseCode;
import com.hjy.common.ServerResponse;
import com.hjy.dao.MajorMapper;
import com.hjy.entity.Major;
import com.hjy.service.MajorService;
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
@Service("MajorService")
public class MajorServiceImpl implements MajorService {

	private Logger logger = LoggerFactory.getLogger(MajorServiceImpl.class);

	@Autowired
	private MajorMapper majorMapper;


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse insertMajor(Major major) {
		int resultCount = majorMapper.insert(major);
		if(resultCount == 0){
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse deleteMajor(String majorIds) {
		List<String> majorList = Splitter.on(",").splitToList(majorIds);
		if(CollectionUtils.isEmpty(majorList)){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.BAD_REQUEST.getCode(),ResponseCode.BAD_REQUEST.getDesc());
		}
		majorMapper.deleteByMajorIds(majorList);
		return ServerResponse.createBySuccessMessage("删除成功");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse updateMajor(Major major) {
		int i = majorMapper.updateByPrimaryKeySelective(major);
		if (i > 0) {
			return ServerResponse.createBySuccessMessage("更新成功");
		} else {
			return ServerResponse.createByErrorMessage("更新失败");
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ServerResponse<PageInfo> getMajorList(int pageNum, int pageSize) {
		//使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum,pageSize);
		// 获取

		List<Major> majorList = majorMapper.getList();

		PageInfo<Major> pageInfo = new PageInfo<>(majorList);
		return ServerResponse.createBySuccess(pageInfo);
	}
}
