package com.entertainment.project.common.log;


/**
 * Created by lc on 16/4/1.
 * 性能日志
 */
public class PerformanceLog {

    public static String fileName = "performance";

    public static String LOGIN = "login";//登录
    public static String APPLICATIONLIST = "application.list.get";//申请单列表获取
    public static String EXPENSELIST = "expense.list.get";//费用列表获取
    public static String EXPENSEREPORTLIST = "expenseReport.list.get";//报销单列表获取
    public static String APPROVEDLIST = "approved.list.get";//已审批列表获取
    public static String APPROVINGLIST = "approving.list.get";//未审批列表获取
    public static String APPLICATIONINFO = "application.info.get";//查询申请单
    public static String EXPENSEREPORTINFO = "expenseReport.info.get";//查询报销单
    public static String EXPENSEINFO = "expense.info.get";//查询费用
}
