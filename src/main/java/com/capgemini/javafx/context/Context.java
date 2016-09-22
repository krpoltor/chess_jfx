package com.capgemini.javafx.context;

import com.capgemini.javafx.dataprovider.data.UserVO;

public class Context {
	private final static Context instance = new Context();

	public static Context getInstance() {
		return instance;
	}

	private UserVO user = new UserVO();

	public void setUser(UserVO user) {
		this.user = user;
	}

	public UserVO currentUser() {
		return user;
	}
}