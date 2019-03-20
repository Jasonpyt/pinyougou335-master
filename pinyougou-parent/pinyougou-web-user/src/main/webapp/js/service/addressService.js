// 定义服务层:
app.service("addressService",function($http) {

    this.findOne=function(id){
        return $http.get("../address/findOne.do?id="+id);
    }
    this.updateDefaultAddress=function(id){
        return $http.get("../address/default.do?id="+id);
    }

    this.search = function(){
        return $http.post("../address/search.do");
    }
    this.delAddress = function(id){
        return $http.get("../address/delete.do?id="+id);
    }
    this.add = function(entity){
        return $http.post("../address/add.do",entity);
    }

    this.update=function(entity){
        return $http.post("../address/update.do",entity);
    }

});