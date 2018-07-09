package com.hjy.controller.portal;

import com.hjy.common.Const;
import com.hjy.common.ResponseCode;
import com.hjy.common.ServerResponse;
import com.hjy.entity.User;
import com.hjy.service.UserService;
import com.hjy.util.RedisOperator;
import com.hjy.util.SHA256Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sun.security.provider.SHA;

import java.util.Date;

/**
 *
 * @author hjy
 * @create 2018/05/11
 **/
@RestController
@RequestMapping("/user/")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RedisOperator redis;

	/**
	 * 用户登录
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "login",method = RequestMethod.POST)
	public ServerResponse<User> login(@RequestBody User user,
									  HttpServletRequest request,
									  HttpServletResponse response){

		String username = user.getUsername();
		String password = user.getPassword();

		ServerResponse<User> serverResponse = userService.login(username,password);

		if(serverResponse.isSuccess()){
			String token = SHA256Util.getSHA256StrJava(username + new Date());

			// 登录成功  redis保存加密后的信息    key:token  value:userId
			redis.set(token, JSONObject.toJSONString(serverResponse.getData().getUserId()));

			// 设置请求头：解决关闭浏览器session就被干掉的问题
			Cookie cookie  = new Cookie(Const.JSESSIONID, token);
			cookie.setHttpOnly(true);
			cookie.setPath(request.getContextPath() + "/");
			cookie.setMaxAge(30*60);

			response.addCookie(cookie);

			return serverResponse;
		} else {
			// 登录失败
			return serverResponse;
		}


	}

	/**
	 * 登录注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "logout",method = RequestMethod.POST)
	public ServerResponse<String> logout(HttpSession session){
		String key = session.getId();
		redis.del(key);
		return ServerResponse.createBySuccess("注销成功");
	}

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@PostMapping(value = "signup")
	public ServerResponse register(@RequestBody User user) {

		return userService.register(user);
	}




	/**
	 * 更新
	 * @param user
	 * @return
	 */
	@PutMapping("{id}")
	public ServerResponse updateUser(@PathVariable("id") Long id , User user) {
		if (user == null) {
			return ServerResponse.createByErrorMessage("输入参数有误！");
		}
		user.setUserId(id);
		logger.info("User:" + user);
		return ServerResponse.createByErrorMessage("输入参数有误！");
		//return userService.updateUser(user);
	}

	@PostMapping("check")
	public ServerResponse checkUsername(@RequestBody User user) {
		if (StringUtils.isNotBlank(user.getUsername())) {
			return userService.checkUsername(user.getUsername());
		} else if (StringUtils.isNotBlank(user.getNickname())) {
			return userService.checkNickname(user.getNickname());
		}
		return ServerResponse.createByErrorMessage(ResponseCode.BAD_REQUEST.getDesc());

	}


}
