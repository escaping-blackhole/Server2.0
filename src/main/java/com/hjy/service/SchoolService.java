package com.hjy.service;

import com.github.pagehelper.PageInfo;
import com.hjy.common.ServerResponse;
import com.hjy.entity.School;

/**
 * @author hjy
 * @create 2018/06/24
 **/
public interface SchoolService {

	ServerResponse insertSchool(School school);

	ServerResponse deleteSchool(String  schoolIds);

	ServerResponse updateSchool(School school);

	ServerResponse<PageInfo> getSchoolList(int pageNum, int pageSize);

}
