package kr.co.itcen.mysite.exception;

public class UserDaoException extends RuntimeException {
	public UserDaoException() {
		super("UserDaoException Occurs");
	}
	public UserDaoException(String message) {
		super(message);
	}
}
