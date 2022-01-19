// let baseurl = "https://aicp.teamshub.com/aicareer/api";
let baseurl = "http://192.168.15.60/aicareer/api";

$(document).ready(function() {
    //默认active
    $(".tab_content").hide(); //隐藏全部的tab菜单内容
    $("ul.tabs li:first").addClass("active").show(); //对第一个li标签添加class="active属性"
    $(".tab_content:first").show(); //显示第一个tab内容

    //点击事件
    $("ul.tabs li").click(function() {
        $("ul.tabs li").removeClass("active"); //移除class="active"属性
        $(this).addClass("active"); //添加class="active"到选择标签中
        $(".tab_content").hide(); //隐藏全部标签内容
        var activeTab = $(this).find("a").attr("href"); //找到所属属性值来识别活跃选项卡和内容
        $(activeTab).show(); //使内容消失
        return false;
    });
    $(".switch").click(function(){

        //alert("kskk")
       // e.stopPropagation();
    })

    todolist();

    $("#fresh").click(function () {
        todolist();
    });

    // bind event for connectbtn
    if( $("#login").length ){
        console.log(window.location.href);
        $("#login").click(function () {
            $("#username").val($("#username").val().trim()) ;
            $("#password").val($("#password").val().trim()) ;
            let param = {"userCode": $("#username").val(), "password": $("#password").val()};
            asyncRequest(baseurl+"/auth/login",JSON.stringify(param),
                function (result) {
                    console.log("登录成功");
                    console.log(result);
                    saveChanges("username");
                    saveChanges("password");
                    saveChanges("token");
                }
                ,function(xhrObj, txtStatus, errorThrown){
                    console.log(xhrObj);
                });


        });
    }
    init();

});

function todolist() {
    let param = {};
    asyncRequest(baseurl+"/task/getall",JSON.stringify(param)
        ,function (result) {
            $("#tab1 table tbody").html();
        }
        ,function(xhrObj, txtStatus, errorThrown){
            console.log(xhrObj);
        });


}
function init(){
    if( localStorage.getItem("username") != null  ){
        setlocalStorageToValue("username");
        setlocalStorageToValue("password");
        setlocalStorageToValue("token");
    }
}

