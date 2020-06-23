package com.qa.tests;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;

public class encodeTest {
    public static void main(String[] args) throws URIException {
        String url = "unselectedCols=periodpriceearning,allInComeing,convexity,basevalue&unselectedColsName=累计价差收入,总收益,凸性,基点价值&selectedCols=holdposition,periodhonourinterest,faceamount,interestcost,failvaluation,periodrealearning,finalfloatearning,dirtypricecost,cleanpricecost,bondvaluation,yield,duration,modifyduration,finalaccuredinterest,stockcurmarketvalue,remainterm&selectedColsName=持仓量,累计利息收入,面额,利息成本,公允价值损益,已实现收益,浮动利息收益,全价成本,净价成本,净价估值,买入收益率(%),久期,修正久期,应计利息,全价估值,剩余期限（天）";
        System.out.println(URIUtil.encodeQuery(url));
    }
}
