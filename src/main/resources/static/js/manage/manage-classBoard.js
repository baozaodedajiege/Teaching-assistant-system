/**
 * 模块化JavaScript
 **/
$(function () {
    //创建行
    addRow();

    function addRow() {
        $('body').on("click", ".add", function () {
            var tr = $(this).parent().parent();
            var newtr = tr.clone();
            tr.after(newtr);
            //改变序数
            $(".num").each(function (i) {
                $(".num").eq(i).html(i + 1);
            });
        });
    }

    //删除行
    del();

    function del() {
        $("body").on("click", ".del", function () {
            var tr = $(this).parent().parent();
            tr.remove();
            //改变序数
            $(".num").each(function (i) {
                $(".num").eq(i).html(i + 1);
            })
        })
    }

    upData();

    function upData() {
        $("body").on("dblclick", ".val", function () {
            var $this = $(this);
            var html = $this.html();
            var input = $('<input type="text" value="' + html + '">');
            $this.html(input);
            input.focus();
            input.blur(function () {
                var val = $(this).val();
                $this.html(val);
            })
        })
    }

    //提交数据
    sublime();

    function sublime() {
        $("#save").on("click", function() {
            var datas = [];
            $("#class_table tr").each(function(i) {
                console.log(i);
                if (i !== 0) {
                    var td = $(this).children();
                    td.each(function(i) {
                        var name = $(this).attr("name");
                        if (name === undefined) {
                            return;
                        }
                        var val = $(this).html();
                        var partData = [ name, val ];
                        datas.push(partData);
                    })
                }
            });
            console.log(datas); //输出数据
        })
    }

    add();

    function add() {
        //新增考试，弹出新增窗口
        $("#addClassBtn").click(function () {
            var index = $("#class_table tr").length;
            var row = $("<tr>");
            row.append($("<td class=\"num\" name=\"num\">"+index+"</td>"))
                .append($("<td class=\"val\" name=\"className\">未添加</td>"))
                .append($("<td class=\"subject\" name=\"subjectName\" onclick=\"manageClassBoardPage.updateSubject()\">未添加</td>"))
                .append($("<td class=\"teacher\" name=\"teacherName\" onclick=\"manageClassBoardPage.updateTeacher()\">未添加</td>"))
                .append($("<td class=\"val\" name=\"classNum\">0</td>"))
                .append($("<td disabled=\"disabled\">                            <a href=\"javascript:;\" class=\"add\">\n" +
                    "                                <button type=\"button\" class=\"btn btn-primary btn-xs\"><i class=\"fa fa-plus\"></i>添加</button>\n" +
                    "                            </a>\n" +
                    "                            <a href=\"javascript:;\" class=\"del\">\n" +
                    "                                <button type=\"button\" class=\"btn btn-danger btn-xs\" ><i class=\"fa fa-trash\"></i>删除</button>\n" +
                    "                            </a></td>"));
            $("#class_table tbody").append(row);
        });
    }
});

