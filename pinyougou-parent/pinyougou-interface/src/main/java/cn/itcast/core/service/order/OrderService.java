package cn.itcast.core.service.order;

import cn.itcast.core.pojo.order.Order;

/**
 * @author wophy
 */
public interface OrderService {
    void add(Order order,String username);
}
