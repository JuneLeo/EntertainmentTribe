package com.entertainment.project.common.log;

/**
 * Created by lc on 16/4/1.
 * 行为日志
 */
public class BehaviorLog {

    public static String fileName = "behavior";

    public static String LOGIN = "login";//申请单列表获取
    public static String APPLICATIONSAVE = "application.save";//新建申请单保存
    public static String APPLICATIONSUBMIT = "application.submit";//新建申请单提交
    public static String EXPENSESAVE = "expense.save";//新建费用保存
    public static String EXPENSEREPORTSAVE = "expenseReport.save";//新建报销单保存
    public static String EXPENSEREPORTSUBMIT = "expenseReport.submit";//新建报销单提交
    public static String FLIGHTSSEARCH = "flights.search";//查询机票
    public static String HOTELSEARCH = "hotel.search";//查询酒店
    public static String TRAINSEARCH = "train.search";//查询火车票
    public static String FLIGHTSPAY = "flights.pay";//机票支付
    public static String HOTELPAY = "hotel.pay";//酒店支付
    public static String TRAINPAY = "train.pay";//火车票支付
    public static String ORDERLISTSEARCH = "orderList.search";//订单列表
    public static String EXPENSESTATISTICSEARCH = "expenseStatistic.search";//查询费用统计
    public static String BUDGETSEARCH = "budget.search";//预算联查

    public static String TRAVELSEARCH = "travel.search";//行程管理入口
    public static String REIMINDEX = "reim.index";//报销首页
    public static String FLIGHTDYNAMICORDER = "flight.dynamic.order.search";//航班动态 预定
    public static String FLIGHTDYNAMICTRAVEL = "flight.dynamic.travel.search";//航班动态 行程

}
