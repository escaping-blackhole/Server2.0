package com.hjy.service;

import com.github.pagehelper.PageInfo;
import com.hjy.common.ServerResponse;
import com.hjy.entity.User;

/**
 * @author hjy
 * @create 2018/05/11
 **/
public interface UserService {

	ServerResponse register(User user);

	ServerResponse deleteUser(String userIds);

	ServerResponse updateUser(User user);

	ServerResponse<User> queryUserById(Long id);

	ServerResponse<User> login(String username, String password);

	ServerResponse checkAdminRole(User user);


	ServerResponse<PageInfo> getUserList(int pageNum, int pageSize);

	ServerResponse checkUsername(String username);

	ServerResponse checkNickname(String nickname);

}
