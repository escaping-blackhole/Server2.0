package com.hjy.service.impl;

import com.hjy.common.Const;
import com.hjy.common.ResponseCode;
import com.hjy.common.ServerResponse;
import com.hjy.dao.AdminMapper;
import com.hjy.dao.UserMapper;
import com.hjy.entity.Admin;
import com.hjy.entity.User;
import com.hjy.service.AdminService;
import com.hjy.util.MD5Util;
import com.hjy.util.SHA256Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hjy
 * @create 2018/07/01
 **/
@Service("AdminService")
public class AdminServiceImpl implements AdminService {


	private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminMapper adminMapper;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ServerResponse login(String username, String password) {

		// 校验关键信息是否为空
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return ServerResponse.createByErrorMessage(ResponseCode.BAD_REQUEST.getDesc());
		}

		String SHA256password = SHA256Util.getSHA256StrJava(password + username);

		Admin admin  = adminMapper.selectLogin(username,SHA256password);
		if(admin == null){
			return ServerResponse.createByErrorMessage("用户名或密码错误");
		}

		admin.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
		return ServerResponse.createBySuccess("登录成功",admin);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse register(Admin admin) {

		ServerResponse validResponse = this.checkValid(admin.getAdminname(), Const.ADMINNAME);
		if(!validResponse.isSuccess()){
			return validResponse;
		}
		//SHA256加密
		String password = SHA256Util.getSHA256StrJava(admin.getPassword() + admin.getAdminname());
		admin.setPassword(password);

		int resultCount = adminMapper.insert(admin);
		if(resultCount == 0){
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}


	@Override
	public ServerResponse<String> checkValid(String str,String type){
		if(StringUtils.isNotBlank(type)){
			//开始校验
			if(Const.ADMINNAME.equals(type)){
				int resultCount = adminMapper.checkAdminName(str);
				if(resultCount > 0 ){
					return ServerResponse.createByErrorMessage("用户名已存在");
				}
			}
		} else {
			//参数错误
			return ServerResponse.createByErrorMessage(ResponseCode.BAD_REQUEST.getDesc());
		}
		return ServerResponse.createBySuccessMessage("用户名不存在");
	}

}
