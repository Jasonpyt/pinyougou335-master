package cn.itcast.core.service.order;

import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.cart.Cart;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.utils.uniquekey.IdWorker;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.RedisTemplate;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderItemDao orderItemDao;
    @Resource
    private ItemDao itemDao;
    @Resource
    private IdWorker idWorker;
    @Resource
    private PayLogDao payLogDao;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void add(Order order, String username) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("BUYER_CART").get(username);
        Double payLogTotalFee = 0d;
        ArrayList<String> orderIdList = new ArrayList<>();
        //保存订单 根据商家分类 保存到订单表
        if (cartList != null && cartList.size() > 0) {
            for (Cart cart : cartList) {
                double payment=0;
                long orderId = idWorker.nextId();
                orderIdList.add(orderId+"");
                //封装主键
                order.setOrderId(orderId);
                //设置付款状态
                order.setStatus("1");
                //订单创建时间
                order.setCreateTime(new Date());
                //订单更新时间
                order.setUpdateTime(new Date());
                //下单用户 ()
                order.setUserId(username);
                //订单来源
                order.setSourceType("2");

                //设置金额


                //保存订单明细
                //保存每一个购物项
                List<OrderItem> orderItemList = cart.getOrderItemList();
                for (OrderItem orderItem : orderItemList) {
                    //设置商品的id
                    orderItem.setId(idWorker.nextId());
                    Item item = itemDao.selectByPrimaryKey(orderItem.getItemId());
                    //设施商品id
                    orderItem.setGoodsId(item.getGoodsId());
                    //设置订单id
                    orderItem.setOrderId(orderId);
                    //设置图片地址
                    orderItem.setPicPath(item.getImage());
                    //设置单价
                    orderItem.setPrice(item.getPrice());
                    //设置数量

                    //设置购买总金额
                    double totalFee = item.getPrice().doubleValue() * orderItem.getNum();
                    payment += totalFee;
                    //支付总金额 用于支付页面显示  日志保存
                    payLogTotalFee+=totalFee;
                    orderItem.setTotalFee(new BigDecimal(totalFee));
                    orderItem.setSellerId(item.getSellerId());  // 商家id
                    orderItemDao.insertSelective(orderItem);
                }
                // 设置该商家下的总金额
                order.setPayment(new BigDecimal(payment));
                orderDao.insertSelective(order);
            }
        }
        //保存支付日志
        PayLog payLog = new PayLog();
        //下单时间
        payLog.setCreateTime(new Date());
        //订单编号
        payLog.setOutTradeNo(idWorker.nextId()+"");
        //支付金额
        payLog.setTotalFee(payLogTotalFee.longValue());
        //userId
        payLog.setUserId(username);
        //交易状态 0 未付款
        payLog.setTradeState("0");
        //订单编号列表
        payLog.setOrderList(orderIdList.toString().replace("[","").replace("]",""));

        //支付类型 完成时间  交易号码  交易状态  在支付时更新
        payLogDao.insertSelective(payLog);
        //保存在redis中
        redisTemplate.boundHashOps("payLog").put(username,payLog);
        // 删除购物车
        redisTemplate.boundHashOps("BUYER_CART").delete(username);



    }
}
