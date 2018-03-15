(function(jQuery){
	$.fn.controlCheckBox = function(checkname){
		/**
		 * 判断全选按钮的选中状态
		 */


		if($(this).prop('checked')){
            $("input[name='"+checkname+"']").prop("checked",true);
		}else{
            $("input[name='"+checkname+"']").prop("checked",false);
		}
	}
})(jQuery);
