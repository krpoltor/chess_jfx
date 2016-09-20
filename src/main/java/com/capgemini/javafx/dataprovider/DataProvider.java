package com.capgemini.javafx.dataprovider;

import java.io.IOException;
import java.util.Collection;

import com.capgemini.javafx.dataprovider.data.UserVO;
import com.capgemini.javafx.dataprovider.impl.DataProviderImpl;

public interface DataProvider {
	
	/**
	 * Instance of this interface.
	 */
	DataProvider INSTANCE = new DataProviderImpl();

	/**
	 * Finds users with their userId or userFirstName or userLastName containing specified string and/or given
	 * sex.
	 *
	 * @param name
	 *            string contained in name
	 * @return collection of persons matching the given criteria
	 * @throws IOException 
	 */
	Collection<UserVO> findUsers(String userId, String userFirstName, String userLastName) throws IOException;

	Boolean deleteUser(UserVO deleteUser) ;

	void updateUser(UserVO updatedUser) ;

}
