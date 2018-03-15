var tree = {
    zTree:'',
    pNode:'',
    setting : {
        isSimpleData: true,
        treeNodeKey: "mid",
        treeNodeParentKey: "pid",
        async : true,
        showLine: true,
        root:{
            isRoot:true,
            nodes:[]
        },
        callback:{
            /**
             *
             * @param event    鼠标事件
             * @param treeId   树的容器ID
             * @param treeNode 当前点击的节点
             */
            expand:function (event, treeId,treeNode) {
                tree.pNode = treeNode;
                tree.loadNodeByPNODE();
            }
        }
    },
    loadTree:function () {
        $.post("/api/getMenuList",null,function (data) {
            $("#tree").zTree(tree.setting,data);
        });

    },
    loadRootNode:function () {
        var parameter = {
            pid : 0
        };
        $.post("/api/getMenuListById",parameter,function (data) {
            tree.zTree = $("#tree").zTree(tree.setting,data);
        });
    },
    loadNodeByPNODE:function () {
        var parameter = {
            pid : tree.pNode.mid
        };
        if(!tree.zTree.getNodeByParam("pid",tree.pNode.mid)){
            $.post("/api/getMenuListById",parameter,function (data) {
                /*
                 把查询出来的子节点追加到父节点上
                 */
                tree.zTree.addNodes(tree.pNode,data,true);
            });
        }

    }
};
$().ready(function () {
    //tree.loadTree();  //一次性加载
    tree.loadRootNode();//点击加载
});