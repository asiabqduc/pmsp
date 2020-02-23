package net.paramount.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.paramount.auth.entity.UserAccount;
import net.paramount.exceptions.CorporateAuthException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.service.GenericService;

public interface UserAccountService extends GenericService<UserAccount, Long>, UserDetailsService{
    /**
     * Finds the user with the provided name.
     * 
     * @param username The user name
     * @return The user
     * @throws ObjectNotFoundException If no such user exists.
     */
	UserAccount get(String username) throws ObjectNotFoundException;

	/**
	 * Create a new user with the supplied details.
	 */
	void registerUserAccount(UserAccount user);

	/**
	 * Update the specified user.
	 */
	void updateUser(UserAccount user);

	/**
	 * Remove the user with the given login name from the system.
	 */
	void deleteUser(String username);

	/**
	 * Modify the current user's password. This should change the user's password in the
	 * persistent user repository (database, LDAP etc).
	 *
	 * @param oldPassword current password (for re-authentication if required)
	 * @param newPassword the password to change to
	 */
	void changePassword(String oldPassword, String newPassword);

	/**
	 * Check if a user with the email exists in the system.
	 */
	boolean existsByEmail(String emailAddress);

	/**
	 * Check if a user with the supplied login name exists in the system.
	 */
	boolean userExists(String username);

	int countByLogin(String login);

	UserDetails loadUserByEmail(final String email);
	UserAccount save(UserAccount user);
	UserAccount getUserAccount(String loginId, String password) throws CorporateAuthException;
	UserAccount getUserAccount(String userToken) throws CorporateAuthException;
	UserAccount confirm(String confirmedEmail) throws CorporateAuthException;
	void initializeMasterData() throws CorporateAuthException;

	
}
