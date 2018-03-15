(function (jQuery) {
    $.confirm = function (confirmJson) {
        $("a").each(function () {
            if($(this).text()=="删除"){
                $(this).unbind("click");
                $(this).bind("click",function () {
                    return window.confirm(confirmJson.message);
                });
            }
        });
    }
})(jQuery);
