package com.hjy.service.impl;

import com.google.common.base.Splitter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.common.Const;
import com.hjy.common.ResponseCode;
import com.hjy.common.ServerResponse;
import com.hjy.dao.UserMapper;
import com.hjy.entity.User;
import com.hjy.service.UserService;
import com.hjy.util.CookieUtil;
import com.hjy.util.MD5Util;
import com.hjy.util.SHA256Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author hjy
 * @create 2018/05/11
 **/
@Service("UserService")
public class UserServiceImpl implements UserService {


	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse register(User user) {
		ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
		if(!validResponse.isSuccess()){
			return validResponse;
		}

		user.setGmtCreate(new Date());

		//SHA256加密
		String password = SHA256Util.getSHA256StrJava(user.getPassword() + user.getUsername());
		user.setPassword(password);

		int resultCount = userMapper.insert(user);
		if(resultCount == 0){
			return ServerResponse.createByErrorMessage("注册失败");
		}
		return ServerResponse.createBySuccessMessage("注册成功");
	}

	public ServerResponse<String> checkValid(String str,String type){
		if(StringUtils.isNotBlank(type)){
			//开始校验
			if(Const.USERNAME.equals(type)){
				int resultCount = userMapper.checkUsername(str);
				if(resultCount > 0 ){
					return ServerResponse.createByErrorMessage("用户名已存在");
				}
			} else if (Const.NICKNAME.equals(type)) {
				int resultCount = userMapper.checkNickname(str);
				if(resultCount > 0 ){
					return ServerResponse.createByErrorMessage("花名已存在");
				}
			}
		}else{
			return ServerResponse.createByErrorMessage("参数错误");
		}
		return ServerResponse.createBySuccessMessage("校验成功");
	}




	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse deleteUser(String userIds) {
		List<String> userList = Splitter.on(",").splitToList(userIds);
		if(CollectionUtils.isEmpty(userList)){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.BAD_REQUEST.getCode(),ResponseCode.BAD_REQUEST.getDesc());
		}
		userMapper.deleteByUserIds(userList);
		return ServerResponse.createBySuccessMessage("删除成功");

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ServerResponse updateUser(User user) {
		int i = userMapper.updateByPrimaryKeySelective(user);
		if (i > 0) {
			return ServerResponse.createBySuccessMessage("更新成功");
		} else {
			return ServerResponse.createByErrorMessage("更新失败");
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ServerResponse<User> queryUserById(Long id) {

		User user = userMapper.selectByPrimaryKey(id);
		if (user.getUserId() != null) {
			return ServerResponse.createBySuccess(user);
		} else {
			return ServerResponse.createByErrorMessage("查询失败");
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ServerResponse<User> login(String username, String password) {

		int resultCount = userMapper.checkUsername(username);
		if(resultCount == 0 ){
			return ServerResponse.createByErrorMessage("用户名不存在");
		}

		password = SHA256Util.getSHA256StrJava(password+username);
		User user  = userMapper.selectLogin(username,password);
		if(user == null){
			return ServerResponse.createByErrorMessage("密码错误");
		}

		user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
		return ServerResponse.createBySuccess("登录成功",user);
	}

	/**
	 * 校验是否是管理员
	 * @param user
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS) //支持当前事务，如果当前没有事务，就以非事务方式执行。
	public ServerResponse checkAdminRole(User user) {
		if(user != null){
			return ServerResponse.createBySuccess();
		}
		return ServerResponse.createByError();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ServerResponse<PageInfo> getUserList(int pageNum, int pageSize) {
		//使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum,pageSize);
		// 获取
		List<User> userList = userMapper.getList();
		PageInfo<User> pageInfo = new PageInfo<User>(userList);
		return ServerResponse.createBySuccess(pageInfo);
	}

	@Override
	public ServerResponse checkUsername(String username) {
		return this.checkValid(username, Const.USERNAME);
	}

	@Override
	public ServerResponse checkNickname(String nickname) {
		return this.checkValid(nickname, Const.NICKNAME);
	}




}
