package thrillio.Managers;

import java.util.List;



import thrillio.constants.Gender;
import thrillio.dao.UserDao;
import thrillio.entities.User;
import thrillio.util.StringUtil;

public class UserManager {
	private static UserManager instance = new UserManager();
	private static UserDao dao = new UserDao();
	

	private UserManager() {}
	public static UserManager getInstance(){
		return instance;
	}
	public User createUser(long id, String email, String password, String firstName, String lastName, Gender gender, String userType) {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstNsme(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setUserType(userType);
		return user;
		
		
	}
	public List<User> getUsers() {
		return dao.getUsers();
	}
	public User getUser(long userId) {
		
		
		return dao.getUser(userId);
		
	}
	public long authenticate(String email, String password) {
		// TODO Auto-generated method stub
		String password1 =StringUtil.encodePassword(password);
		System.out.println("password1: "+ password1);
		return dao.authenticate(email,password1);
		
	}

	

}
