package com.ctgu.controller;

import com.ctgu.common.Const;
import com.ctgu.enums.AccountEnum;
import com.ctgu.model.*;
import com.ctgu.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.ctgu.common.Const.TeacherLevel;

@Controller
@RequestMapping(value = "/manage")
public class ManageController {

    private static Log LOG = LogFactory.getLog(ManageController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ClassService classService;
    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 管理员登录页
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);

        if (currentAccount == null) {
            return "manage/manage-login";
        } else {
            return "redirect:/manage/contest/list";
        }
    }

    /**
     * 用户管理
     */
    @RequestMapping(value = "/account/list", method = RequestMethod.GET)
    public String accountList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            //Map<String, Object> data = accountService.getAccounts(page, Const.accountPageSize);
            Map<String, Object> data = accountService.getAccountsByLevel(page, Const.accountPageSize, 1);
            model.addAttribute(Const.DATA, data);
            return "manage/manage-accountList";
        }
    }

    /**
     * 用户管理
     */
    @RequestMapping(value="/account/{level}/list", method= RequestMethod.GET)
    public String accountLevelList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                                   @PathVariable("level") Integer level,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            //Map<String, Object> data = accountService.getAccounts(page, Const.accountPageSize);
            Map<String, Object> data = accountService.getAccountsByLevel(page, Const.accountPageSize, level);
            data.put("subjects", subjectService.getSubjects());
            model.addAttribute(Const.DATA, data);
            return "manage/manage-account" + AccountEnum.getAccountEnum(level).getName() +  "List";
        }
    }

    /**
     * 考试管理
     */
    @RequestMapping(value = "/contest/list", method = RequestMethod.GET)
    public String contestList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = null;
            if (currentAccount.getLevel() == AccountEnum.ADMIN.getLevel()) {
                data = contestService.getContests(page, Const.contestPageSize);
            } else {
                data = contestService.getContestsByAccountId(page, Const.subjectPageSize, currentAccount.getId());
            }

            List<Subject> subjects = subjectService.getSubjects();
            data.put("subjects", subjects);
            model.addAttribute(Const.DATA, data);
            return "manage/manage-contestBoard";
        }
    }

    /**
     * 考试管理-查看试题
     */
    @RequestMapping(value = "/contest/{contestId}/problems", method = RequestMethod.GET)
    public String contestProblemList(HttpServletRequest request,
                                     @PathVariable("contestId") Integer contestId, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = new HashMap<>();
            List<Question> questions = questionService.getQuestionsByContestId(contestId);
            Contest contest = contestService.getContestById(contestId);
            data.put("questionsSize", questions.size());
            data.put("questions", questions);
            data.put("contest", contest);
            model.addAttribute(Const.DATA, data);
            return "manage/manage-editContestProblem";
        }
    }

    /**
     * 題目管理
     */
    @RequestMapping(value = "/question/list", method = RequestMethod.GET)
    public String questionList(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "content", defaultValue = "") String content,
                               Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = questionService.getQuestionsByContent(page,
                    Const.questionPageSize, content);
            List<Question> questions = (List<Question>) data.get("questions");
            List<Subject> subjects = subjectService.getSubjects();
            Map<Integer, String> subjectId2name = subjects.stream().
                    collect(Collectors.toMap(Subject::getId, Subject::getName));
            for (Question question : questions) {
                question.setSubjectName(subjectId2name.
                        getOrDefault(question.getSubjectId(), "未知科目"));
            }
            data.put("subjects", subjects);
            data.put("content", content);
            model.addAttribute("data", data);
            return "manage/manage-questionBoard";
        }
    }

    /**
     * 成绩管理-考试列表
     */
    @RequestMapping(value = "/result/contest/list", method = RequestMethod.GET)
    public String resultContestList(HttpServletRequest request,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = null;
            if (currentAccount.getLevel() == AccountEnum.ADMIN.getLevel()) {
                data = contestService.getContests(page, Const.contestPageSize);
            } else {
                data = contestService.getContestsByAccountId(page, Const.subjectPageSize, currentAccount.getId());

            }
            List<Subject> subjects = subjectService.getSubjects();
            data.put("subjects", subjects);
            model.addAttribute(Const.DATA, data);
            return "manage/manage-resultContestBoard";
        }
    }

    /**
     * 成绩管理-考试列表-学生成绩列表
     */
    @RequestMapping(value = "/result/contest/{contestId}/list", method = RequestMethod.GET)
    public String resultStudentList(HttpServletRequest request,
                                    @PathVariable("contestId") int contestId,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = new HashMap<>();
            List<Grade> grades = gradeService.getGradesByContestId(contestId);
            Contest contest = contestService.getContestById(contestId);
            List<Question> questions = questionService.getQuestionsByContestId(contestId);
            List<Integer> studentIds = grades.stream().map(Grade::getStudentId).collect(Collectors.toList());
            List<Account> students = accountService.getAccountsByStudentIds(studentIds);
            Map<Integer, Account> id2student = students.stream().
                    collect(Collectors.toMap(Account::getId, account -> account));
            for (Grade grade : grades) {
                Account student = id2student.get(grade.getStudentId());
                grade.setStudent(student);
            }
            data.put("grades", grades);
            data.put("contest", contest);
            data.put("questions", questions);
            model.addAttribute(Const.DATA, data);
            return "manage/manage-resultStudentBoard";
        }
    }

    /**
     * 课程管理
     */
    @RequestMapping(value = "/subject/list", method = RequestMethod.GET)
    public String subjectList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data;
            if (currentAccount.getLevel() == AccountEnum.ADMIN.getLevel()) {
                data = subjectService.getSubjects(page, Const.subjectPageSize);
            } else {
                data = subjectService.getSubjects(page, Const.subjectPageSize, currentAccount.getId());
            }


            model.addAttribute(Const.DATA, data);
            return "manage/manage-subjectBoard";
        }
    }

    /**
     * 班级管理
     */
    @RequestMapping(value = "/class/list/{id}", method = RequestMethod.GET)
    public String classList(HttpServletRequest request,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            @PathVariable int id,
                            Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = classService.getClassesBySubjectId(page, Const.classPageSize, id);
            model.addAttribute(Const.DATA, data);
            List<Account> teachers = accountService.getAccountsByLevel(TeacherLevel);
            model.addAttribute("teachers", teachers);
            List<Subject> subjects = subjectService.getSubjects();
            model.addAttribute("subjects", subjects);
            return "manage/manage-classBoard";
        }
    }

    /**
     * 班级人数管理
     */
    @RequestMapping(value = "/class/classStudentList/{classId}", method = RequestMethod.GET)
    public String classStudentList(HttpServletRequest request,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            @PathVariable int classId,
                            Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = classService.getStudentByClassId(page, Const.accountPageSize, classId);
            model.addAttribute(Const.DATA, data);
            return "manage/manage-classStudentList";
        }
    }

    /**
     * 帖子管理
     */
    @RequestMapping(value = "/post/list", method = RequestMethod.GET)
    public String postList(HttpServletRequest request,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = postService.getPosts(page, Const.postPageSize);
            List<Post> posts = (List<Post>) data.get("posts");
            Set<Integer> authorIds = posts.stream().map(Post::getAuthorId).collect(Collectors.toCollection(HashSet::new));
            List<Account> authors = accountService.getAccountsByIds(authorIds);
            Map<Integer, Account> id2author = authors.stream().
                    collect(Collectors.toMap(Account::getId, account -> account));
            for (Post post : posts) {
                post.setAuthor(id2author.get(post.getAuthorId()));
            }
            model.addAttribute(Const.DATA, data);
            return "manage/manage-postBoard";
        }
    }

    /**
     * 教学大纲管理
     */
    @RequestMapping(value = "/syllabus/{subjectId}", method = RequestMethod.GET)
    public String getSyllabus(HttpServletRequest request,
                              @PathVariable int subjectId,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            String syllabus_url = subjectService.getSyllabusBySubjectId(subjectId);
            if(syllabus_url != null) {
                model.addAttribute(Const.DATA, Const.DEFAULT_SYLLABUS_PATH + syllabus_url);
            } else {
                model.addAttribute(Const.DATA, Const.DEFAULT_FILE_HTML_PATH);
            }
        }
        return "manage/manage-syllabus-index";
    }
    /**
     * 评论管理
     */
    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    public String commentList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = commentService.getComments(page, Const.commentPageSize);
            List<Comment> comments = (List<Comment>) data.get("comments");
            Set<Integer> userIds = comments.stream().map(Comment::getUserId).collect(Collectors.toCollection(HashSet::new));
            List<Account> users = accountService.getAccountsByIds(userIds);
            Map<Integer, Account> id2user = users.stream().
                    collect(Collectors.toMap(Account::getId, account -> account));
            for (Comment comment : comments) {
                comment.setUser(id2user.get(comment.getUserId()));
            }
            model.addAttribute(Const.DATA, data);
            return "manage/manage-commentBoard";
        }
    }

    /**
     *  调查问卷管理
     * @param request
     * @param page
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/questionnaire/{id}/list", method = RequestMethod.GET)
    public String getQuestionnaire(HttpServletRequest request,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @PathVariable int id,
                                   Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = questionnaireService.getQuestionnaireBySubjectId(page, Const.questionnairePageSize, id);
            model.addAttribute(Const.DATA, data);
            List<Account> teachers = accountService.getAccountsByLevel(TeacherLevel);
            model.addAttribute("teachers", teachers);
            List<Subject> subjects = subjectService.getSubjects();
            model.addAttribute("subjects", subjects);
            model.addAttribute("subjectId", id);
        }
        return "manage/manage-questionnaireBoard";
    }

    @RequestMapping(value = "/questionnaire/index/add/{id}" , method = RequestMethod.GET)
    public String index(HttpServletRequest request,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @PathVariable int id,
                        Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        }
        model.addAttribute("subjectId", id);
        return "manage/manage-questionnaire-add";
    }

    @RequestMapping(value = "/questionnaire/list/{id}", method = RequestMethod.GET)
    public String getQuestionnaireById(HttpServletRequest request,
                                   @PathVariable int id,
                                   Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
            model.addAttribute("questionnaire", questionnaire);
        }
        return "manage/manage-questionnaire-detail";
    }

    @RequestMapping(value = "/questionnaireReply/{id}/list", method = RequestMethod.GET)
    public String getQuestionnaireReplyById(HttpServletRequest request,
                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                       @PathVariable int id,
                                       Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            Map<String, Object> data = questionnaireService.getQuestionnaireReplyById(page, Const.questionnairePageSize, id);
            model.addAttribute(Const.DATA, data);
        }
        return "manage/manage-questionnaireReplyList";
    }

    @RequestMapping(value = "/questionnaireReply/list/{id}", method = RequestMethod.GET)
    public String getQuestionnaireReplyById(HttpServletRequest request,
                                       @PathVariable int id,
                                       Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(Const.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "error/404";
        } else {
            QuestionnaireReply questionnaireReply = questionnaireService.getQuestionnaireReplyById(id);
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setTitle(questionnaireReply.getTitle());
            questionnaire.setContent(questionnaireReply.getHtmlContent());
            questionnaire.setCreateTime(questionnaireReply.getCreateTime());
            model.addAttribute("subjectId", id);
            model.addAttribute("questionnaire", questionnaire);
        }
        return "manage/manage-questionnaireReply-detail";
    }
}
