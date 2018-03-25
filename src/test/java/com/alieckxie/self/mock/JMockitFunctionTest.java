package com.alieckxie.self.mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.mockito.MockitoAnnotations;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Invocation;
import mockit.MockUp;

public class JMockitFunctionTest {

	@Injectable
	private UserService userService;

	@Test
	public void testFindOneUser() {
		new Expectations(UserService.class) {
			{
				new UserService(withInstanceOf(UserDao.class), "admin");
				result = userService;

				userService.findById(123);
				result = "Hello Yanbin's blog";
			}
		};

		Example example = new Example();
		String user = example.findOneUser("admin");
		assertEquals("Hello Yanbin's blog", user);
	}
	
	@Test
	public void testStaticGetDtcnfrm() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final Date parseDate = simpleDateFormat.parse("2017-08-31");
		new Expectations(UserService.class) {
			{
				UserService.getDtcnfrm();
				result = parseDate;
			}
		};
		
		Example example = new Example();
		Date dateConfirm = example.getDateConfirmByApi();
		assertEquals("2017-08-31", simpleDateFormat.format(dateConfirm));
	}

	@org.mockito.Mock
	private UserDao mockedUserDao;

	@Test
	public void testFetchOneUser() {
		// 需要把最上对userService这个成员变量的Mocked的注解给去掉，这样后面的才能正确地mock构造函数中对象
		// 否则mock出来的对象就只注入到这个成员变量中，新new出来的就还是新的
		MockitoAnnotations.initMocks(this);
		new MockUp<UserService>() {
			@mockit.Mock
			public void $init(Invocation invocation, UserDao userDao, String category) {
				UserService userService = invocation.getInvokedInstance();
				Deencapsulation.setField(userService, mockedUserDao);
			}
		};

		when(mockedUserDao.findById(123)).thenReturn("Hello Again");

		Example example = new Example();
		String user = example.findOneUser("admin");
		assertEquals("Hello Again", user);
	}
}

class Example {
	
	public Date getDateConfirmByApi() {
		Date dtcnfrm = UserService.getDtcnfrm();
		System.out.println(dtcnfrm);
		return dtcnfrm;
	}

	public String findOneUser(String category) {
		if ("general".equals(category) || category.equals("admin")) {
			return new UserService(new UserDao(), category).findById(123);
		}
		throw new RuntimeException("Invalid category");
	}
}

class UserService {
	private UserDao userDao;
	private final String category;

	public static Date getDtcnfrm() {
		return new Date();
	} 
	
	public UserService(UserDao userDao, String category) {
		this.userDao = userDao;
		this.category = category;
	}

	public String findById(int id) {
		System.out.println(category);
		return userDao.findById(id);
	}
}

class UserDao {
	public String findById(int id) {
		throw new RuntimeException("not implemented");
	}
}
