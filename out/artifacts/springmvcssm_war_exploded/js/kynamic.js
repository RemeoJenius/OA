/**
 * Created by liyongjun on 17/2/16.
 */
var kynamic = {
    /**
     * 凡是树的操作
     */
  kynamicTree:{
        pNode:'',
        zTree:'',
        setting:{
            isSimpleData: true,
            treeNodeKey: "kid",
            treeNodeParentKey: "pid",
            async : true,
            keepParent:true,
            showLine: true,
            root:{
                isRoot:true,
                nodes:[]
            },
            callback:{
                rightClick:function (event, treeId, treeNode) {
                    /**
                     * 在点击右键的时候把treeNode赋值给pNode
                     */
                    kynamic.kynamicTree.pNode = treeNode;
                    /**
                     * 判断点击的是文件夹节点还是文件节点
                     */
                    if(treeNode.isParent){ //文件夹节点
                        kynamic.kynamicTree.controlRMenu({
                            x:event.clientX,
                            y:event.clientY,
                            addFile:true,
                            addFolder:true,
                            deleteNode:true,
                            updateNode:true
                        })
                    }else{ // 文件节点
                        kynamic.kynamicTree.controlRMenu({
                            x:event.clientX,
                            y:event.clientY,
                            addFile:false,
                            addFolder:false,
                            deleteNode:true,
                            updateNode:true
                        })
                    }


                },
                /**
                 * kynhamic节点的点击事件
                 */
                click:function (event, treeId, treeNode) {
                    /**
                     * 因为在单击的时候，右键事件不一定发生，所以得重新给pNode赋值
                     */
                    kynamic.kynamicTree.pNode = treeNode;
                    var parameter = {
                        kid:kynamic.kynamicTree.pNode.kid
                    };
                    $.post("/api/getVersionByKid",parameter,function (data) {
                        if(data.length==0){ // 没有版本
                            kynamic.version.controlShowVersion({
                                addVersion:true,
                                versionList:false,
                                checkin:true,
                                checkout:true


                            });
                            alert(kynamic.kynamicTree.pNode.kid);
                        }else{
                            /**
                             * 控制div和checkin和checkout按钮的显示
                             */
                            kynamic.version.controlShowVersion({
                                addVersion:false,
                                versionList:true,
                                checkin:false,
                                checkout:false

                            });
                            kynamic.version.showVersionByKid(data);

                        }

                    });

                }
            }
        },
        loadKynamicTree:function () {
            $.post("/api/showKynamicTree",null,function (data) {
                kynamic.kynamicTree.zTree = $('#kynamicTree').zTree(kynamic.kynamicTree.setting,data);
            });
        },
        /**
         * 控制右键菜单的显示
         *   div的位置
         *   右键菜单的菜单项
         */
        controlRMenu:function (rMenuJson) {
            /**
             * 显示菜单项的显示逻辑
             */


            /**
             * 增加文件的显示控制
             */
            $("#showRMenu").show();
            $("#rMenu").css({"top":rMenuJson.y+"px","left":rMenuJson.x+"px","display":"block"});
            if (rMenuJson.addFile){
                $("#addFile").show();
            }else{
                $("#addFile").hide();
            }
            if (rMenuJson.addFolder){
                $("#addFolder").show();
            }else{
                $("#addFolder").hide();
            }
            if (rMenuJson.deleteNode){
                $("#deleteNode").show();
            }else{
                $("#deleteNode").hide();
            }
            if (rMenuJson.updateNode){
                $("#updateNode").show();
            }else{
                $("#updateNode").hide();
            }

        },
        /**
         * 增加节点
         */
        addNode:function (addJson) {

            var fileName = window.prompt(addJson.fileMessage);
            if(fileName!=null){ // fileName不为null
                if(fileName!=""){
                    // 检查是否有重名的现象
                    $.post("/api/exsitName",{name:fileName},function (data) {
                        if(data.message=="1"){
                            alert("文件重名，请重新输入！");
                        }else{
                            var parameter ={
                                name:fileName,
                                isParent:addJson.isParent,
                                pid:kynamic.kynamicTree.pNode.kid
                            };
                            $.post("/api/saveKynamic",parameter,function (data) {
                                var kid = data.kid;
                                var newNode= {
                                    kid:kid,
                                    pid:kynamic.kynamicTree.pNode.kid,
                                    isParent:addJson.isParent,
                                    name:fileName
                                };
                                kynamic.kynamicTree.zTree.addNodes(kynamic.kynamicTree.pNode, newNode, true);
                                console.log(kynamic.kynamicTree.pNode);
                            });
                        }
                    });
                }else {
                    alert(addJson.errorMessage);
                }

            }
        },
        /**
         * 增加文件
         */
        addFile:function () {
            kynamic.kynamicTree.addNode({
                fileMessage:'请输入文件的名称！',
                errorMessage:'文件的名称不能为空!',
                isParent:false
            })
        },
        /**
         * 增加文件夹
         */
        addFolder:function () {
            /**
             * 1.在kynamic表中增加一行数据
             * 2.在指定的父节点下增加一个子节点
             */
            kynamic.kynamicTree.addNode({
                fileMessage:'请输入文件夹的名称！',
                errorMessage:'文件夹的名称不能为空!',
                isParent:true
            })
        },
        /**
         * 删除节点
         */
        deleteNode:function () {
            /**
             * 1.判断当前删除的节点是否是文件节点
             *      是  直接删除
             *      否  判断文件夹节点是否有子节点如果没有删除，如果有，提示不能删除
             */
            if(kynamic.kynamicTree.pNode.isParent){ // 文件夹节点
                if(kynamic.kynamicTree.zTree.getNodeByParam("pid",kynamic.kynamicTree.pNode.kid)){
                    alert("有子节点不能删除！");
                }else{
                    if(window.confirm("您确认要删除吗？")){
                        var parameter = {
                            kid: kynamic.kynamicTree.pNode.kid
                        };
                        console.log(kynamic);
                        $.post("/api/deleteKynamic",parameter,function (data) {
                            kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
                            alert(data.message);
                        });
                    }
                }
            }else{ // 文件节点
                if(window.confirm("您确认要删除吗？")){
                    var parameter = {
                        kid: kynamic.kynamicTree.pNode.kid
                    };
                    console.log(kynamic);
                    $.post("/api/deleteKynamic",parameter,function (data) {
                        kynamic.kynamicTree.zTree.removeNode(kynamic.kynamicTree.pNode);
                        alert(data.message);
                    });
                }

            }
        },
        /**
         * 修改节点
         */
        updateNode:function () {
            var newName = window.prompt("请输入新的名称",kynamic.kynamicTree.pNode.name);
            var parameter = {
                kid:kynamic.kynamicTree.pNode.kid,
                name:newName
            };
            $.post("/api/exsitName",{name:newName},function (data1) {
                if(data1.message=="1"){
                    alert("文件重名，请重新输入！");
                }else{ // 进行修改
                    $.post("/api/updateKynamic",parameter,function (data2){
                        kynamic.kynamicTree.pNode.name = newName;
                        kynamic.kynamicTree.zTree.refresh();

                    });
                }
            });
        }
  },
    /**
     * 版本的维护
     */
    version:{
        /**
         * 如果当前点击的节点有版本，则显示版本
         */
        showVersionByKid:function (versionList) {
            $("#showVersion").empty();
            for (var i = 0; i < versionList.length; i++) {
                var version = versionList[i].version;
                var date = new Date(versionList[i].updateTime);
                Y = date.getFullYear() + '-';
                M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                D = date.getDate() + ' ';
                console.log(Y+M+D);
                var updatetime = Y+M+D;
                var $versionA = $("<a/>");
                $versionA.text(version);
                $versionA.css("cursor", "pointer");
                $versionA.attr("version",versionList[i].version);
                $versionA.attr("updatetime",updatetime);

                $versionA.unbind("click");
                /**
                 * click事件的函数是在单击的时候触发的，这段代码所在的函数showVersionsByKID，而这个函数在回调函数中
                 *    versionList是传递过来的形参，所以回调函数的声明周期结束以后，该参数就不存在了,所以
                 *       versinList[i] is undefined
                 *
                 * 如果在$.post或者$.ajax中，回调函数中，调用了一个函数，而该函数中又有事件，那么事件中不能直接使用回调函数的形参
                 *    因为事件执行的时候，回调函数已经执行完毕了，形参已经不存在了
                 */
                $versionA.bind("click", function(){
                    //alert(versionList[i].version);
                    //alert(version);
                    alert($(this).attr("version"));
                });

                var $versionTD = $("<td/>");
                $versionTD.attr("height", "26");
                $versionTD.attr("align", "center");
                $versionTD.attr("valign", "middle");
                $versionTD.attr("bgcolor", "#FFFFFF");
                $versionTD.attr("style", "border-bottom:1px solid #f3f8fd;");
                $versionTD.append($versionA);

                var $updatetimeTD = $("<td/>");
                $updatetimeTD.attr("align", "center");
                $updatetimeTD.attr("valign", "middle");
                $updatetimeTD.attr("bgcolor", "#FFFFFF");
                $updatetimeTD.attr("style", "border-bottom:1px solid #f3f8fd;");
                $updatetimeTD.text(updatetime);

                var $A = $("<a/>");
                $A.text("删除");
                var $delTD = $("<td/>");
                $delTD.attr("align", "center");
                $delTD.attr("valign", "middle");
                $delTD.attr("bgcolor", "#FFFFFF");
                $delTD.attr("style", "border-bottom:1px solid #f3f8fd;");
                $delTD.append($A);

                var $TR = $("<tr/>");
                $TR.append($versionTD);
                $TR.append($updatetimeTD);
                $TR.append($delTD);
                $("#showVersion").append($TR);

            };

        },
        /**
         * 控制两个div checkin,checkout的显示
         */
        controlShowVersion:function (versionShowJson) {
            console.log(versionShowJson);
            if(versionShowJson.addVersion){
                $('#addVersion').show();
            }else{
                $('#addVersion').hide();
            }
            if(versionShowJson.versionList){
                $('#versionList').show();
            }else{
                $('#versionList').hide();
            }
            if(versionShowJson.checkin){
                $('#checkin').show();
            }else{
                $('#checkin').hide();
            }
            if(versionShowJson.checkout){
                $('#checkout').show();
            }else{
                $('#checkout').hide();
            }
        }
    }
};
$().ready(function () {
   kynamic.kynamicTree.loadKynamicTree();
    /**
     * hover在这里仅仅是声明事件，事件函数中内容到底是否执行，根据触发的时候判断
     */
   $("#rMenu").hover(function () {
       /**
        * 声明增、删、改的事件
        */
       $('#addFile').unbind("click");
       $('#addFile').bind("click",function () {
           kynamic.kynamicTree.addFile();
       });
       $('#addFolder').unbind("click");
       $('#addFolder').bind("click",function () {
           kynamic.kynamicTree.addFolder();
       });
       $('#updateNode').unbind("click");
       $('#updateNode').bind("click",function () {
           kynamic.kynamicTree.updateNode();
       });
       $('#deleteNode').unbind("click");
       $('#deleteNode').bind("click",function () {
           kynamic.kynamicTree.deleteNode();
       });
   },function () {
       //当该方法被触发的时候，树早已经存在了，右键菜单已经显示了
       $('#rMenu').hide();
   })
});