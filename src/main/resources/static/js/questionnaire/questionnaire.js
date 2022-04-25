/**
 * 模块JavaScript
 */
var questionnairePage = {
    data: {
        // data:{};
        index: 0,
        cnt: 0,
        titleId : 0,
        authorId: 0,
        subjectId: 0,
    },
    init: function (content, authorId, subjectId) {
        questionnairePage.data.index = 0;
        questionnairePage.data.cnt = 0;
        questionnairePage.data.titleId = 0;
        questionnairePage.data.authorId = authorId;
        questionnairePage.data.subjectId = subjectId;
        questionnairePage.data.content = content;
    },

    // 添加问题
    addQuestion: function (btn) {
        cnt = 0;

        var text = '  <div>  ' +
            '<div class="ui icon input ques">\n' +
            '         <div class="ui label">\n' +
            '    题目内容' +
            '  </div> <input type="text" maxlength="60" class="notempty" id="title_' + (++questionnairePage.data.titleId) + '"> ' + '\n' +
            '    </div>' +
            '</div>' +
            '        <div> \n' +
            '            <div class="ui icon input ques"> \n' +

            '                <div class="ui label"> 选项' + (cnt + 1) + '</div><input type="text" maxlength="60" value="" id="opt_' + (++cnt) + '">&emsp;\n' +
            '            </div>\n' +
            '        </div>' +
            '    <button class="ui small circular icon green button right floated" onclick="questionnairePage.addOption(this)" id="add_opt">\n' +
            '        <i class="add icon"></i>\n' +
            '    </button>' +
            '<button class="ui small circular icon green button right floated" onclick="questionnairePage.removeOption(this)" id="reomve_opt"> <i class="minus icon"></i></button>' +

            '    <div>\n' +
            '        <button class="ui green button" onclick="questionnairePage.saveQuestion(this)">保存</button>\n' +
            '    </div>';


        var tar = $(btn).parent().last();
        tar.append(text);
        $(btn).addClass("disabled");
    },

    // 添加选项
    addOption: function (btn) {
        var text = '        <div> \n' +
            '            <div class="ui icon input ques"> \n' +

            '                <div class="ui label"> 选项' + (cnt + 1) + '</div>' +
            '<input type="text" maxlength="60" value="" id="opt_' + (++cnt) + '">&emsp;\n' +
            '            </div>\n' +
            '        </div>';
        var parent = $(btn).parent();
        $(btn).before(text);
    },

    //
    removeOption: function(btn) {
        $("#opt_" + cnt).parent().remove();
        cnt--;
    },

    // 保存问题和选项
    saveQuestion: function (btn) {
        let opts = [];
        let text = "";
        for (let i = 0; i < cnt; i++) {
            var idname = "opt_" + (i + 1);
            var opt = $("#" + idname);
            opts[i] = opt.val();
        }
        let title_id = "title_" + questionnairePage.data.titleId;
        let title_context = $("#" + title_id).val();

        let addbtn = $("#add_ques");
        $(".ques").remove();
        $("#add_opt").remove();
        btn.remove();
        $("#reomve_opt").remove();
        addbtn.removeClass("disabled");


        let p = '<div class="grouped fields">\n' +
            '                <h3 class="ques_title">* ' + questionnairePage.data.titleId + '. ' + title_context + '</h3>\n';
        for (let i = 0; i < cnt; i++) {
            let name = new Date().getSeconds();
            p += '                <div class="field">\n' +
                '                    <div class="ui radio checkbox ques_opt">\n' +
                '                       <input type="radio" name="' + name + '">\n' +
                '                        <label>' + opts[i] + '</label>\n' +
                '                    </div>\n' +
                '</div>\n'
        }


        $("#problem_list").append(p);
        cnt = 0;
    },

    // 保存调查问卷
    saveQuestionnaire: function (authorId,subjectId) {
        this.authorId = authorId;
        this.subjectId = subjectId;
        $.ajax({
            url : app.URL.addQuestionnaireUrl(),
            type : "POST",
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            <!-- 向后端传输的数据 -->
            data : JSON.stringify({
                title: $("#questionnaire_title").val(),
                content: $("#problem_list").html(),
                authorId: authorId,
                subjectId: subjectId,
            }),
            success:function(result) {
                if (result && result['success']) {
                    // 验证通过 刷新页面
                    //window.location.reload();
                    window.location.href = app.URL.manageSubjectListUrl();
                } else {
                    console.log(result.message);
                }
            },
            error:function(result){
                console.log(result.message);
            }
        });
    },

};