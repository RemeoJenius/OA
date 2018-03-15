/**
 * Created by liyongjun on 17/2/12.
 */
var privilege = {
    /**
     * 所有初始化操作
     */
    init:{
        /**
         * 所有的初始化事件
         */
        initEvent:function () {
            /**
             * 设置权限的click事件
             */
            $("a").each(function(){
                if ($(this).text() == "设置权限") {
                    $(this).unbind("click");
                    $(this).bind("click", function(){
                        /**
                         * 1、显示所有的div
                         * 2、动态的显示用户名
                         * 3、加载权限树
                         */
                        //初始化数据
                        //privilege.init.initData(hobj);
                        //initDate函数的调用这就是当前对象
                        privilege.init.pFunction.divOption.showDiv();//显示所有的div
                        privilege.init.pFunction.userOption.showUsername();//动态的显示用户名
                        privilege.init.pFunction.privilegeTree.loadPrivilegeTree();//加载权限树
                        return false;
                    });
                }
            });
            /**
             * 全选按钮的事件
             */
            $("#allchecked").unbind("click");
            $("#allchecked").bind("click",function () {
                privilege.init.pFunction.privilegeTree.checkAll.call(this);
            });
            /**
             * 保存权限的事件
             */
            $("#savePrivilege").unbind("click");
            $("#savePrivilege").bind("click",function () {
                privilege.init.pFunction.privilegeTree.savePrivilege();
            });
        },
        /**
         * 初始化数据
         */
        initData:function (username,uid) {
            privilege.init.data.user.username = username;
            privilege.init.data.user.uid = uid;

        },
        /**
         * 按照功能划分区域
         */
        pFunction:{
            // 所有的权限树的操作
            privilegeTree:{
                zTree:'',
                /**
                 * 树的配置
                 */
                setting:{
                    isSimpleData: true,
                    treeNodeKey: "mid",
                    treeNodeParentKey: "pid",
                    async : true,
                    showLine: true,
                    root: {
                        isRoot: true,
                        nodes: []
                    },
                    checkable:true,
                     checkType:{
                         "Y":"p",
                         "N":"s"

                     },
                    callback:{
                        /**
                         * 在点击树上的复选框之前触发该方法
                         * @param treeId
                         * @param treeNode
                         */
                        beforeChange:function (treeId,treeNode) {
                            privilege.init.pFunction.privilegeTree.controlCheckBox({
                                "Y":"p",
                                "N":"s"

                            });
                        },
                        change:function (treeId,treeNode) {
                            if(privilege.init.pFunction.privilegeTree.zTree.getCheckedNodes(false).length!=0){ // 获取到没有选中的
                                $("#allchecked").prop("checked",false);
                            }else{
                                $("#allchecked").prop("checked",true);
                            }
                        }
                    }
                },
                /**
                 * 显示权限树
                 */
                loadPrivilegeTree:function () {
                    var parameter = {
                        uid:privilege.init.data.user.uid
                    };
                    $.post("/api/getMenuByUId/"+parameter.uid,null,function (data) {
                       privilege.init.pFunction.privilegeTree.zTree =  $("#privilegeTree").zTree(privilege.init.pFunction.privilegeTree.setting,data);
                        /**
                         * 这里是设置全选按钮默认状态的最佳位置
                         *    默认值必须z在点击设置全选的超级链接中设置
                         *    确保zTree必须有值
                         */
                        if(privilege.init.pFunction.privilegeTree.zTree.getCheckedNodes(false)){ // 获取到没有选中的
                            $("#allchecked").prop("checked",false);
                        }else{
                            $("#allchecked").prop("checked",true);
                        }

                    });

                },
                /**
                 * 对权限树复选框的控制
                 */
                controlCheckBox:function (checkType) {
                    var setting = privilege.init.pFunction.privilegeTree.zTree.getSetting();
                    setting.checkType = checkType;
                    privilege.init.pFunction.privilegeTree.zTree.updateSetting(setting);

                },
                /**
                 * 针对某一用户保存权限
                 */
                savePrivilege:function () {
                    var checkedNodes = privilege.init.pFunction.privilegeTree.zTree.getCheckedNodes(true);
                    var mids = "";
                    for (var i =0;i<checkedNodes.length;i++){
                        if(i<checkedNodes.length-1){
                            mids = mids+checkedNodes[i].mid+",";
                        }else{
                            mids = mids+checkedNodes[i].mid;
                        }
                    }


                    var parameter = {
                        uid:privilege.init.data.user.uid,
                        mids:mids

                    };
                    $.post("/api/savePrivilegeMid",parameter,function (data) {
                        alert("保存成功!");
                        location.reload();
                    });
                },
                /**
                 * 全选复选框的实现
                 */
                checkAll:function () {
                    /**
                     * 改变树上的复选框的选中规则
                     * 在执行函数的时候，zTree已经存在
                     */
                    /**
                     * 改变zTre中复选框的规则
                     */
                    privilege.init.pFunction.privilegeTree.controlCheckBox({
                        "Y":"ps",
                        "N":"ps"
                    });
                    if($(this).prop("checked")){ // 全选
                        privilege.init.pFunction.privilegeTree.zTree.checkAllNodes(true);
                    }else { // 全不选
                        privilege.init.pFunction.privilegeTree.zTree.checkAllNodes(false);
                    }

                }
            },
            userOption:{// 存放用户的操作
                showUsername:function () {// 把用户名动态的显示到div中

                }
            },
            divOption:{// 显示div的操作
                showDiv:function () {
                    $("#userTitle").show();
                    $("#privilegeTitle").show();
                    $("#privilegeContent").show();
                }

            }

        },
        /**
         * json对象对数据的封装
         */
        data:{// 所有数据封装
            user : {// 存放用户的数据
               uid:'',
                username:''
            }
        }
    }
};
$().ready(function () {
    privilege.init.initEvent();
});
/**
 * 全选按钮的状态的设置：
 * 在点击完成设置权限的超级链接时，全选按钮的默认状态已经确定了
 *     必须在事件中做这些事情
 * 在全选按钮设置默认值之前，必须保证权限树已经生成了
 */
