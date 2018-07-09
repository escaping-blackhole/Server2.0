package com.hjy.service.impl;

import com.github.pagehelper.PageInfo;
import com.hjy.common.ServerResponse;
import com.hjy.dao.FileInfoMapper;
import com.hjy.service.FileInfoService;

import com.hjy.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author hjy
 * @create 2018/06/24
 **/
@Service("FileService")
public class FileInfoServiceImpl implements FileInfoService {

	@Autowired
	FileInfoMapper fileInfoMapper;

	/**
	 * 上传文件
	 * @param fileInfo
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor=Exception.class,propagation = Propagation.REQUIRED)
	public ServerResponse insertFileInfo(FileInfo fileInfo, Long userId) {
		Date date = new Date();
		try {
			//fileInfoMapper.insertFile(userId, fileInfo, date);
		} catch (Exception e) {
			//回滚
			return ServerResponse.createByErrorMessage("上传失败");
		}
		return ServerResponse.createBySuccess("上传成功");
	}





	@Override
	public ServerResponse deleteFileInfoById(Long userId, String fileHash) {
		return null;
	}


	@Override
	public ServerResponse updateFileInfo(FileInfo fileInfo, Long userId) {
		return null;
	}

	@Override
	public ServerResponse<PageInfo> getFileInfoList(int pageNum, int pageSize) {
		return null;
	}

	@Override
	public ServerResponse<PageInfo> getFileInfoListByUserId(int pageNum, int pageSize, Long userId) {
		return null;
	}
}
