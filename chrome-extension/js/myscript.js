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
        $(activeTab).fadeIn(); //使内容消失
        return false;
    });
});