package app.login;

import app.entities.User;
import app.utils.UserUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class LoginDelegate
{
	@Autowired
	private UserUtils userUtils;

	HashGenerationHelper hashGenerationHelper;

	public LoginDelegate() {
		hashGenerationHelper = new HashGenerationHelper();
	}

	public LoginDelegate(HashGenerationHelper hashGenerationHelper) {
		this.hashGenerationHelper = hashGenerationHelper;
	}

	public boolean checkUsernameExists(String username) {
		return  userUtils.isUserNameAlreadyInUse(username);
	}

	public boolean isValidUser(String username, String password) throws SQLException
	{
		String salt =  userUtils.getSalt(username);
		String hash = generatePasswordHash(salt ,password);
	    return userUtils.areUserDCredentialsCorrect(username, hash);
	}

	public void saveUser(User user) {
		generatePasswordHash(user);
		userUtils.saveUser(user);
	}

	public long getIdForUserFromLoginBean(LoginBean loginBean) {
		return userUtils.getIdFromName(loginBean.getUsername());
	}

	private String generatePasswordHash(String salt, String password) {
		String hash = hashGenerationHelper.generateSHA256(salt+password);
		hash = hash.substring(0, 30);
		return hash;
	}

	private void generatePasswordHash(User user) {
		String salt = generateSalt(20);
		String hash = hashGenerationHelper.generateSHA256(salt+user.getPassword());
		hash = hash.substring(0, 30);
		user.setSalt(salt);
		user.setHash(hash);

	}

    private String generateSalt(int length) {
        RandomStringUtils utils = new RandomStringUtils();
        return utils.randomAscii(length);
    }
}
