package com.alieckxie.self.database;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alieckxie.self.dao.BaseDao;
import com.alieckxie.self.dao.MyApplicationContextAware;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-transaction.xml" })
public class DataBaseConnTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Resource
	private BaseDao baseDao;

	@Test
	public void testDataBaseConnection() {
		String sql = "SELECT * FROM TEST1";
		SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(sql);
		SqlRowSetMetaData metaData = queryForRowSet.getMetaData();
		String[] columnNames = metaData.getColumnNames();
		for (String string : columnNames) {
			System.out.println(string);
		}
		System.out.println(queryForRowSet);
	}

	@Test
	public void testBaseDaoInsert() {
		baseDao.insert(6, "Alieckxie");
	}

	@Test
	public void testTransactional() {
		baseDao.deleteAndInsert(6, "Alieckxie", 10);
	}

	@Test
	public void testGetApplicationContextAware() {
		ApplicationContext applicationContext = MyApplicationContextAware.getApplicationContext();
		System.out.println(applicationContext);
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) applicationContext
				.getBean("transactionManager");
		System.out.println(transactionManager);

	}

	@Test
	public void testOriginTransaction() {
		new BaseDao().originDeleteAndInsert(3, "Alieckxie", 5);
	}

	@Test
	public void testSelectLock1() throws InterruptedException {
		new BaseDao().selectForInsert(3, "Alieckxie");
	}

	@Test
	public void testSelectLock2() {
		new BaseDao().selectForTest(3, "Test");
	}

}
