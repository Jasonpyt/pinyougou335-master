app.controller("baseController",function($scope){
    // 定义一个数组:
    $scope.selectIds = [];
    // 更新复选框：
    $scope.updateSelection = function($event,id){
        // 复选框选中
        if($event.target.checked){
            // 向数组中添加元素
            $scope.selectIds.push(id);
        }else{
            // 从数组中移除
            var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx,1);
        }

    }
});
