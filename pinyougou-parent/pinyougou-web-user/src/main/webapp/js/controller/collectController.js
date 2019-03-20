app.controller("collectController",function ($scope,collectService) {
    //显示用户的收藏商品
    $scope.showCollect=function () {
        collectService.showCollect().success(function (response) {
            $scope.list=response;
        });
    }

    //添加商品到购物车
    $scope.addToCart=function(id,num){
        //alert('SKUID:'+$scope.sku.id );

        $http.get('http://localhost:9103/cart/addGoodsToCartList.do?itemId='
            +id+'&num='+num ,{'withCredentials':true} ).success(
            function(response){
                if(response.flag){
                    alert(response.message);
                    location.href='http://localhost:9103/cart.html';
                }else{
                    alert(response.message);
                }
            }
        );

    }
});