var manageClassBoardPage = {
    data: {
        pageNum: 0,
        pageSize: 0,
        totalPageNum: 0,
        totalPageSize: 0,
        classes: []
    },
    teachers: [],
    subjects: [],
    init: function (pageNum, pageSize, totalPageNum, totalPageSize, classes, teachers, subjects) {
        manageClassBoardPage.data.pageNum = pageNum;
        manageClassBoardPage.data.pageSize = pageSize;
        manageClassBoardPage.data.totalPageNum = totalPageNum;
        manageClassBoardPage.data.totalPageSize = totalPageSize;
        manageClassBoardPage.data.classes = classes;
        manageClassBoardPage.teachers = teachers;
        manageClassBoardPage.subjects = subjects;
        //分页初始化
        manageClassBoardPage.subPageMenuInit();

    },
    firstPage: function () {
        window.location.href = app.URL.manageSubjectListUrl() + '?page=1';
    },
    prevPage: function () {
        window.location.href = app.URL.manageSubjectListUrl() + '?page=' + (pageNum - 1);
    },
    targetPage: function (page) {
        window.location.href = app.URL.manageSubjectListUrl() + '?page=' + page;
    },
    nextPage: function () {
        window.location.href = app.URL.manageSubjectListUrl() + '?page=' + (pageNum + 1);
    },
    lastPage: function () {
        window.location.href = app.URL.manageSubjectListUrl() + '?page=' + manageClassBoardPage.data.totalPageNum;
    },
    subPageMenuInit: function () {
        var subPageStr = '<ul class="pagination">';
        if (manageClassBoardPage.data.pageNum == 1) {
            subPageStr += '<li class="disabled"><a><span>首页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>上一页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageClassBoardPage.firstPage()"><span>首页</span></a></li>';
            subPageStr += '<li><a onclick="manageClassBoardPage.prevPage()"><span>上一页</span></a></li>';
        }
        var startPage = (manageClassBoardPage.data.pageNum - 4 > 0 ? manageClassBoardPage.data.pageNum - 4 : 1);
        var endPage = (startPage + 7 > manageClassBoardPage.data.totalPageNum ? manageClassBoardPage.data.totalPageNum : startPage + 7);
        console.log('startPage = ' + startPage);
        console.log('endPage = ' + endPage);
        console.log('pageNum = ' + manageClassBoardPage.data.pageNum);
        console.log('totalPageNum = ' + manageClassBoardPage.data.totalPageNum);
        for (var i = startPage; i <= endPage; i++) {
            if (i == manageClassBoardPage.data.pageNum) {
                subPageStr += '<li class="active"><a onclick="manageClassBoardPage.targetPage(' + i + ')">' + i + '</a></li>';
            } else {
                subPageStr += '<li><a onclick="manageClassBoardPage.targetPage(' + i + ')">' + i + '</a></li>';
            }
        }
        if (manageClassBoardPage.data.pageNum == manageClassBoardPage.data.totalPageNum) {
            subPageStr += '<li class="disabled"><a><span>下一页</span></a></li>';
            subPageStr += '<li class="disabled"><a><span>末页</span></a></li>';
        } else {
            subPageStr += '<li><a onclick="manageClassBoardPage.nextPage()"><span>下一页</span></a></li>';
            subPageStr += '<li><a onclick="manageClassBoardPage.lastPage()"><span>末页</span></a></li>';
        }
        $('#subPageHead').html(subPageStr);
    },
    updateTeacher: function () {
        $("body").on("dblclick", ".teacher", function () {
            var $this = $(this);
            var html = $this.html();
            if (manageClassBoardPage.teachers !== undefined) {
                var input = $("<select></select>");
                var newData = manageClassBoardPage.teachers;
                for (var i = 0; i < manageClassBoardPage.teachers.length; i++) {
                    console.log(newData[i].name);
                    if (html === newData[i].name) {
                        var option = $('<option selected>' + newData[i].name + '</option>');
                    } else {
                        var option = $('<option>' + newData[i].name + '</option>');
                    }
                    input.append(option);
                }
            } else {
                var input = $('<input type="text" value="' + html + '">');
            }
            $this.html(input);
            input.focus();
            input.blur(function () {
                var val = $(this).val();
                $this.html(val);
            })
        })
    },
    updateSubject: function () {
        $("body").on("dblclick", ".subject", function () {
            var $this = $(this);
            var html = $this.html();
            if (manageClassBoardPage.subjects !== undefined) {
                var input = $("<select></select>");
                var newData = manageClassBoardPage.subjects;
                for (var i = 0; i < manageClassBoardPage.subjects.length; i++) {
                    if (html === newData[i].name) {
                        console.log(html);
                        var option = $('<option selected>' + newData[i].name + '</option>');
                    } else {
                        var option = $('<option>' + newData[i].name + '</option>');
                    }
                    input.append(option);
                }
            } else {
                var input = $('<input type="text" value="' + html + '">');
            }
            $this.html(input);
            input.focus();
            input.blur(function () {
                var val = $(this).val();
                $this.html(val);
            })
        })
    },
    update: function () {
        $("body").on("dblclick", ".val", function () {
            var $this = $(this);
            var html = $this.html();

            var input = $('<input type="text" value="' + html + '">');

            $this.html(input);
            input.focus();
            input.blur(function () {
                var val = $(this).val();
                $this.html(val);
            })
        })
    },
    saveClass: function () {
        $("#save").on("click", function () {
            var datas = [];
            $("#table tr").each(function (i) {
                if (i === 0) {
                    return;
                } else {
                    var td = $(this).children();
                    td.each(function (i) {
                        var name = $(this).attr("name");
                        if (name === undefined) {
                            return;
                        }
                        var val = $(this).html();
                        datas.push(partData);
                    })
                }
            });
            console.log(datas); //输出数据
        })
    },
    classStudentAction: function (index) {
        window.location.href = app.URL.classStudentListUrl() + index;
    }

};
