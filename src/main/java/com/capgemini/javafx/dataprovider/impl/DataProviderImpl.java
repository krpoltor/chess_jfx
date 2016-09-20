package com.capgemini.javafx.dataprovider.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.capgemini.javafx.dataprovider.DataProvider;
import com.capgemini.javafx.dataprovider.data.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataProviderImpl implements DataProvider {

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	public DataProviderImpl() {
	}

	@Override
	public Collection<UserVO> findUsers(String userId, String userFirstName, String userLastName) throws IOException {
		LOG.debug("Entering findUsers()");

		String url = "http://localhost:8090/user/search";
		String charset = "UTF-8";

		String login = userId;
		String name = userFirstName;
		String surname = userLastName;
		if (login == null || login.isEmpty()) {
			login = "";
		}
		if (name == null || name.isEmpty()) {
			name = "";
		}
		if (surname == null || surname.isEmpty()) {
			surname = "";
		}

		String query = String.format("login=%s&name=%s&surname=%s", URLEncoder.encode(login, charset),
				URLEncoder.encode(name, charset), URLEncoder.encode(surname, charset));

		URLConnection connection = new URL(url + "?" + query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = connection.getInputStream();

		Scanner scanner = new Scanner(response);
		String responseBody = scanner.useDelimiter("\\A").next();
		// System.out.println(responseBody);

		ObjectMapper mapper = new ObjectMapper();
		Collection<UserVO> result = mapper.readValue(responseBody,
				mapper.getTypeFactory().constructCollectionType(Collection.class, UserVO.class));

		LOG.debug("Leaving findUsers()");
		response.close();
		return result;
	}

}