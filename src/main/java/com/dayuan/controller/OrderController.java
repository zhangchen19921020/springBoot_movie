package com.dayuan.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.dayuan.config.AlipayConfig;
import com.dayuan.entity.Order;
import com.dayuan.entity.User;
import com.dayuan.mapper.*;
import com.dayuan.service.OrderService;
import com.dayuan.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api")
public class OrderController {


    @Resource
    private MovieMapper movieMapper;
     @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderService orderService;
    @Resource
    private UserMapper userMapper;


    /**
     * @param showTimeId
     * @return
     */
    @PostMapping("listSaledSeats.do")
    @ResponseBody
    public AjaxResult listSaledSeats(@RequestParam Integer showTimeId) {
        try {
            return AjaxResult.success(orderMapper.listSaledSeats(showTimeId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @PostMapping("addOrder.do")
    @ResponseBody
    public AjaxResult addOrder(HttpSession session,
                               ModelMap modelMap,
                               @RequestParam Integer showTimeId,
                               @RequestParam Integer movieId,
                               @RequestParam("selectedSeats[]") List<String> selectedSeats) {


        String loginName = (String) session.getAttribute("loginName");

        User user = userMapper.selectByLoginName(loginName);

        Order order = null;
        try {
            order = orderService.addOrder(showTimeId, movieId, selectedSeats, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }


        //请求支付宝获取表单
        try {
            String result = orderService.toPay(order.getOrderNo(), order.getTotalPrice());
            return AjaxResult.success(result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            AjaxResult.fail("支付失败");
        }
        return null;
    }

    @RequestMapping(value = "/notifyUrl.do")
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {


        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用,已经在配置文件里面配置Response编码
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = false; //调用SDK验证签名
        signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/

        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");

            //支付宝交易号
            String trade_no = request.getParameter("trade_no");

            //交易状态
            String trade_status = request.getParameter("trade_status");

            //验证app_id
            String app_id = request.getParameter("app_id");
            if (!app_id.equals(AlipayConfig.app_id)) {
                try {
                    response.getWriter().println("fail");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            //验证
            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                //        1:更改订单状态
                //        2:加余额


                try {

                    orderMapper.updateStatus(1, out_trade_no, trade_no);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            response.getWriter().println("success");

        } else {//验证失败

            response.getWriter().println("fail");

        }

    }


    @PostMapping("getMovieId.do")
    @ResponseBody
    public AjaxResult getMovieId(@RequestParam("id") Integer id) {

        try {
            return AjaxResult.success(movieMapper.selectByPrimaryKey(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





}
