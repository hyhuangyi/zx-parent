package cn.common.util.sys;

import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JDBCUtils {

    /**
     * 获取连接
     *
     * @param url
     * @param name
     * @param pwd
     * @param driver
     * @return
     */
    public static Connection getConnection(String url, String name, String pwd, String driver) {

        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, name, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return conn;
    }

    /**
     * 关闭连接
     *
     * @param conn
     * @param st
     * @param rs
     */
    public static void close(Connection conn, Statement st, ResultSet rs) {
        //关闭连接
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //关闭statement
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //关闭结果集
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //-------------------------------封装sql操作------------------------------

    /**
     * 获取列表
     *
     * @param conn
     * @param sql
     * @param obj
     * @return
     */
    public static List<Map> getList(Connection conn, String sql, Object... obj) {
        List<Map> list = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.获取预处理对象
            ps = conn.prepareStatement(sql);
            //循环参数，如果没有就不走这里
            for (int i = 1; i <= obj.length; i++) {
                //注意：数组下标从0开始，预处理参数设置从1开始
                ps.setObject(i, obj[i - 1]);
            }
            //2.执行SQL语句
            log.info(sql);
            rs = ps.executeQuery();
            //3.遍历结果集
            //遍历之前准备：因为封装不知道未来会查询多少列，所以我们需要指定有多少列
            ResultSetMetaData metaData = rs.getMetaData();//获取ResultSet对象的列编号、类型和属性
            int cols_len = metaData.getColumnCount();//获取列数

            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = metaData.getColumnLabel(i + 1);//列别名
                    Object cols_value = rs.getObject(cols_name);
                    if (null == cols_value) {
                        cols_value = "";
                    }
                    map.put(cols_name, cols_value);
                }
                list.add(map);
            }
            return list;
            //5.关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
        return null;
    }

    /**
     * 增加、删除、修改
     *
     * @param sql sql语句
     * @param obj 参数
     * @return
     */
    public static boolean dml(Connection conn, String sql, Object... obj) {

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            for (int i = 1; i <= obj.length; i++) {
                ps.setObject(i, obj[i - 1]);
            }
            log.info(sql);
            int update = ps.executeUpdate();

            if (update > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
        return false;
    }

    /**
     * 查询返回单个对象
     *
     * @param conn
     * @param sql
     * @param obj
     * @return
     */
    public static Map getOne(Connection conn, String sql, Object... obj) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= obj.length; i++) {
                ps.setObject(i, obj[i - 1]);
            }
            log.info(sql);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int cols_len = metaData.getColumnCount();
            Map map = new HashMap();
            while (rs.next()) {
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = metaData.getColumnLabel(i + 1);
                    Object cols_value = rs.getObject(cols_name);
                    if (null == cols_value) {
                        cols_value = "";
                    }
                    map.put(cols_name, cols_value);
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
        return null;
    }

    /**
     * 获取表数据量
     *
     * @param conn
     * @param sql
     * @param obj
     * @return
     */
    public static Long getCount(Connection conn, String sql, Boolean ifClose, Object... obj) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= obj.length; i++) {
                ps.setObject(i, obj[i - 1]);
            }
            log.info(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ifClose)
                JDBCUtils.close(conn, ps, rs);
        }
        return null;
    }


    /**
     * 获取表列表
     * @param conn
     * @return
     */
    public static List<Map> getTableList(Connection conn) {
        String sql = "select table_name,table_comment from information_schema.tables where table_schema = (select database())";
        return getList(conn, sql);
    }

    /**
     * 获取表字段信息
     * @param conn
     * @param table
     * @return
     */
    public static List<Map> getColumnInfo(Connection conn, String table) {
        String sql = "select column_name,column_comment,column_type FROM information_schema.columns where  table_schema = (select database()) and table_name =?";
        return getList(conn, sql, table);
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://150.158.86.218:3306/zx";
        String user = "root";
        String password = "zxadmin";
        String mysql_driver = "com.mysql.jdbc.Driver";
        Connection conn = getConnection(url, user, password, mysql_driver);
        System.out.println(getTableList(conn));
    }
}