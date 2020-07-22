package com.qa.tests;


import com.qa.util.Xt_Operate;
import java.util.List;
import java.util.Map;

public class testJdbc2 extends Xt_Operate {

    public static void main(String[] args) {


        //查询
        LoadJdbc();
        //查询数据并返回查询结果
        afferentSQL("SELECT NAME FROM `user` WHERE login_name like '1990000000%';");
        //SelectXT（）使用list接收返回结果
        List<Object> usersa = SelectXT();

        //数据读取示例
        //获取第一条数据
        Map<String, Object> rowData = (Map<String, Object>)usersa.get(1);
        //获取第一条数据的name字段
        rowData.get("name");

        System.out.println("第1条数据的name字段："+rowData.get("name"));


        //传入sql
        LoadJdbc();

        String[] strArray = {"UPDATE `user`  SET `name` = '555' WHERE login_name LIKE '1990000000%' AND `name` = '222';", "UPDATE `user`  SET `name` = '666' WHERE login_name LIKE '1990000000%' AND `name` = '444';"};

//        String str =
//        String[] stringArrayData = initSql.split(",");

        //执行删除操作
        InsertUpdateDeleteXT(strArray);



    }
}

