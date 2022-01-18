// var aicareerUrl= "http://localhost:8090/extension/collect";
var aicareerUrl= "https://aicp.teamshub.com/aicareer/api/extension/collect";

// $("h2.art-tt").after("<button id='collectbtn' style='background-color:yellow;border: none'>采集</button>");
//
// if( $("#collectbtn").length ){
//
//     $("#collectbtn").bind("click",function(){
//         sendArticle();
//     });
// }

var oHtml = document.getElementsByTagName("html")[0];
oHtml.onkeydown = function(ev) {
    if(ev.ctrlKey  && ev.keyCode == 81) {
        sendArticle();
    }
}
function sendArticle() {
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
    if($(".art-affix1>.down").length>0){
        for(let i=0;i< $(".art-affix1>.down").length;i++) {
            let name = $(".art-affix1>.down").eq(i).text();
            let value = $(".art-affix1>.down").eq(i).attr("href");
            var attach_id = value.substring(value.lastIndexOf("/")+1)
            param["attachment"].push({"attachName":name,"attachUrl":value});
        }
    }
    asyncRequest(aicareerUrl,JSON.stringify(param)
        ,function (result) {
            alert(result.data);
        }
        ,function(xhrObj, txtStatus, errorThrown){
            alert(xhrObj);
        });
}