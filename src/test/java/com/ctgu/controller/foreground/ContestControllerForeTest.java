package com.ctgu.controller.foreground;

import com.ctgu.controller.ContestController;
import com.ctgu.dto.AjaxResult;
import com.ctgu.model.Contest;
import com.ctgu.service.ContestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/*
 *@Author： 李菊
 *@Date： 2019/12/25
 *@Description：对于考试的测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class ContestControllerForeTest {
    /*
     *@Param：
     *@return：
     *@Description：不同参数测试
     **/
    @Autowired
    private ContestController contestController;
    @Autowired
    private ContestService contestService;

    @Test
    @Transactional
    @Rollback
    public void addContestTest() {
        Contest contest = new Contest();
        contest.setSubjectName("信息安全");
        contest.setSubjectId(3);
        contest.setId(11);
        contest.setState(1);
        contest.setStartTime(new Date());
        contest.setEndTime(new Date());
        AjaxResult ajaxResult = contestController.addContest(contest);
        Assert.assertNotNull(ajaxResult);
        System.out.println(ajaxResult);

    }

    @Test
    @Transactional
    @Rollback
    public void updateContestTest() {
        Contest contest = contestService.getContestById(4);
        Assert.assertNotNull(contest);
        contest.setTotalScore(156);
        AjaxResult ajaxResult = contestController.updateContest(contest);
        Assert.assertNotNull(ajaxResult);
        System.out.println(ajaxResult);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteContestTest() {
        AjaxResult ajaxResult = contestController.deleteContest(15);
        Assert.assertNotNull(ajaxResult);
        System.out.println(ajaxResult);
    }

    @Test
    @Transactional
    @Rollback
    public void finishContestTest() {
        AjaxResult ajaxResult = contestController.finishContest(4);
        Assert.assertNotNull(ajaxResult);
        Contest contest = contestService.getContestById(4);
        Assert.assertNotNull(contest);
        Assert.assertEquals(3, contest.getState());
        System.out.println(contest);
    }

}
