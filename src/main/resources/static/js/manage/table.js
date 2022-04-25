$(function() {
    //创建行
    addRow();

    function addRow() {
        $('body').on("click", ".add", function() {
            var tr = $(this).parent().parent();
            var newtr = tr.clone();
            tr.after(newtr);
            //改变序数
            $(".num").each(function(i) {
                $(".num").eq(i).html(i + 1);
            })
        })

    }
    //删除行
    del();

    function del() {
        $("body").on("click", ".del", function() {
            var tr = $(this).parent().parent();
            tr.remove();
            //改变序数
            $(".num").each(function(i) {
                $(".num").eq(i).html(i + 1);
            })
        })
    }
    upData();

    function upData() {
        $("body").on("dblclick", ".val", function() {
            var $this = $(this);
            var html = $this.html();
            var divType = $this.attr("select");
            console.log(divType);
            if (divType != undefined) {
                var input = $("<select></select>");
                var newData = eval('(' + divType + ')');
                for (var i = 0; i < newData.length; i++) {
                    if (html == newData[i]) {
                        var option = $('<option selected>' + newData[i] + '</option>');
                    } else {
                        var option = $('<option>' + newData[i] + '</option>');
                    }

                    input.append(option);
                }
            } else {
                var input = $('<input type="text" value="' + html + '">');
            }
            $this.html(input);
            input.focus();
            input.blur(function() {
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
            $("#table tr").each(function(i) {
                if (i == 0) {
                    return ;
                } else {
                    var td = $(this).children();
                    td.each(function(i) {
                        var name = $(this).attr("name");
                        if (name == undefined) {
                            return;
                        }
                        var val = $(this).html();
                        datas.push(partData);
                    })
                }
            });
            console.log(datas); //输出数据
        })

    }
});