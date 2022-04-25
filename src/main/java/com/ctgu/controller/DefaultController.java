package com.ctgu.controller;

import com.ctgu.common.Const;
import com.ctgu.dao.ClassMapper;
import com.ctgu.dto.AjaxResult;
import com.ctgu.model.Class;
import com.ctgu.model.*;
import com.ctgu.service.*;
import com.github.pagehelper.PageHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    private static Log LOG = LogFactory.getLog(DefaultController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private SubjectCommentService subjectCommentService;
    @Autowired
    private Class_StudentService class_studentService;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private ClassService classService;

    /**
     * 首页
     */
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(Const.DATA, new Date());
        return "home";
    }

    /**
     * 在线考试列表页
     */
    @RequestMapping(value="/contest/index", method= RequestMethod.GET)
    public String contestIndex(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {
        System.out.println("in");
        contestService.updateStateToStart();
        contestService.updateStateToEnd();
        System.out.println("out");
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        Map<String, Object> data = contestService.getContests(page, Const.contestPageSize);
        model.addAttribute(Const.DATA, data);
        return "contest/index";
    }

    /**
     * 在线考试页
     */
    @RequestMapping(value="/contest/{contestId}", method= RequestMethod.GET)
    public String contestDetail(HttpServletRequest request,
                               @PathVariable("contestId") int contestId,
                               Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        Contest contest = contestService.getContestById(contestId);
        if (currentAccount == null || contest.getStartTime().getTime() > System.currentTimeMillis()
                || contest.getEndTime().getTime() < System.currentTimeMillis()) {
            return "redirect:/contest/index";
        }
        List<Question> questions = questionService.getQuestionsByContestId(contestId);
        for (Question question : questions) {
            question.setAnswer("");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("contest", contest);
        data.put("questions", questions);
        model.addAttribute(Const.DATA, data);
        return "contest/detail";
    }

    /**
     * 题库中心页
     */
    @RequestMapping(value="/problemset/list", method= RequestMethod.GET)
    public String problemSet(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        Map<String, Object> data = new HashMap<>();
        if(currentAccount.getLevel() == 0){
            List<Integer> classIds = class_studentService.selectByStudentId(currentAccount.getId());
            List<Subject> subjects = new ArrayList<Subject>();
            for(int i = 0 ; i < classIds.size() ; i++){
                Class aClass = classMapper.selectByPrimaryKey(classIds.get(i));
                Subject subject = subjectService.getSubjectById(aClass.getSubjectId());
                subjects.add(subject);
            }
            //实现分页
            int count = subjects.size();
            if (count == 0) {
                data.put("pageNum", 0);
                data.put("pageSize", 0);
                data.put("totalPageNum", 1);
                data.put("totalPageSize", 0);
                data.put("subjects", new ArrayList<>());
            }
            int totalPageNum = count % Const.subjectPageSize == 0 ? count / Const.subjectPageSize : count / Const.subjectPageSize + 1;
            if (page > totalPageNum) {
                data.put("pageNum", 0);
                data.put("pageSize", 0);
                data.put("totalPageNum", totalPageNum);
                data.put("totalPageSize", 0);
                data.put("subjects", new ArrayList<>());
            }
            PageHelper.startPage(page, Const.subjectPageSize);
            data.put("pageNum", page);
            data.put("pageSize", Const.subjectPageSize);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", count);
            data.put("subjects", subjects);
        }
        // 如果是管理员
        if(currentAccount.getLevel() != 0){
            data = subjectService.getSubjects(page,Const.subjectPageSize);
        }
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(Const.DATA, data);
        return "problem/problemset";
    }

    /**
     * 题目列表页
     */
    @RequestMapping(value="/problemset/{problemsetId}/problems", method= RequestMethod.GET)
    public String problemList(HttpServletRequest request,
                              @PathVariable("problemsetId") Integer problemsetId,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "content", defaultValue = "") String content,
                              @RequestParam(value = "difficulty", defaultValue = "0") int difficulty,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        Map<String, Object> data = questionService.getQuestionsByProblemsetIdAndContentAndDiffculty(page, Const.questionPageSize,
                problemsetId, content, difficulty);
        Subject subject = subjectService.getSubjectById(problemsetId);
        data.put("subject", subject);
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(Const.DATA, data);
        List list = new ArrayList();
        list = (List) data.get("questions");
        return "problem/problemlist";
    }

    /**
     * 根据内容和难度筛选题目
     */
    @RequestMapping(value="/problemset/{problemsetId}/problems", method= RequestMethod.POST)
    public String problemListPost(HttpServletRequest request,
                              @PathVariable("problemsetId") Integer problemsetId,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "content", defaultValue = "") String content,
                              @RequestParam(value = "difficulty", defaultValue = "0") int difficulty,
                              Model model) {
        System.out.println("content"+content);
        System.out.println("difficulty" + difficulty);
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        Map<String, Object> data = questionService.getQuestionsByProblemsetIdAndContentAndDiffculty(page, Const.questionPageSize,
                problemsetId, content, difficulty);
        Subject subject = subjectService.getSubjectById(problemsetId);
        data.put("subject", subject);
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(Const.DATA, data);
        List list = (List) data.get("questions");
//        List<Question> questions = questionService.getQuestionsBySubjectId(problemsetId);
//        model.addAttribute("questions",questions);
//        System.out.println("content" + content);
//        System.out.println("difficulty" + difficulty);
//        System.out.println(problemsetId+" : "+questions);
        return "problem/problemlist";
    }

    /**
     * 增加评论
     */
    @RequestMapping(value="/problemset/{problemsetId}/problem/{problemId}", method= RequestMethod.POST)
    public String problemDetail(HttpServletRequest request,
                                @PathVariable("problemsetId") Integer problemsetId,
                                @PathVariable("problemId") Integer problemId,
                                @RequestParam("accountId") Integer accountId,
                                @RequestParam("content") String content,
                                Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        Map<String, Object> data = new HashMap<>();
        Question question = questionService.getQuestionById(problemId);
        Subject subject = subjectService.getSubjectById(problemsetId);
        data.put("question", question);
        data.put("subject", subject);
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(Const.DATA, data);

        int nextQuestionId = question.getId();
        List<Question> questions = questionService.getQuestionsBySubjectId(problemsetId);
        for (int i = 0 ; i < questions.size() ; i++){
            if(questions.get(i).getId() == problemId){
                if(i < questions.size()-1){
                    nextQuestionId = questions.get(i+1).getId();
                } else {
                    nextQuestionId = questions.get(i).getId();
                }
            }
        }
//        System.out.println(nextQuestionId);
        data.put("nextQuestionId",nextQuestionId);

        Account account = accountService.getAccountById(accountId);
//        System.out.println(account);
        SubjectComment subjectComment = new SubjectComment();
        subjectComment.setAccountId(accountId);
        subjectComment.setAccountName(account.getName());
        subjectComment.setAvatarimgurl(account.getAvatarImgUrl());
        subjectComment.setContent(content);
        subjectComment.setCreateTime(new Date());
        subjectComment.setSubjectId(problemId);

//        System.out.println(subjectComment);
        subjectCommentService.insertSelective(subjectComment);
        //评论条数
        Integer count = subjectCommentService.getCountBySubjectId(problemId);
        data.put("count",count);
        //获取所有评论
        List<SubjectComment> subjectComments = subjectCommentService.selectBySubjectId(problemId);
        data.put("subjectComments",subjectComments);
        return "problem/problemdetail";
    }

    /**
     * 题目详情页
     */
    @RequestMapping(value="/problemset/{problemsetId}/problem/{problemId}", method= RequestMethod.GET)
    public String addComment(HttpServletRequest request,
                                @PathVariable("problemsetId") Integer problemsetId,
                                @PathVariable("problemId") Integer problemId,
                                Model model) {
        System.out.println("yes");
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        Map<String, Object> data = new HashMap<>();
        Question question = questionService.getQuestionById(problemId);
        Subject subject = subjectService.getSubjectById(problemsetId);
        data.put("question", question);
        data.put("subject", subject);
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(Const.DATA, data);
        int nextQuestionId = question.getId();
        List<Question> questions = questionService.getQuestionsBySubjectId(problemsetId);
        for (int i = 0 ; i < questions.size() ; i++){
            if(questions.get(i).getId() == problemId){
                if(i < questions.size()-1){
                    nextQuestionId = questions.get(i+1).getId();
                } else {
                    nextQuestionId = questions.get(i).getId();
                }
            }
        }
//        System.out.println(nextQuestionId);
        data.put("nextQuestionId",nextQuestionId);

        //评论条数
        Integer count = subjectCommentService.getCountBySubjectId(problemId);
        data.put("count",count);
        //获取所有评论
        List<SubjectComment> subjectComments = subjectCommentService.selectBySubjectId(problemId);
        data.put("subjectComments",subjectComments);
        return "problem/problemdetail";
    }

    /**
     * 讨论区首页
     */
    @RequestMapping(value="/discuss/{type}", method= RequestMethod.GET)
    public String discuss(HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "1") int page,
                          Model model,
                          @PathVariable Integer type
    ) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        Map<String, Object> data = new HashMap<>();
        List<Post> posts = new ArrayList<>();

        posts = postService.getPostsOrderByCreate_time();
        data = postService.getPostsPage(page, Const.postPageSize,posts);
