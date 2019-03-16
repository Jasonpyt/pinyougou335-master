package cn.itcast.core.service.order;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.order.Order;
import entity.PageResult;

/**
 * @author wophy
 */
public interface OrderService {
    void add(Order order,String username);

    /**
     * 订单查询
     * @param page
     * @param rows
     * @param order
     * @return
     */
    public PageResult search(Integer page, Integer rows, Order order);

    Order findOneById(Long orderId);
}
