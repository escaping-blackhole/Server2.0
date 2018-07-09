package com.hjy.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjy.common.Const;
import com.hjy.common.ResponseCode;
import com.hjy.common.ServerResponse;
import com.hjy.controller.backend.UserManageController;
import com.hjy.util.CookieUtil;
import com.hjy.util.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = "/console/*")
public class CustomFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList( "/console/login", "/console/logout", "/console/register")));


    @Autowired
    private RedisOperator redis;

    private Logger logger = LoggerFactory.getLogger(CustomFilter.class);

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //判断允许不拦截的路径
        String path =
                request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (!allowedPath) {
            //Cookie验证
            String key = CookieUtil.getUid(request, Const.JSESSIONID);
            String value = null;
            if (StringUtils.isBlank(key)) {
                //清空缓存  token为空
            } else {
                value = redis.get(key);
            }
            if (StringUtils.isBlank(value)) {

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.setStatus(401);

                ObjectMapper mapper = new ObjectMapper();
                ServerResponse serverResponse = ServerResponse.createByNeedLoginCodeMessage();
                response.getWriter().write(mapper.writeValueAsString(serverResponse));
                return;
            } else if (Integer.parseInt(value) > Const.JUDGEROLE) {
                //判断权限
                ObjectMapper mapper = new ObjectMapper();
                ServerResponse serverResponse = ServerResponse.createByErrorMessage("权限不够");
                response.getWriter().write(mapper.writeValueAsString(serverResponse));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
 
    @Override
    public void destroy() {
 
    }
}
