package com.qa.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xt_Operate {
    //实例JdbcPz
    public static JdbcPz jdb = new JdbcPz();
    //需用到的变量
    private static Connection conn=null;
    private static PreparedStatement ps=null;
    private static ResultSet rs=null;
    public static String SQLsen=null;
    //参数数组，SQL语句的参数直接放入ArrayList
    public static ArrayList<Object> parameter = new ArrayList<>();
    //初始化
    public static void LoadJdbc(){
        //调用配置JdbcPz 中的连接方法完成初始化
        conn= JdbcPz.getConnection();
    }

    //afferentSQL（） 接收出传入的SQL语句
    public static void  afferentSQL(String Sqlsentence){
        try {
            SQLsen = Sqlsentence;
            ps = conn.prepareStatement(Sqlsentence, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * //增、修、删 一条数据并返回影响条数
     * @Sqlsentence SQL语句
     * @author JDBC_XT by 丘秋
     */
//变量赋值返回影响条数
    public static int UpdatemoveXT(){
        int a = 0;
        try {
            //遍历变量
            for(int i=0;i<parameter.size();i++){
                //下标从0开始，要加个1
                int o = i + 1;
                //取出变量元素
                Object param = parameter.get(i);
                //判断变量的类型并完成赋值 （只写三种，可以继续处理更多类型）
                if (param instanceof Integer) {
                    ps.setInt(o,(int)param);
                } else if (param instanceof Double) {
                    ps.setDouble(o,(double)param);
                } else if (param instanceof String) {
                    ps.setString(o,(String)param);
                }
            }
            //执行SQL,完成操作
            a = ps.executeUpdate();
            System.out.println("✔ 操作 "+ a +" 条数据成功 ！");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //关闭流
            closeXT();
        }
        //返回影响条数
        return a;
    }

    /**
     * 查询数据
     */
    public static List<Object> SelectXT(){
        //要返回的List数组
        List<Object> users = new ArrayList<Object>();
        try {
            //处理变量
            for(int i=0;i<parameter.size();i++){
                int o = i + 1;
                Object param = parameter.get(i);
                if (param instanceof Integer) {
                    ps.setInt(o,(int)param);
                } else if (param instanceof Double) {
                    ps.setDouble(o,(double)param);
                } else if (param instanceof String) {
                    ps.setString(o,(String)param);
                }
            }
            //执行SQL
            rs=ps.executeQuery();
            int ts= 0;
            //获取数据库表的属性
            ResultSetMetaData rsmd = rs.getMetaData();
            //遍历返回结果并存入List
            while (rs.next()) {
                ts++;
                //使用Map集合保存每条数据
                Map<String, Object> rowData = new HashMap<String, Object>();
                //遍历当前条的每一列
                for(int l=1;l<=rsmd.getColumnCount();l++){
                    rowData.put(rsmd.getColumnName(l),rs.getObject(l));
                }
                //追加到List中
                users.add(rowData);
            }
            System.out.println("✔ 查询数据成功,共返回"+ts+" 条数据 ！");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //关闭
            closeXT();
        }
        //返回数据
        return users;
    }

    //关闭
    private static void closeXT(){
        parameter.clear();
        try {
            if(ps!=null){
                ps.close();
            }
            if(rs!=null){
                rs.close();
            }
            ps=null;rs=null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
