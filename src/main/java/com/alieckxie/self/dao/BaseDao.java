package com.alieckxie.self.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Repository
public class BaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insert(Number id, String name) {
		String sql = "INSERT INTO TEST1(ID, NAME) VALUES(?, ?)";
		int update = jdbcTemplate.update(sql, new Object[] { id, name });
		System.out.println(update);
	}

	public void delete(Number id, String name) {
		String sql = "DELETE FROM TEST1 WHERE ID = ? AND NAME = ?";
		int update = jdbcTemplate.update(sql, new Object[] { id, name });
		System.out.println(update);
	}

	@Transactional
	public void deleteAndInsert(Number id, String name, int factor) {
		delete(id, name);
		int i = 30 / factor;
		insert(i, name);
	}

	public void originDeleteAndInsert(Number id, String name, int factor) {
		PlatformTransactionManager transactionManager = (PlatformTransactionManager) MyApplicationContextAware
				.getSpringBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status = transactionManager.getTransaction(def);
		this.jdbcTemplate = (JdbcTemplate) MyApplicationContextAware.getSpringBean("jdbcTemplate");
		try {
			delete(id, name);
			int i = 30 / factor;
			insert(i, name);
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
		}
	}

	public void selectForInsert(Number id, String name) throws InterruptedException {
		String sqlCount = "SELECT ID FROM TEST1 WHERE ID = ? FOR UPDATE";
		String sqlInsert = "INSERT INTO TEST1(ID, NAME) VALUES(?, ?)";
		PlatformTransactionManager transactionManager = (PlatformTransactionManager) MyApplicationContextAware
				.getSpringBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		TransactionStatus status = transactionManager.getTransaction(def);
		this.jdbcTemplate = (JdbcTemplate) MyApplicationContextAware.getSpringBean("jdbcTemplate");
		List<Integer> count = jdbcTemplate.queryForList(sqlCount, int.class, id);
		if (count.size() == 0) {
			int update = jdbcTemplate.update(sqlInsert, id, name);
			Thread.sleep(1000 * 20);
			System.out.println("插入的sql的执行结果为---->" + update);
		}
		transactionManager.commit(status);
	}

	public void selectForTest(Number id, String name) {
		String sqlCount = "SELECT ID FROM TEST1 WHERE ID = ? FOR UPDATE";
		PlatformTransactionManager transactionManager = (PlatformTransactionManager) MyApplicationContextAware
				.getSpringBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		TransactionStatus status = transactionManager.getTransaction(def);
		this.jdbcTemplate = (JdbcTemplate) MyApplicationContextAware.getSpringBean("jdbcTemplate");
		List<Integer> count = jdbcTemplate.queryForList(sqlCount, int.class, id);
		if (count.size() == 0) {
			System.out.println("====进行Insert操作====");
		} else {
			System.out.println("====进行Update操作====");
		}
		transactionManager.commit(status);
	}

}
