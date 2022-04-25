package com.ctgu.dao;

import com.ctgu.model.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: AccountMapperTest
 * Description:
 * date: 2019/12/19 13:03
 *
 * @author crwen
 * @create 2019-12-19-13:03
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountMapperTest {

	@Autowired
	private AccountMapper accountMapper;

	@Test
	public void getAccountsByLevelTest() {
		List<Account> accountsList = accountMapper.getAccountsByLevel(2);
		Assert.assertNotNull(accountsList);
		for (Account account : accountsList) {
			System.out.println(account);
		}
	}

	@Test
	public void getCountByLevelTest() {
		int count = accountMapper.getCountByLevel(2);
		System.out.println(count);
	}
}