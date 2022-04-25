package com.ctgu.service;

import com.ctgu.common.Const;
import com.ctgu.model.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    private static Log LOG = LogFactory.getLog(AccountServiceTest.class);

    @Autowired
    private AccountService accountService;

    @Test
    public void addAccount() throws Exception {
        Account account = new Account();
        account.setName("梅超风");
        account.setUsername("14251104208");
        account.setPassword("123456");
        account.setQq("1394176783");
        account.setPhone("15622110487");
        account.setEmail("zzqnxx@foxmail.com");
        account.setDescription("搬砖");
        account.setLevel(0);
        int result = accountService.addAccount(account);
        LOG.info("result = " + result);
    }

    @Test
    public void updateAccount() throws Exception {
        Account account = new Account();
        account.setId(1);
        account.setPassword("123456");
        account.setQq("1394176783");
        account.setPhone("15622110487");
        account.setEmail("zzqnxx@foxmail.com");
        account.setDescription("搬砖");
        boolean result = accountService.updateAccount(account);
        LOG.info("result = " + result);
    }

    @Test
    public void updateAvatarImgUrlById() throws Exception {
        boolean result = accountService.updateAvatarImgUrlById(Const.DEFAULT_AVATAR_IMG_URL+"1", 1);
        LOG.info("result = " + result);
    }

    @Test
    public void getAccountByUsername() throws Exception {
        Account account = accountService.getAccountByUsername("14251104208");
        LOG.info("account = " + account);
    }

    @Test
    public void getAccountsByStudentIds() throws Exception{

        List<Integer> stu_ids = new ArrayList<Integer>();
        stu_ids.add(1);
        stu_ids.add(2);
        stu_ids.add(3);
        List<Account> accounts = accountService.getAccountsByStudentIds(stu_ids);
        for(Account account : accounts ){
            LOG.info("account = " + account);
        }
    }

    @Test
    public void getAccounts() throws Exception{
        Map<String, Object> data = new HashMap<>();
        data = accountService.getAccounts(1,5);
        List<Account> accounts = (List<Account>) data.getOrDefault("accounts", new ArrayList<>());
        for (Account account : accounts) {
            LOG.info("account = " + account);
        }
    }

    @Test
    public void deleteAccount() throws Exception{
        boolean result = accountService.deleteAccount(15);
        LOG.info("result = " + result);
    }

    @Test
    public void disabledAccount() throws Exception{
        boolean result = accountService.disabledAccount(21);
        LOG.info("result = " + result);
    }

    @Test
    public void abledAccount() throws Exception{
        boolean result = accountService.abledAccount(21);
        LOG.info("result = " + result);
    }

    @Test
    public void getAccountsByIds() throws Exception{
        Set<Integer> ids = new HashSet<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        List<Account> accounts = accountService.getAccountsByIds(ids);
        for(Account account : accounts ){
            LOG.info("account = " + account);
        }
    }

    @Test
    public void getAccountById() throws Exception{
        Account account = accountService.getAccountById(1);
        LOG.info("account = " + account);
    }
}
