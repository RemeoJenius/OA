/**
 * Created by liyongjun on 17/2/14.
 */
var menu = {
    setting:{
        isSimpleData: true,
        treeNodeKey: "mid",
        treeNodeParentKey: "pid",
        async : true,
        showLine: true,
        root:{
            isRoot:true,
            nodes:[]
        }
    },
    loadMenuTree:function () {
        $.post("/api/getLeftMenuByUid",null,function (data) {
            $("#menuTree").zTree(menu.setting,data);
        });
    }
};
$().ready(function () {
   menu.loadMenuTree();
});