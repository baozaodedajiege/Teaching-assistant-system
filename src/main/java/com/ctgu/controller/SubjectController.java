package com.ctgu.controller;

import com.ctgu.common.Const;
import com.ctgu.dto.AjaxResult;
import com.ctgu.exception.WebError;
import com.ctgu.model.Account;
import com.ctgu.model.Subject;
import com.ctgu.model.Syllabus;
import com.ctgu.service.AccountService;
import com.ctgu.service.SubjectService;
import com.ctgu.util.MD5;
import com.ctgu.util.word2html.Word2Html;
import com.ctgu.util.word2html.bean.DocHtmlDto;
import com.ctgu.util.word2html.config.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    private static Log LOG = LogFactory.getLog(SubjectController.class);

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private AccountService accountService;

    //添加课程
    @RequestMapping(value = "/api/addSubject", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addSubject(@RequestBody Subject subject) {
        AjaxResult ajaxResult = new AjaxResult();
        subject.setImgUrl(Const.DEFAULT_SUBJECT_IMG_URL);
        subject.setQuestionNum(0);
        int subjectId = subjectService.addSubject(subject);
        return new AjaxResult().setData(subjectId);
    }

    //更新课程
    @RequestMapping(value = "/api/updateSubject", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateSubject(@RequestBody Subject subject) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = subjectService.updateSubject(subject);
        return new AjaxResult().setData(result);
    }

    //删除课程
    @DeleteMapping("/api/deleteSubject/{id}")
    public AjaxResult deleteSubject(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = subjectService.deleteSubjectById(id);
        return new AjaxResult().setData(result);
    }

    /**
     * 分页获取所有课程列表
     */
    @RequestMapping(value = "/api/getSubjects", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult getSubjects(HttpServletRequest request, HttpServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Account current_account = accountService.getAccountByUsername(username);
            if (current_account != null) {
                String pwd = MD5.md5(Const.MD5_SALT + password);
                if (pwd.equals(current_account.getPassword())) {
                    request.getSession().setAttribute(Const.CURRENT_ACCOUNT, current_account);
                    ajaxResult.setData(current_account);
                } else {
                    return AjaxResult.fixedError(WebError.WRONG_PASSWORD);
                }
            } else {
                return AjaxResult.fixedError(WebError.WRONG_USERNAME);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return AjaxResult.fixedError(WebError.COMMON);
        }
        return ajaxResult;
    }


    /**
     * 添加教学大纲
     */
    @PostMapping("/api/addSyllabus/{subjectId}")
    @ResponseBody
    public AjaxResult addSyllabus(HttpServletRequest request,
                                  @RequestParam("file") MultipartFile file,
                                  @PathVariable int subjectId) throws IOException {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        AjaxResult ajaxResult = new AjaxResult();
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            ajaxResult.setMessage("用户尚未登录");
            return ajaxResult;
        }
        if (file.isEmpty()) {
            ajaxResult.setMessage("上传失败，请选择文件");
        }
        String fileName = file.getOriginalFilename();
        String filePath = Const.UPLOAD_FILE_DOC_PATH;
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            Word2Html word2Html = new Word2Html();
            DocHtmlDto docHtmlDto =  word2Html.doc2Html(filePath + fileName);
            ajaxResult.setData(docHtmlDto.getFilePath());
            Syllabus syllabus = new Syllabus();
            syllabus.setSubjectId(subjectId);
            syllabus.setSyllabusUrl(docHtmlDto.getFilePath());
            subjectService.updateSyllabusUrlBySubjectId(syllabus);
            LOG.info("上传成功");
        } catch (IOException e) {
            ajaxResult.setData("上传失败");
            LOG.error(e.toString(), e);
        }
        return ajaxResult;
    }

    /**
     * 教学大纲管理
     */
    @RequestMapping(value = "/api/getSyllabus/{subjectId}", method = RequestMethod.GET)
    public String getSyllabus(HttpServletRequest request,
                              @PathVariable int subjectId,
                              Model model) {
        String result = subjectService.getSyllabusBySubjectId(subjectId);
        if (result == null) {
            result = "init.html";
        }
        return result;
    }
}
