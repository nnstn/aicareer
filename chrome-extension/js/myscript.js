if( $("#schdulebtn").length ){

  $("#schdulebtn").bind("click",function(){
      var title = $("h2.art-tt").html(); //帖子标题
      var author =$(".author a").html(); //帖子作者
      var publist_date =$(".publist-date").html();//发布日期
      var update_date =$(".update-date").html();//最后更新日期
      var url = window.location.href;
    let param = {};
    $.ajax({
      type : 'POST',
      url : "http://localhost:9016/generate/orderid",
      async : false,
      data:param,
      dataType : 'json',
      traditional:true,
      timeout:120000,
      success : function(result,status,resp) {
        alert(result.data);
      },
      complete:function(){

      },
      error: function(xhrObj, txtStatus, errorThrown) {
        serviceError(xhrObj, txtStatus, errorThrown);
      }
    });
  });
}

if(localStorage['c'] ){
  if(localStorage['c']>0 ){
    $("#disconnectbtn").attr("disabled",false);
      $("#connectbtn").attr("disabled",true);
    }else{
      $("#disconnectbtn").attr("disabled",true);
      $("#connectbtn").attr("disabled",false);
    }

}

//chrome.runtime.onStartup.addListener(function(){ init();});
// bind event for connectbtn
if( $("#connectbtn").length ){
  $("#connectbtn").bind("click",function(){
    connect();
  }
);



}


if( $("#tmpserver").length ){
  $("#tmpserver").bind("select",function(){
    connect();
  }
    );


}


if( $("#disconnectbtn").length ){
  $("#disconnectbtn").bind("click",function(){
    removeProxy();
  }
    );

}





function removeProxy(){
  localStorage['c']=0;

var config = {
  mode: "system"
};

chrome.proxy.settings.set(
    {value: config, scope: 'regular'},
    function() {});
      $("#disconnectbtn").attr("disabled",true);
      $("#connectbtn").attr("disabled",false);


}



function connect(){
  localStorage['c']=1;
httpsserver = document.getElementById("tmpserver").value;
 var config = {
            mode: "pac_script",
            pacScript: {
              data: "function dnsDomainIs(host, pattern) {return host.length >= pattern.length && (host === pattern || host.substring(host.length - pattern.length - 1) === '.' + pattern);};\n"+
            "function FindProxyForURL(url, host) {\n"+
            "var PROXY = 'HTTPS  "+httpsserver+"'\n"+
           " return PROXY;\n"+
          "}\n"
            }
          };
          $("#connectbtn").attr("disabled",true);
          $("#disconnectbtn").attr("disabled",false);

      chrome.proxy.settings.set( {value: config, scope: 'regular'},function() {

      });
}