package cn.itcast.core.controller.goods;

import cn.itcast.core.pojo.good.Goods;

import cn.itcast.core.service.goods.GoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    /**
     * 运营商商品审核列表查询
     * @param page
     * @param rows
     * @param goods
     * @return
     */
    @RequestMapping("/search.do")
    public PageResult search(Integer page, Integer rows, @RequestBody Goods goods) {
        return goodsService.searchForManager(page,rows,goods);
    }

    /**
     * 运营商审核商品
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus.do")
    public Result updateStatus(Long[] ids,String status){
        try {
            goodsService.updateStatus(ids,status);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }
    @RequestMapping("/delete.do")
    public Result delete(Long[] ids){
        try {
            goodsService.delete(ids);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");

        }
    }


}
