package com.ctgu.controller;

import com.ctgu.dto.AjaxResult;
import com.ctgu.model.Questionnaire;
import com.ctgu.model.QuestionnaireReply;
import com.ctgu.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: QuestionnaireController
 * Description:
 * date: 2019/12/22 20:43
 *
 * @author crwen
 * @create 2019-12-22-20:43
 */
@RestController
@RequestMapping(value = "/questionnaire")
public class QuestionnaireController {

	@Autowired
	private QuestionnaireService questionnaireService;

	/**
	 *  新增调查问卷
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/api/addQuestionnaire", method = {RequestMethod.POST})
	public AjaxResult addQuestionnaire(@RequestBody Questionnaire questionnaire) {
		AjaxResult ajaxResult = new AjaxResult();
		int res = questionnaireService.addQuestionnaire(questionnaire);
		return new AjaxResult().setData(1);
	}

	/**
	 *  提交调查问卷
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/api/submitQuestionnaire", method = {RequestMethod.POST})
	public AjaxResult submitQuestionnaire(@RequestBody QuestionnaireReply questionnaire) {
		AjaxResult ajaxResult = new AjaxResult();
		//System.out.println(questionnaire);
		int res = questionnaireService.submitQuestionnaire(questionnaire);
		return new AjaxResult().setData(1);
	}

	/**
	 *  删除调查问卷
	 * @param id
	 * @return
	 */
	@DeleteMapping("/api/deleteQuestionnaire/{id}")
	public AjaxResult deleteQuestionnaire(@PathVariable int id) {
		AjaxResult ajaxResult = new AjaxResult();
		boolean result = questionnaireService.deleteQuestionnaireById(id);
		return new AjaxResult().setData(1);
	}

}
