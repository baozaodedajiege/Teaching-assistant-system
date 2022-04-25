package com.ctgu.controller;

import com.ctgu.common.Const;
import com.ctgu.dto.AjaxResult;
import com.ctgu.model.Account;
import com.ctgu.model.Class;
import com.ctgu.model.Class_Student;
import com.ctgu.model.Syllabus;
import com.ctgu.service.ClassService;
import com.ctgu.util.ReadExcel;
import com.ctgu.util.word2html.Word2Html;
import com.ctgu.util.word2html.bean.DocHtmlDto;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author aoboxia
 * @date 2019/12/18 21:55
 */
@RestController
@RequestMapping(value = "/class")
public class ClassController {
    private static Log LOG = LogFactory.getLog(ClassController.class);

    @Autowired
    private ClassService classService;

    //添加课程
    @RequestMapping(value="/api/addClass", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addClass(@RequestBody Class _class) {
        AjaxResult ajaxResult = new AjaxResult();
        int classId = classService.addClass(_class);
        return new AjaxResult().setData(classId);
    }

    //更新课程
    @RequestMapping(value="/api/updateClass", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateClass(@RequestBody Class subject) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = classService.updateClass(subject);
        return new AjaxResult().setData(result);
    }

    //删除课程
    @DeleteMapping("/api/deleteClass/{id}")
    public AjaxResult deleteClass(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = classService.deleteClassById(id);
        return new AjaxResult().setData(result);
    }

    //保存课程
    @RequestMapping(value="/api/saveClass", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult saveClass(@RequestBody Map classList) {
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(classList);
        boolean result = true;
        return new AjaxResult().setData(result);
    }

    //从excel中导入班级学生列表
    /**
     * 添加教学大纲
     */
    @PostMapping("/api/addStudentList/{classId}")
    @ResponseBody
    public AjaxResult addSyllabus(HttpServletRequest request,
                                  @RequestParam("file") MultipartFile file,
                                  @PathVariable int classId) throws IOException {
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        AjaxResult ajaxResult = new AjaxResult();
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            ajaxResult.setMessage("用户操作不合法");
            return ajaxResult;
        }
        if (file.isEmpty()) {
            ajaxResult.setMessage("上传失败，请选择文件");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        String filePath = System.currentTimeMillis()+"_tmp";
        // 用uuid作为文件名，防止生成的临时文件重复
        final File excelFile = File.createTempFile(filePath, prefix);
        // MultipartFile to File
        file.transferTo(excelFile);
        List<Account> accountList = ReadExcel.readMoreSheetFromXLS(excelFile);
        classService.addStudentByClassId(accountList, classId);
        LOG.info("上传成功");
        //程序结束时，删除临时文件
        deleteFile(excelFile);
        return ajaxResult;
    }
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
