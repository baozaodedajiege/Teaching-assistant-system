package com.ctgu.service;

import com.ctgu.model.Contest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class ContestServiceTest {

    private static Log LOG = LogFactory.getLog(ContestServiceTest.class);

    @Autowired
    private ContestService contestService;

    @Test
    public void addContest() throws Exception {
        Contest contest = new Contest();
        contest.setTitle("三峡大学2019年数据库原理与应用考试试题B卷");
        contest.setSubjectId(8);


        DateFormat startTimeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = startTimeDateFormat.parse("2019-12-21 09:00:00");
        contest.setStartTime(startTime);

        DateFormat endTimeDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endTime = endTimeDateFormat.parse("2019-12-21 11:00:00");
        contest.setEndTime(endTime);

        int result = contestService.addContest(contest);
        LOG.info("result = " + result);
    }

    @Test
    public void updateContest() throws Exception {
        Contest contest = contestService.getContestById(9);
        contest.setTitle("三峡大学2019年数据库原理与应用考试试题A卷");
        boolean result = contestService.updateContest(contest);
        LOG.info("result = " + result);
    }

    @Test
    public void getContestById() throws Exception {
        Contest contest = contestService.getContestById(1);
        LOG.info("contest = " + contest);
    }

    @Test
    public void getContests() throws Exception {
        Map<String, Object> data = contestService.getContests(1, 10);
        List<Contest> contests = (List<Contest>) data.get("contests");
        for (Contest contest : contests) {
            LOG.info("contest = " + contest);
        }
    }

    @Test
    public void updateStateToStart() throws Exception{
        boolean result = contestService.updateStateToStart();
        LOG.info("result = " + result);
    }

    @Test
    public void updateStateToEnd() throws Exception{
        boolean result = contestService.updateStateToEnd();
        LOG.info("result = " + result);
    }

    @Test
    public void getContestsByContestIds() throws Exception{
        Set<Integer> contest_ids = new HashSet<Integer>();
        contest_ids.add(8);
        contest_ids.add(9);
        List<Contest> contests = contestService.getContestsByContestIds(contest_ids);
        for(Contest contest : contests){
            LOG.info("contest = " + contest);
        }
    }

}
