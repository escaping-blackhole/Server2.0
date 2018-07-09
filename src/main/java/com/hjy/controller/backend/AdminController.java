package com.hjy.controller.backend;

import com.alibaba.fastjson.JSONObject;
import com.hjy.common.Const;
import com.hjy.common.ResponseCode;
import com.hjy.common.ServerResponse;
import com.hjy.entity.Admin;
import com.hjy.service.AdminService;
import com.hjy.util.CookieUtil;
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
import java.util.Date;

/**
 * @author hjy
 * @create 2018/07/01
 **/
@RestController
@RequestMapping("/console/")
public class AdminController {

	private Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@Autowired
	private RedisOperator redis;

	@PostMapping(value = "login")
	public ServerResponse<Admin> login(@RequestBody Admin admin,
									   HttpServletRequest request,
									   HttpServletResponse response) {
		String adminname = admin.getAdminname();
		String password = admin.getPassword();

		ServerResponse<Admin> serverResponse = adminService.login(adminname,password);

		if(serverResponse.isSuccess()){
			String token = SHA256Util.getSHA256StrJava(adminname + new Date());

			// 登录成功  redis保存加密后的信息
			redis.set(token, JSONObject.toJSONString(serverResponse.getData().getAdminId()));

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


	@PostMapping( value = "logout")
	public ServerResponse logout(HttpServletRequest  request,
								 HttpServletResponse response) {
		//Cookie验证
		String key = CookieUtil.getUid(request, Const.JSESSIONID);
		String value =redis.get(key);
		if (StringUtils.isBlank(value)) {
			response.setStatus(401);
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
		}
		redis.del(key);
		return ServerResponse.createBySuccessMessage("注销成功");
	}

	@PostMapping(value = "register")
	public ServerResponse register(@RequestBody Admin admin) {
		String adminname = admin.getAdminname();
		String password = admin.getPassword();

		// 校验关键信息是否为空
		if (StringUtils.isBlank(adminname) || StringUtils.isBlank(password)) {
			return ServerResponse.createByErrorMessage(ResponseCode.BAD_REQUEST.getDesc());
		}

		return adminService.register(admin);
	}

	@PostMapping(value = "check")
	public ServerResponse check(@RequestBody Admin admin) {
		String adminname = admin.getAdminname();
		return  adminService.checkValid(adminname, Const.ADMINNAME);
	}


}
