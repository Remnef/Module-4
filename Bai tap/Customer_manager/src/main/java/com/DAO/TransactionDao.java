package com.DAO;

import com.contact.MySQLConnUtils;
import com.model.Customer;
import com.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    PreparedStatement ps = null;
    Connection conn = null;
    ResultSet rs = null;

    public List<Transaction> getList(){
        List<Transaction> transactionList = new ArrayList<>();
        try{
            String query = "SELECT * FROM customers.transaction_history ORDER BY id DESC";
            conn = MySQLConnUtils.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                transactionList.add(new Transaction(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getFloat(5),
                        rs.getString(6)));
            }
        }catch(Exception e){

        }
        return transactionList;
    }

    public void addList(Transaction transaction) throws SQLException {
        String query="INSERT INTO `customers`.`transaction_history` (`name`, `method`, `money`, `fee`, `time`) VALUES (?,?,?,?,?);";
        conn = MySQLConnUtils.getConnection();
        ps = conn.prepareStatement(query);
        ps.setString(1,transaction.getName());
        ps.setString(2,transaction.getMethod());
        ps.setFloat(3,transaction.getMoney());
        ps.setFloat(4,transaction.getFee());
        ps.setString(5,transaction.getTime());
        ps.executeUpdate();
    }
}
