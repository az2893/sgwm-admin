$(function() {
	common.showMessage($("#message").val());
});

function search() {
    //search  查找

      var  param=$("#title").val();
        $.ajax({
            type: 'get',
            url: "/ad/search/?name="+param,
            dataType: "json",
            success: function (data) {
                var tbody=window.document.getElementById("tab");
                <!-- 清空表格-->
                $("#tab   tr:not(:first)").html("");
                var str = "";
                for (var i in data){
                    str+="<tr>"+
                        "<td>"+parseInt(parseInt(i)+parseInt(1))+"</td>"+
                        "<td>"+data[i].title+"</td>"+
                        " <td>"+data[i].link+"</td>"+
                        "<td>"+
                        "<a href='javascript:void(0);'onclick='modifyInit('"+data[i].id+"')'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
                        "<a href='javascript:void(0);' onclick='remove('"+data[i].id+"')'>删除</a>"+
                        "</td>"+
                        "</tr>"
                }
                tbody.innerHTML += str;
            },
            error:function (data) {

            }
        });

}

function remove(id) {
        $.ajax({
            type: 'get',
            url: "/ad/delete/"+id,
            dataType: "json",
            success: function (data) {
                alert("删除成功");
                location.href="/ad/adlist/1"
            },
            error:function (data) {
				alert("删除失败");
            }
        });
}

function modifyInit(id) {
	$("#id").val(id);
	$("#mainForm").attr("action",$("#basePath").val() + "/ad/modifyInit");
	$("#mainForm").submit();
}