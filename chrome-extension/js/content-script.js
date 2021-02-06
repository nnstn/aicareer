// var aicareerUrl= "http://localhost:8090/extension/collect";
var aicareerUrl= "http://aicp.teamshub.com/aicareer/extension/collect";

$("h2.art-tt").after("<button id='collectbtn' style='background-color:yellow;border: none'>采集</button>");

if( $("#collectbtn").length ){

    $("#collectbtn").bind("click",function(){
        var title = $("h2.art-tt").html(); //帖子标题
        var author =$(".author a").html(); //帖子作者
        var publist_date =$(".publist-date").html();//发布日期
        var update_date =$(".update-date").html();//最后更新日期

        var communityName =$(".home").text();//圈子名称
        var communityUrl =$(".home").attr("href");//圈子地址
        var teamName =$(".home").next().text();//分组名称
        var teamUrl =$(".home").next().attr("href");//分组地址
        var url = window.location.href;
        url= url.indexOf("?")==-1?url:url.substring(0,url.indexOf("?"));
        var article_id = url.substring(url.lastIndexOf("/")+1);
        //var article_id = url.substring(url.lastIndexOf("/")+1,url.indexOf("?")==-1?url.length:url.indexOf("?"));
        let param = {
            "articleId":article_id,
            "author":author,
            "articleTitle":title,
            "articleUrl":url,
            "publishDate":publist_date,
            "updateDate":update_date,
            "communityName":communityName,
            "communityUrl":communityUrl,
            "teamName":teamName,
            "teamUrl":teamUrl,
            "attachment":[]
        };
        if($(".art-affix1>.down").size()>0){
            for(let i=0;i< $(".art-affix1>.down").size();i++) {
                let name = $(".art-affix1>.down").eq(i).text();
                let value = $(".art-affix1>.down").eq(i).attr("href");
                var attach_id = value.substring(value.lastIndexOf("/")+1)
                param["attachment"].push({"attachName":name,"attachUrl":value});
            }
        }
        $.ajax({
            type : 'POST',
            url : aicareerUrl,
            async : false,
            data:JSON.stringify(param),
            dataType : 'json',
            contentType: "application/json",
            traditional:true,
            timeout:120000,
            success : function(result,status,resp) {
                alert(result.data);
            },
            complete:function(){

            },
            error: function(xhrObj, txtStatus, errorThrown) {
                //serviceError(xhrObj, txtStatus, errorThrown);
            }
        });
    });
}

var oHtml = document.getElementsByTagName("html")[0];
oHtml.onkeydown = function(ev) {
    if(ev.ctrlKey  && ev.keyCode == 81) {
        $("#collectbtn").trigger("click");
    }
}

function ShowTip(tip, type) {
    var $tip = $('#tip');
    if ($tip.length == 0) {
        // 设置样式，也可以定义在css文件中
        $tip = $('<span id="tip" style="transform:translate(-50%,-50%);position:fixed;top:50%;left: 50%;z-index:9999;height:28px; padding: 2px 16px;line-height: 28px;color:#fff;background:rgba(0,0,0,0.7);font-size:12px;border-radius:4px;"></span>');
        $('body').append($tip);
    }
    $tip.stop(true).prop('class', 'alert alert-' + type).text(tip).fadeIn(500).delay(1000).fadeOut(500);
}
// 正在加载中
//loading.ShowLoading(); 显示   loading.CloseLoading();隐藏
var loading={
    loadingHtml:'<div class="loading" style="display: flex;align-items: center; transform:translate(-50%,-50%);position:fixed;top:50%;left: 50%;z-index:9999;height:28px; padding: 2px 16px;line-height: 28px;color:#fff;background:rgba(0,0,0,0.7);font-size:12px;border-radius:4px;"><img style="width: 14px;height: 14px;margin-right: 6px;" src="img/loading.gif" />正在加载中</div>',
    ShowLoading:function(){
        $('body').append(loading.loadingHtml);
    },
    CloseLoading:function(){
        $('body .loading').remove();
    }
}