package com.xiaojiaqi.learn.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: liangjiaqi
 * @Date: 2020/8/23 9:37 AM
 */
public class OrderStep {
    private long orderId;
    private String desc;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public OrderStep(long orderId, String desc) {
        this.orderId = orderId;
        this.desc = desc;
    }

    public OrderStep() {
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static List<OrderStep> buildOrders(){
        // 1039: 创建、付款、推送、完成
        // 1065：创建、付款、完成
        // 7235：创建、付款、完成


        List<OrderStep> orderStepList = new ArrayList<OrderStep>();

        OrderStep[] s = new OrderStep[]{new OrderStep(1039,"创建"),
                new OrderStep(1065,"创建"),
                new OrderStep(1039,"付款"),
                new OrderStep(7235, "创建"),
                new OrderStep(1065, "付款"),
                new OrderStep(7235, "付款"),
                new OrderStep(1065,"完成"),
                new OrderStep(1039, "推送"),
                new OrderStep(7235,"完成"),
                new OrderStep(1039, "完成")};
        orderStepList.addAll(Arrays.asList(s));
        return orderStepList;

    }
}
