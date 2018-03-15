var common = {
    myconfig:function (message) {
        return window.confirm(message);
    }
};

$().ready(function () {
    /**
     * 当页面进行加载的时候，给名称删除的超级链接添加一个事件
     */
    // $("a").each(function () {
    //     if($(this).text()=="删除"){
    //         $(this).unbind("click");
    //         $(this).bind("click",function () {
    //             return common.myconfig("您确认删除吗?")
    //         });
    //     }
    //
    // });
    $.confirm({
        message:'您确认删除吗?'
    });
});
