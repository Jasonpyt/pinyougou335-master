//控制层
app.controller('addressController' ,function($scope,$controller,addressService) {

    /*$controller('baseController', {$scope: $scope});//继承*/

    // 查询一个:
    $scope.findById = function(id){
        addressService.findOne(id).success(function(response){
            // {id:xx,name:yy,firstChar:zz}
            $scope.entity = response;
        });
    }


    // 显示状态

    $scope.updateDefaultAddress = function(id){
        addressService.updateDefaultAddress(id).success(function(response){
            // {id:xx,name:yy,firstChar:zz}
            // 判断保存是否成功:
            if (response.flag == true) {
                // 保存成功
                alert(response.message);
                $scope.search();

            } else {
                // 保存失败
                alert(response.message);
            }
        });
    }



    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(){
        // 向后台发送请求获取数据:
        addressService.search().success(function(response){
            $scope.list = response;
        });
    }

    // 删除品牌:
    $scope.delAddress = function(id) {

            addressService.delAddress(id).success(function (response) {
                // 判断保存是否成功:
                if (response.flag == true) {
                    // 保存成功
                    // alert(response.message);
                    $scope.search();

                } else {
                    // 保存失败
                    alert(response.message);
                }
            });
        }


    // 保存品牌的方法:
    $scope.save = function(){
        // 区分是保存还是修改
        var object;
        if($scope.entity.id != null){
            // 更新
            object = addressService.update($scope.entity);
        }else{
            // 保存
            object = addressService.add($scope.entity);
        }
        object.success(function(response){
            // {flag:true,message:xxx}
            // 判断保存是否成功:
            if(response.flag){
                // 保存成功
                alert(response.message);
                $scope.search();
            }else{
                // 保存失败
                alert(response.message);
            }
        });
    }

});