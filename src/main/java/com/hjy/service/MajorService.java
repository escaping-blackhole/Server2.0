package com.hjy.service;

import com.github.pagehelper.PageInfo;
import com.hjy.common.ServerResponse;
import com.hjy.entity.Major;

/**
 * @author hjy
 * @create 2018/06/24
 **/
public interface MajorService {


	ServerResponse insertMajor(Major major);

	ServerResponse deleteMajor(String  schoolIds);

	ServerResponse updateMajor(Major major);

	ServerResponse<PageInfo> getMajorList(int pageNum, int pageSize);

}
