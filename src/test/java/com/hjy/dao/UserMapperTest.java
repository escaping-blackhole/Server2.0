package com.hjy.dao;

import com.hjy.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {




	@Test
	public void getList() throws Exception {
		System.out.println(userMapper.getList());
	}

	@Autowired
	private UserMapper userMapper;


	@Test
	public void deleteByPrimaryKey() throws Exception {

	}

	@Test
	public void insert() throws Exception {
		User user = new User();
		user.setUsername("312222222");
		user.setPassword("321");
		user.setGmtCreate(new Date());
		System.out.println(userMapper.insert(user));
	}

}