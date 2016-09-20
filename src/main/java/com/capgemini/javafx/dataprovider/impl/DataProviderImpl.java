package com.capgemini.javafx.dataprovider.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.capgemini.javafx.alerthelper.AlertHelper;
import com.capgemini.javafx.dataprovider.DataProvider;
import com.capgemini.javafx.dataprovider.data.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataProviderImpl implements DataProvider {

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	private final AlertHelper alertHelper = AlertHelper.INSTANCE;

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
		if (login == null) {
			login = "";
		} else if (login.isEmpty()) {
			login = "*";
		}
		if (name == null) {
			name = "";
		} else if (name.isEmpty()) {
			name = "*";
		}
		if (surname == null) {
			surname = "";
		} else if (surname.isEmpty()) {
			surname = "*";
		}

		String query = String.format("login=%s&name=%s&surname=%s", URLEncoder.encode(login, charset),
				URLEncoder.encode(name, charset), URLEncoder.encode(surname, charset));

		URLConnection connection = new URL(url + "?" + query).openConnection();
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream response = connection.getInputStream();

		Scanner scanner = new Scanner(response);
		String responseBody = scanner.useDelimiter("\\A").next();
		scanner.close();
		// System.out.println(responseBody);

		ObjectMapper mapper = new ObjectMapper();
		Collection<UserVO> result = mapper.readValue(responseBody,
				mapper.getTypeFactory().constructCollectionType(Collection.class, UserVO.class));

		response.close();
		LOG.debug("Leaving findUsers()");
		return result;
	}

	@Override
	public Boolean deleteUser(UserVO deleteUser) {
		LOG.debug("Entering deleteUser()");

		Boolean result = false;
		try {
			URL url = new URL("http://localhost:8090/user/" + deleteUser.getId());
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpCon.setRequestMethod("DELETE");
			httpCon.connect();

			httpCon.disconnect();
			if (httpCon.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + httpCon.getResponseCode());
			} else {
				result = true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		LOG.debug("Leaving deleteUser()");
		return result;
	}

	@Override
	public void updateUser(UserVO updatedUser) {
		LOG.debug("Entering updateUser()");

		try {
			URL url = new URL("http://localhost:8090/user/");
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

			httpCon.setRequestMethod("PUT");
			httpCon.setDoInput(true);
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Content-Type", "application/json");

			OutputStream out = httpCon.getOutputStream();

			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(updatedUser);

			out.write(jsonInString.getBytes());
			out.close();

			httpCon.getInputStream();

			httpCon.disconnect();
			if (httpCon.getResponseCode() != 200) {

				alertHelper.showErrorAlert("Error!", "Ooops, there was an error!",
						"Error code: " + httpCon.getResponseCode());

				// throw new RuntimeException("Failed : HTTP error code : " +
				// httpCon.getResponseCode());

			} else {
				alertHelper.showInformationAlert("Information Dialog", "Look, an Information Dialog", "Done!");
			}
			// System.out.println(responseBody);
			// response.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LOG.debug("Leaving updateUser()");
	}

}
