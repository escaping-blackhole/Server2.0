package com.hjy.service;

import com.github.pagehelper.PageInfo;
import com.hjy.common.ServerResponse;
import com.hjy.entity.FileInfo;

/**
 * @author hjy
 * @create 2018/06/24
 **/
public interface FileInfoService {

	ServerResponse insertFileInfo(FileInfo fileInfo, Long userId);

	ServerResponse deleteFileInfoById(Long userId, String fileHash);

	ServerResponse updateFileInfo(FileInfo fileInfo, Long userId);

	ServerResponse<PageInfo> getFileInfoList(int pageNum, int pageSize);

	ServerResponse<PageInfo> getFileInfoListByUserId(int pageNum, int pageSize,Long userId);

}
