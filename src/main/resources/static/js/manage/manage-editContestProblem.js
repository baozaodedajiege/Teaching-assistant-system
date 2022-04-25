/**
 * 模块化JavaScript
 **/


var manageEditContestProblemPage = {
    data: {
        contest: null,
        questions: []
    },
    init: function (contest, questions) {
        manageEditContestProblemPage.data.contest = contest;
        manageEditContestProblemPage.data.questions = questions;
        window.operateEvents = {

            'click .addQes': function (e, value, row, index) {
                $.ajax({
                    url: app.URL.addContestQuestionUrl(),
                    type: "POST",
                    dataType: "json",
                    contentType: "application/json;charset=UTF-8",
                    data: JSON.stringify({
                        id: row.id,
                        contestId: manageEditContestProblemPage.data.contest.id,
                        score: row.score
                    }),
                    success: function (result) {
                        console.log(result);
                        if (result && result['success']) {
                            // 验证通过 刷新页面
                            window.location.reload();
                        } else {
                        }
                    },
                    error: function (result) {
                    }
                });
            },
        };
        $('#importQuestionTable').bootstrapTable({
            method: 'get',
            url: app.URL.getOtherQuestionsUrl(manageEditContestProblemPage.data.contest.id),//请求路径
            // striped: false, //是否显示行间隔色
            // classes: "table table-hover",
            pageNumber: 1, //初始化加载第一页
            pagination: true,//是否分页
            sidePagination: 'client',//server:服务器端分页|client：前端分页
            pageSize: 10,//单页记录数
            pageList: [5, 10, 20, 30],//可选择单页记录数
            showRefresh: true,//刷新按钮
            queryParams: function (params) {
                var temp = {
                    content: $('#keyWord').val()
                };
                return temp;
            },
            responseHandler: function (res) {
                console.log(res);
                return res.data;
            },
            search:true, //******开启搜索框****//
            searchOnEnterKey:true, //******回车后执行搜索****//
            columns:
                [
                    {field: "no", title: "#", align: "center", valign: "middle", sortable: "true", formatter: tableNum},
                    {field: "title", title: "标题", align: "left", valign: "middle", sortable: "true"},
                    {
                        field: "subjectName",
                        title: "课程名称",
                        align: "center",
                        valign: "middle",
                        sortable: "true",
                        formatter: formatSubjectName
                    },
                    {
                        field: "questionType",
                        title: "题目类型",
                        align: "center",
                        valign: "middle",
                        sortable: "true",
                        formatter: formatQuestionType
                    },
                    {
                        field: "difficulty",
                        title: "题目难度",
                        align: "center",
                        valign: "middle",
                        sortable: "true",
                        formatter: formatDifficulty
                    },
                    {
                        field: "op",
                        title: "操作",
                        events: "operateEvents",
                        formatter: operator
                    }
                ]
        });
        function tableNum(value, row, index) {
            var table = $('#importQuestionTable');
            //获取每页显示的数量
            var pageSize = table.bootstrapTable('getOptions').pageSize;
            //获取当前是第几页
            var pageNumber = table.bootstrapTable('getOptions').pageNumber;
            //返回序号，注意index是从0开始的，所以要加上1
            return pageSize * (pageNumber - 1) + index + 1;
        }

        //value代表该列的值，row代表当前对象
        function formatQuestionType(value, row, index) {
            if (value === 0) {
                return "单项选择";
            } else if (value === 1) {
                return "多项选择";
            } else if (value === 2) {
                return "问答题";
            } else if (value === 3) {
                return "编程题";
            } else {
                return "未知";
            }
        }

        function formatSubjectName(value, row, index) {
            return value === null ? "未知" : value;
        }

        function formatDifficulty(value, row, index) {
            return value + "星";
        }

        //添加操作
        function operator(value, row, index) {
            return "<button type=\"button\" class=\"addQes btn btn-success btn-xs\" ><i class=\"fa fa-plus\"></i>添加</button>";
        }

        //新增题目，弹出新增窗口
        $("#addQuestionBtn").click(function () {
            //输入框初始化数据
            manageEditContestProblemPage.initAddQuestionData();
            $("#addQuestionModal").modal({
                keyboard: false,
                show: true,
                backdrop: "static"
            });
        });

        //导入题目，弹出新增窗口
        $("#importQuestionBtn").click(function () {
            $("#importQuestionModal").modal({
                keyboard: false,
                show: true,
                backdrop: "static"
            });
        });


        //新增题目，取消题目增加
        $('#cancelAddQuestionBtn').click(function () {
            $("#addQuestionModal").modal('hide');
        });

        //新增考试，确定增加考试
        $('#confirmAddQuestionBtn').click(function () {
            manageEditContestProblemPage.addQuestionAction();
        });

        //编辑题目，取消题目编辑
        $('#cancelUpdateQuestionBtn').click(function () {
            $("#updateQuestionModal").modal('hide');
        });

        //编辑题目，确定保存考试
        $('#confirmUpdateQuestionBtn').click(function () {
            manageEditContestProblemPage.updateQuestionAction();
        });

        //编辑题目，取消题目编辑
        $('#cancelImportQuestionBtn').click(function () {
            $("#importQuestionModal").modal('hide');
        });

        //编辑题目，确定保存考试
        $('#confirmImportQuestionBtn').click(function () {
            manageEditContestProblemPage.updateQuestionAction();
        });

    },
    initAddQuestionData: function () {
        //初始化数据
        $('#questionTitle').val("");
        $('#questionContent').val("");
        $('#optionA').val("");
        $('#optionB').val("");
        $('#optionC').val("");
        $('#optionD').val("");
        $('#questionAnswer').val("");
        $('#questionParse').val("");
        $('#questionScore').val("");
    },
    checkAddQuestionData: function (questionTitle, questionContent, questionType,
                                    optionA, optionB, optionC, optionD,
                                    questionAnswer, questionParse, questionDifficulty,
                                    questionValue) {
        //TODO::校验
        return true;

    },
    addQuestionAction: function () {
        var questionTitle = $('#questionTitle').val();
        var questionContent = $('#questionContent').val();
        var questionType = $('#questionType').val();
        var optionA = $('#optionA').val();
        var optionB = $('#optionB').val();
        var optionC = $('#optionC').val();
        var optionD = $('#optionD').val();
        var questionAnswer = $('#questionAnswer').val();
        var questionParse = $('#questionParse').val();
        var questionDifficulty = $('#questionDifficulty').val();
        var questionScore = $('#questionScore').val();
        var contestId = manageEditContestProblemPage.data.contest.id;
        var subjectId = manageEditContestProblemPage.data.contest.subjectId;

        if (manageEditContestProblemPage.checkAddQuestionData(questionTitle, questionContent,
            questionType, optionA, optionB, optionC, optionD, questionAnswer, questionParse,
            questionDifficulty, questionScore)) {
            $.ajax({
                url: app.URL.addQuestionUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                    title: questionTitle,
                    content: questionContent,
                    questionType: questionType,
                    optionA: optionA,
                    optionB: optionB,
                    optionC: optionC,
                    optionD: optionD,
                    answer: questionAnswer,
                    parse: questionParse,
                    subjectId: subjectId,
                    contestId: contestId,
                    score: questionScore,
                    difficulty: questionDifficulty,
                }),
                success: function (result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                            '                <p>' + result.message + '</p>');
                        $('#loginModalErrorMessage').removeClass('hidden');
                    }
                },
                error: function (result) {
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>' + result.message + '</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            });
        }
    },
    //编辑题目模态框触发
    updateQuestionModalAction: function (index) {
        //编辑考试，弹出编辑窗口
        //console.log(index);
        //输入框初始化数据
        manageEditContestProblemPage.initUpdateQuestionData(index);
        $("#updateQuestionModal").modal({
            keyboard: false,
            show: true,
            backdrop: "static"
        });
    },
    initUpdateQuestionData: function (index) {
        //初始化数据
        $('#updateQuestionIndex').val(index);
        $('#updateQuestionTitle').val(questions[index].title);
        $('#updateQuestionContent').val(questions[index].content);
        var selectQuestionTypes = document.getElementById('updateQuestionType');
        for (var i = 0; i < selectQuestionTypes.length; i++) {
            if (selectQuestionTypes[i].value == questions[index].questionType) {
                selectQuestionTypes[i].selected = true;
            }
        }
        $('#updateOptionA').val(questions[index].optionA);
        $('#updateOptionB').val(questions[index].optionB);
        $('#updateOptionC').val(questions[index].optionC);
        $('#updateOptionD').val(questions[index].optionD);
        $('#updateQuestionAnswer').val(questions[index].answer);
        $('#updateQuestionParse').val(questions[index].parse);
        var selectQuestionDifficulty = document.getElementById('updateQuestionDifficulty');
        for (var i = 0; i < selectQuestionDifficulty.length; i++) {
            if (selectQuestionDifficulty[i].value == questions[index].difficulty) {
                selectQuestionDifficulty[i].selected = true;
            }
        }
        $('#updateQuestionScore').val(questions[index].score);
    },
    checkUpdateQuestionData: function (questionTitle, questionContent, questionType,
                                       optionA, optionB, optionC, optionD,
                                       questionAnswer, questionParse, questionDifficulty,
                                       questionScore) {
        //TODO::校验
        return true;

    },
    updateQuestionAction: function () {
        var index = $('#updateQuestionIndex').val();
        var questionTitle = $('#updateQuestionTitle').val();
        var questionContent = $('#updateQuestionContent').val();
        var questionType = $('#updateQuestionType').val();
        var optionA = $('#updateOptionA').val();
        var optionB = $('#updateOptionB').val();
        var optionC = $('#updateOptionC').val();
        var optionD = $('#updateOptionD').val();
        var questionAnswer = $('#updateQuestionAnswer').val();
        var questionParse = $('#updateQuestionParse').val();
        var questionDifficulty = $('#updateQuestionDifficulty').val();
        var questionScore = $('#updateQuestionScore').val();
        var contestId = manageEditContestProblemPage.data.contest.id;
        var subjectId = manageEditContestProblemPage.data.contest.subjectId;

        if (manageEditContestProblemPage.checkUpdateQuestionData(questionTitle, questionContent,
            questionType, optionA, optionB, optionC, optionD, questionAnswer, questionParse,
            questionDifficulty, questionScore)) {
            $.ajax({
                url: app.URL.updateQuestionUrl(),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                <!-- 向后端传输的数据 -->
                data: JSON.stringify({
                    id: questions[index].id,
                    title: questionTitle,
                    content: questionContent,
                    questionType: questionType,
                    optionA: optionA,
                    optionB: optionB,
                    optionC: optionC,
                    optionD: optionD,
                    answer: questionAnswer,
                    parse: questionParse,
                    subjectId: subjectId,
                    contestId: contestId,
                    score: questionScore,
                    difficulty: questionDifficulty,
                }),
                success: function (result) {
                    if (result && result['success']) {
                        // 验证通过 刷新页面
                        window.location.reload();
                    } else {
                        $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                            '                <p>' + result.message + '</p>');
                        $('#loginModalErrorMessage').removeClass('hidden');
                    }
                },
                error: function (result) {
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>' + result.message + '</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            });
        }
    },
    deleteQuestionAction: function (index) {
        $.ajax({
            url: app.URL.deleteContestQuestionUrl(),
            type: "POST",
            dataType: "json",
            data: JSON.stringify({
                id: index,
                contestId: manageEditContestProblemPage.data.contest.id,
            }),
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result && result['success']) {
                    // 验证通过 刷新页面
                    window.location.reload();
                } else {
                    $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                        '                <p>' + result.message + '</p>');
                    $('#loginModalErrorMessage').removeClass('hidden');
                }
            },
            error: function (result) {
                $('#loginModalErrorMessage').html('<i class="close icon"></i><div class="header">错误提示</div>\n' +
                    '                <p>' + result.message + '</p>');
                $('#loginModalErrorMessage').removeClass('hidden');
            }
        });
    }


};