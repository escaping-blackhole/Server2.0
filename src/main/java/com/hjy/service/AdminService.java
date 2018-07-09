package com.hjy.service;

import com.hjy.common.ServerResponse;
import com.hjy.entity.Admin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hjy
 * @create 2018/07/01
 **/
public interface AdminService {

	ServerResponse<Admin> login(String adminname,
								String password);

	ServerResponse register(Admin admin);

	ServerResponse<String> checkValid(String str,String type);

}
