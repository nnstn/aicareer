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
        var article_id = url.substring(url.lastIndexOf("/")+1)
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
        // {
        //     "articleId":1121,
        //     "author":"wangjn_bj",
        //     "articleUrl":"11",
        //     "publishDate":"2021-01-29 15:20",
        //     "updateDate":"2021-01-29 15:20",
        //     "attachment":[
        //         {
        //             "attachId":1121,
        //             "attachName":"attachName",
        //             "attachUrl":"13123"
        //         }
        //     ]
        // }
        $.ajax({
            type : 'POST',
            url : "http://localhost:8090/extension/collect",
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
    console.log(ev.ctrlKey)
    console.log("键盘按钮："+ev.keyCode)
    if(ev.ctrlKey  && ev.keyCode == 81) {
        console.log("存储帖子组合件 CTRL+Q");
        $("#collectbtn").trigger("click");
    }
}