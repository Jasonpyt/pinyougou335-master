//服务层
app.service("collectService",function ($http) {
    //展示该用户的收藏
    this.showCollect=function () {
        return $http.post("/collect/showCollect.do");
    }
});