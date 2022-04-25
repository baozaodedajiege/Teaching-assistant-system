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
        $("#save").on("click", function () {
            var datas = [];
            $("#class_table tr").each(function (i) {
                console.log(i);
                if (i !== 0) {
                    var td = $(this).children();
                    td.each(function (i) {
                        var name = $(this).attr("name");
                        if (name === undefined) {
                            return;
                        }
                        var val = $(this).html();
                        var partData = [name, val];
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
        $("#addStudentBtn").click(function () {
            var index = $("#student_table tr").length;
            var row = $("<tr>");
            row.append($("<td class=\"num\">" + index + "</td>"))
                .append($("<td><img src='/upload/images/headimg_placeholder.png' style=\"width: 32px;height: 32px;\" class=\"img-circle\" /></td>"))
                .append($("<td class=\"val\" >未添加</td>"))
                .append($("<td class=\"val\" >未添加</td>"))
                .append($("<td class=\"val\" >未添加</td>"))
                .append($("<td class=\"val\" >未添加</td>"))
                .append($("<td class=\"val\" >未添加</td>"))
                .append($("                        <td disabled=\"disabled\">\n" +
                    "                            <a href=\"javascript:;\" class=\"add\">\n" +
                    "                                <button type=\"button\" class=\"btn btn-primary btn-xs\"><i class=\"fa fa-plus\"></i>添加</button>\n" +
                    "                            </a>\n" +
                    "                            <a href=\"javascript:;\" class=\"del\">\n" +
                    "                                <button type=\"button\" class=\"btn btn-danger btn-xs\" ><i class=\"fa fa-trash\"></i>删除</button>\n" +
                    "                            </a>\n" +
                    "                        </td>"));
            console.log(row);
            $("#student_table tbody").append(row);
        });
    }
    
    importStudent();
    
    function importStudent() {
        $("#importBtn").click(function () {
            alert("功能暂未实现");
        })
    }
});