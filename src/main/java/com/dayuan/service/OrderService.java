package com.dayuan.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.dayuan.config.AlipayConfig;
import com.dayuan.entity.Movie;
import com.dayuan.entity.Order;
import com.dayuan.entity.OrderInfo;
import com.dayuan.exception.BizException;
import com.dayuan.mapper.MovieMapper;
import com.dayuan.mapper.OrderInfoMapper;
import com.dayuan.mapper.OrderMapper;
import com.dayuan.util.DecimalCalculate;
import com.dayuan.util.MyRandomUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private MovieMapper movieMapper;

    private static Logger log = LoggerFactory.getLogger(OrderService.class);


    /**
     * 添加订单业务
     *
     * @param showTimeId
     * @param movieId
     * @param selectedSeats
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public Order addOrder(Integer showTimeId, Integer movieId, List<String> selectedSeats, Integer userId) throws BizException{


        for (String seat : selectedSeats) {
            int count = orderMapper.countBySeat(showTimeId, seat);
            if (count > 0) {
               throw new BizException("票已售出，请重新选择");

            }
        }


        Movie movie = movieMapper.selectByPrimaryKey(movieId);
        Double totalPrice = DecimalCalculate.mul(Double.parseDouble(movie.getPrice()), selectedSeats.size());
        //主表
        Order order = new Order();
        order.setOrderNo(System.currentTimeMillis() + MyRandomUtil.getRandom(6));
        order.setSeatCode(MyRandomUtil.getRandom(6));
        order.setShowTimeId(showTimeId);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice.toString());
        orderMapper.insertOrder(order);

                                                    //从表id
        for (String seat : selectedSeats) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(order.getId());
            orderInfo.setPrice(movie.getPrice());
            orderInfo.setSeat(seat);
            orderInfoMapper.insertOderInfo(orderInfo);
        }

        return order;
    }


    /**
     * 获取支付form表单
     * @param orderNo
     * @param money
     * @return
     * @throws AlipayApiException
     */
    public String toPay(String orderNo, String money) throws AlipayApiException {
        //调用支付宝支付接口
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderNo;
        //付款金额，必填
        String total_amount = money;
        //订单名称，必填
        String subject = "大猿国际影城";
        //商品描述，可空
        String body = "电影售票";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;

    }


    @Transactional(rollbackFor = Exception.class)
    public void cancelOrderTask(){

        List<Integer> listOrderId = null;
        try {
            listOrderId = orderMapper.listCancelOrderId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("查询到{}条待取消的订单", listOrderId.size());

        for (Integer id:listOrderId) {
            try {
                orderMapper.selectByPrimaryKeyForLock(id);
                orderMapper.updateStatusById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("取消订单，id={}", id);

        }

    }






}
