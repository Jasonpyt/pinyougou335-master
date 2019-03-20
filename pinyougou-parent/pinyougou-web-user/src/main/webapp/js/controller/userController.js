//控制层
app.controller('userController', function ($scope, $controller, userService) {

  $scope.entity = {};
    $scope.lev = ["最强王者","青铜","白金","黄金"];
    $scope.sexList = ["男","女"];

//保存
    $scope.update = function () {
        userService.update($scope.entity).success(function (response) {
            if (response.flag) {
                alert(response.message);
                $scope.findByName();
            } else {
                alert(response.message);
            }
        });

    }

    // 查询一个:
    $scope.findByName = function (name) {
        userService.findOne(name).success(function (response) {
            // {id:xx,name:yy,firstChar:zz}
            $scope.entity = response;
        });
    }
// 头像上传:
   /* $scope.uploadFile = function () {
        // 调用uploadService的方法完成文件的上传
        uploadService.uploadFile().success(function (response) {
            if (response.flag) {
                // 获得url
                $scope.entity.headPic = response.message;
            } else {
                alert(response.message);
            }
        });
    }*/


    //注册用户
    $scope.reg = function () {

        //比较两次输入的密码是否一致
        if ($scope.password != $scope.entity.password) {
            alert("两次输入密码不一致，请重新输入");
            $scope.entity.password = "";
            $scope.password = "";
            return;
        }
        //新增
        userService.add($scope.entity, $scope.smscode).success(
            function (response) {
                alert(response.message);
            }
        );
    }

    //发送验证码
    $scope.sendCode = function () {
        if ($scope.entity.phone == null || $scope.entity.phone == "") {
            alert("请填写手机号码");
            return;
        }

        userService.sendCode($scope.entity.phone).success(
            function (response) {
                alert(response.message);
            }
        );
    }

});	