//        if(type == 1) {
//            List<Reply> relies = replyService.getReliesOrderByCreateTime();
//            Set<Integer> set = new TreeSet<Integer>() ;
//            for(int i = 0 ; i < relies.size() ; i++){
//                if(!set.contains(relies.get(i).getPostId())){
//                    posts.add(postService.getPostById(relies.get(i).getPostId()));
//                    set.add(relies.get(i).getPostId());
//                }
//            }
//            data = postService.getPostsPage(page, Const.postPageSize,posts);
//        }
//        else if(type == 2){
//            posts = postService.getPostsOrderByCreate_time();
//            data = postService.getPostsPage(page, Const.postPageSize,posts);
//        }
//        else{
//            Map<Integer,Integer> map = new HashMap<>();
//            List<Reply> relies = replyService.getRelies();
//            for(int i = 0 ; i < relies.size() ; i++){
//                if(map.containsKey(relies.get(i).getPostId())){
//                    int sum = map.get(relies.get(i).getPostId());
//                    map.put(relies.get(i).getPostId(),sum+1);
//                }else{
//                    map.put(relies.get(i).getPostId(),1);
//                }
//            }
//
//            List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(map.entrySet());
//            Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
//                @Override
//                public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2)
//                {
//                    int compare = (o1.getValue()).compareTo(o2.getValue());
//                    return -compare;
//                }
//            });
//            for(Map.Entry<Integer, Integer> a:map.entrySet()){
//                posts.add(postService.getPostById(a.getKey()));
//            }
//            data = postService.getPostsPage(page, Const.postPageSize,posts);
//        }
        Set<Integer> authorIds = posts.stream().map(Post::getAuthorId).collect(Collectors.toCollection(HashSet::new));
        List<Account> authors = accountService.getAccountsByIds(authorIds);
        Map<Integer, Account> id2author = authors.stream().
                collect(Collectors.toMap(Account::getId, account -> account));
        for (Post post : posts) {
            post.setAuthor(id2author.get(post.getAuthorId()));
        }
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(Const.DATA, data);
        data.put("type",type);
        return "discuss/discuss";
    }

    /**
     * 帖子详情页
     */
    @RequestMapping(value="/discuss/discussDetail/{postId}", method= RequestMethod.GET)
    public String discussDetail(HttpServletRequest request, @PathVariable("postId") Integer postId, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);

        Map<String, Object> data = new HashMap<>();
        Post post = postService.getPostById(postId);
        Account author = accountService.getAccountById(post.getAuthorId());
        post.setAuthor(author);
        data.put("post", post);

        List<Comment> comments  = commentService.getCommentsByPostId(postId);
        List<Reply> replies = replyService.getReliesByPostId(postId);
        Set<Integer> userIds = new HashSet<>();
        for (Comment comment : comments) {
            comment.setReplies(new ArrayList<>());
            userIds.add(comment.getUserId());
        }
        for (Reply reply : replies) {
            userIds.add(reply.getUserId());
            userIds.add(reply.getAtuserId());
        }
        List<Account> users = accountService.getAccountsByIds(userIds);
        Map<Integer, Account> id2user = users.stream().
                collect(Collectors.toMap(Account::getId, account -> account));
        for (Comment comment : comments) {
            comment.setUser(id2user.get(comment.getUserId()));
        }
        for (Reply reply : replies) {
            reply.setUser(id2user.get(reply.getUserId()));
            if (reply.getAtuserId() != 0) {
                reply.setAtuser(id2user.get(reply.getAtuserId()));
            }
        }
        Map<Integer, Comment> id2comment = comments.stream().
                collect(Collectors.toMap(Comment::getId, comment -> comment));
        for (Reply reply : replies) {
            Comment comment = id2comment.get(reply.getCommentId());
            if (comment != null)
                comment.getReplies().add(reply);
        }
        data.put("comments", comments);
        if (currentAccount != null){
            data.put("userId", currentAccount.getId());
        } else {
            data.put("userId", 0);
        }

        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(Const.DATA, data);
        return "discuss/discussDetail";
    }

    /**
     * 发布帖子页
     */
    @RequestMapping(value="/discuss/post", method= RequestMethod.GET)
    public String postDiscuss(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        Map<String, Object> data = new HashMap<>();
        data.put("authorId", currentAccount.getId());
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(Const.DATA, data);
        return "discuss/postDiscuss";
    }

    /**
     *  查看消息通知
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/message/index", method= RequestMethod.GET)
    public String messageIndex(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        Map<String, Object> data = new HashMap<>();
        data.put("authorId", currentAccount.getId());
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        //List<Integer> subjectIds = classMapper.getSubjectIdsByAccountId(currentAccount.getId());
        List<Questionnaire> questionnaires = new ArrayList<>();
        //for (Integer subjectId : subjectIds) {
        //    List<Questionnaire> questionnaireList = questionnaireService.getQuestionnaireBySubjectId(subjectId);
        //    questionnaires.addAll(questionnaireList);
        //}
        List<Integer> classIds = class_studentService.selectByStudentId(currentAccount.getId());
        for (Integer classId : classIds) {
            Class cla = classService.getClassById(classId);
            List<Questionnaire> questionnaireList = questionnaireService.getQuestionnaireBySubjectId(cla.getSubjectId());
            questionnaires.addAll(questionnaireList);
        }

        data.put("questionnaires", questionnaires);

        //data.put("questionnaires", questionnaires);
        model.addAttribute(Const.DATA, data);
        return "message/messageIndex";
    }

    /**
     *  问卷调查详情
     * @param request
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/questionnaire/list/{id}", method = RequestMethod.GET)
    public String getQuestionnaireById(HttpServletRequest request,
                                       @PathVariable int id,
                                       Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null) {
            //return "redirect:/";
            return "error/404";
        } else {
            Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
            model.addAttribute("questionnaire", questionnaire);
        }
        return "manage/manage-questionnaire-detail";
    }


    /**
     * 获取服务器端时间,防止用户篡改客户端时间提前参与考试
     *
     * @return 时间的json数据
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult time() {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return new AjaxResult().setData(format);
    }

    /**
     * 测试分布式一致性session
     * @param session
     * @return
     */
    @RequestMapping(value = "/uid", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return new AjaxResult().setData(session.getId());
    }
}
