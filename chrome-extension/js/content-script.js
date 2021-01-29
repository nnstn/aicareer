$("h2.art-tt").after("<button id='collectbtn' style='background-color:yellow;border: none'>采集</button>");

if( $("#collectbtn").length ){

    $("#collectbtn").bind("click",function(){
        var title = $("h2.art-tt").html(); //帖子标题
        var author =$(".author a").html(); //帖子作者
        var publist_date =$(".publist-date").html();//发布日期
        var update_date =$(".update-date").html();//最后更新日期
        var url = window.location.href;
        var article_id = url.substring(url.lastIndexOf("/")+1)
        let param = {
            "articleId":article_id,
            "author":author,
            "articleTitle":title,
            "articleUrl":url,
            "publishDate":publist_date,
            "updateDate":update_date,
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
