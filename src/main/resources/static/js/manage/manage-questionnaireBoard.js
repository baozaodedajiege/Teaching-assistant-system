
var manageQuestionnaireBoardPage = {
    data: {
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        content:"",
        title:"",
        subjectId: 0
    },

    init: function (pageNum, pageSize, totalPageNum, totalPageSize, content, title) {

        manageQuestionnaireBoardPage.data.pageNum = pageNum;
        manageQuestionnaireBoardPage.data.pageSize = pageSize;
        manageQuestionnaireBoardPage.data.totalPageNum = totalPageNum;
        manageQuestionnaireBoardPage.data.totalPageSize = totalPageSize;
        manageQuestionnaireBoardPage.data.content = content;
        manageQuestionnaireBoardPage.data.title = title;
        //分页初始化
        // manageQuestionnaireBoardPage.subPageMenuInit();

        // let questionnaireContent = $("#questionnaire_content");

        $("#questionnaire_content").append(content)
    },


    addQuestionIndexAction: function(index) {
        window.location.href = app.URL.addQuestionnaireIndexUrl() + index;
    },

    viewQuestionnaireAction: function(index) {
        window.open(app.URL.viewQuestionnaireIndexUrl() + index);
    },

    viewQuestionnaireReplyAction: function(index) {
        window.open(app.URL.viewQuestionnaireReplyUrl(index));
    },
    viewQuestionnaireReplyDetailAction:function(index) {
        window.open(app.URL.viewQuestionnaireReplyDetailUrl() + index)
    },

    deleteQuestionnaireAction:function(index) {
        $.ajax({
            url : app.URL.deleteQuestionnaireUrl() + index,
            type : "DELETE",
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            success:function(result) {
                if (result && result['success']) {
                    // 验证通过 刷新页面
                    window.location.reload();
                } else {
                    console.log(result.message);
                }
            },
            error:function(result){
                console.log(result.message);
            }
        });
    },

    // 提交调查问卷
    submitQuestionnaireAction: function (questId, submitId) {

        $.ajax({
            url : app.URL.submitQuestionnaireUrl(),
            type : "POST",
            dataType: "json",
            contentType : "application/json;charset=UTF-8",
            <!-- 向后端传输的数据 -->
            data : JSON.stringify({
                title: title,
                htmlContent: $("#questionnaire_content").html(),
                submitId:submitId,
                questId:questId,
            }),
            success:function(result) {
                if (result && result['success']) {
                    // 验证通过 刷新页面
                    //window.location.reload();
                    window.location.href = app.URL.homeUrl();
                } else {
                    console.log(result.message);
                }
            },
            error:function(result){
                console.log(result.message);
            }
        });
    },

    subPageMenuInit: function(){
        var subPageStr = '<ul class="pagination">';
        if (managePostBoardPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePostBoardPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="managePostBoardPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (managePostBoardPage.data.pageNum-4 > 0 ? managePostBoardPage.data.pageNum-4 : 1);
        var endPage = (startPage+7 > managePostBoardPage.data.totalPageNum ? managePostBoardPage.data.totalPageNum : startPage+7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + managePostBoardPage.data.pageNum);
        console.log('totalPageNum = ' + managePostBoardPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == managePostBoardPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="managePostBoardPage.targetPage('+i+')">'+i+'</a></li>';
            } else {
                subPageStr += '<li><a onclick="managePostBoardPage.targetPage('+i+')">'+i+'</a></li>';
            }
        }
        if (managePostBoardPage.data.pageNum == managePostBoardPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="managePostBoardPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="managePostBoardPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },
};