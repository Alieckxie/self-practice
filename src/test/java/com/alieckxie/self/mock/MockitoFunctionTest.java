package com.alieckxie.self.mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

public class MockitoFunctionTest {
	private PersonDao mockDao;
	private PersonService personService;

	@Before
	public void setUp() throws Exception {
		// 模拟PersonDao对象
		mockDao = mock(PersonDao.class);
		when(mockDao.getPerson(1)).thenReturn(new Person(1, "Person1"));
		when(mockDao.update(isA(Person.class))).thenReturn(true);

		personService = new PersonService(mockDao);
	}

	@Test
	public void testUpdate() throws Exception {
		boolean result = personService.update(1, "new name");
		assertTrue("must true", result);
		// 验证是否执行过一次getPerson(1)
		verify(mockDao, times(1)).getPerson(eq(1));
		// 验证是否执行过一次update
		verify(mockDao, times(1)).update(isA(Person.class));
	}

	@Test
	public void testUpdateNotFind() throws Exception {
		boolean result = personService.update(2, "new name");
		assertFalse("must true", result);
		// 验证是否执行过一次getPerson(1)
		verify(mockDao, times(1)).getPerson(eq(1));
		// 验证是否执行过一次update
		verify(mockDao, never()).update(isA(Person.class));
	}

	@Test
	public void testMockitoMock() {
		List<?> mockList = Mockito.mock(ArrayList.class);
		System.out.println(mockList.size());
		Mockito.when(mockList.size()).thenReturn(31);
		System.out.println(mockList.size());
		MatcherAssert.assertThat(mockList.size(), CoreMatchers.equalTo(31));
	}
	
	@Test
	public void testSpy() {
		List<String> list = new LinkedList<String>();
		List<String> spy = spy(list);

		// Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
		// 不可能 : 因为当调用spy.get(0)时会调用真实对象的get(0)函数,此时会发生IndexOutOfBoundsException异常，因为真实List对象是空的
		// when(spy.get(0)).thenReturn("foo");

		// You have to use doReturn() for stubbing
		// 你需要使用doReturn()来打桩
		doReturn("foo").when(spy).get(0);
		System.out.println(spy.get(0));
	}
	
	@Mock private PersonDao annotatedMockDao;
	@InjectMocks @Spy private PersonService annotatedPersonService;
	
	@Test
	public void testAnnotationInject() throws Exception {
		MockitoAnnotations.initMocks(this);
		Field personDaoField = PersonService.class.getDeclaredField("personDao");
		personDaoField.setAccessible(true);
		PersonDao personDao = (PersonDao) personDaoField.get(annotatedPersonService);
		assertThat(personDao.toString(), equalTo(annotatedMockDao.toString()));
		System.out.println(annotatedMockDao);
		System.out.println(annotatedPersonService);
	}
	
	@Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.WARN);
	
	@Test
	public void testAnnotationInjectWithRule() throws Exception {
		Field personDaoField = PersonService.class.getDeclaredField("personDao");
		personDaoField.setAccessible(true);
		PersonDao personDao = (PersonDao) personDaoField.get(annotatedPersonService);
		assertThat(personDao, equalTo(annotatedMockDao));
		System.out.println(annotatedMockDao);
		System.out.println(annotatedPersonService);
	}
}

class PersonService {
	private final PersonDao personDao;

	public PersonService(PersonDao personDao) {
		this.personDao = personDao;
	}

	public boolean update(int id, String name) {
		Person person = personDao.getPerson(id);
		if (person == null) {
			return false;
		}
		Person personUpdate = new Person(person.getId(), name);
		return personDao.update(personUpdate);
	}
}

interface PersonDao {
	Person getPerson(int id);

	boolean update(Person person);
}

class Person {
	private final int id;
	private final String name;

	public Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}