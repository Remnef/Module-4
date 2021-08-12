package com.DAO;

import com.contact.MySQLConnUtils;
import com.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    PreparedStatement ps = null;
    Connection conn = null;
    ResultSet rs = null;

    public Customer selectCustomerById(String id){
        Customer customer = null;
        try{
            String query= "SELECT * FROM customers WHERE `id`=?";
            conn = MySQLConnUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1,id);
            rs = ps.executeQuery();
            while (rs.next()){
                customer = new Customer(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getFloat(7),
                        (rs.getInt(8)==1)?true:false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  customer;
    }

    public List<Customer> customerList(){
        List<Customer> customers = new ArrayList<>();
        try{
            String query = "SELECT * FROM customers ORDER BY id DESC";
            conn = MySQLConnUtils.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                customers.add(new Customer(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getFloat(7),
                        (rs.getInt(8)==1)?true:false));
            }
        }catch (Exception e){

        }
        return  customers;
    }

    public boolean deleteCustomerById(String id){
        Boolean check = false;
        try{
            String query = "DELETE FROM customers where id = ?";
            conn = MySQLConnUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1,id);
            check = ps.executeUpdate() >0;
        }catch (Exception e){

        }
        return check;
    }

    public boolean addNewCustomer(Customer customer){
        boolean check = false;
        try{
            String query = "INSERT INTO `customers` (`username`, `password`, `name`, `email`, `phone`, `salary`, `status`) VALUES (?, ?, ?, ?, ?, ?, b?);";
            conn = MySQLConnUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1,customer.getUsername());
            ps.setString(2,customer.getPassword());
            ps.setString(3,customer.getName());
            ps.setString(4,customer.getEmail());
            ps.setString(5,customer.getPhone());
            ps.setFloat(6,customer.getSalary());
            ps.setString(7, String.valueOf((customer.isStatus())?'1':'0'));
            check = ps.executeUpdate()>0;
        }catch (Exception e){

        }
        return check;
    }

    public boolean editCustomerById(String id, String name, String email, String phone, float salary, boolean status){
        boolean check = false;
        try{
            String query = "UPDATE `customers` SET `name` = ?, `email` = ?, `phone` = ?, `salary` = ?, `status` = ? WHERE (`id` = ?);";
            conn = MySQLConnUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,phone);
            ps.setFloat(4,salary);
            ps.setInt(5, status?1:0);
            ps.setString(6,id);
            check = ps.executeUpdate()>0;
        }catch (Exception e){

        }
        return check;
    }

    public boolean updateSalary(int id, float salary){
        boolean check = false;
        try{
            String query = "UPDATE `customers` SET `salary` = ? WHERE (`id` = ?);";
            conn = MySQLConnUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setFloat(1,salary);
            ps.setInt(2,id);
            check = ps.executeUpdate()>0;
        }catch(Exception e){

        }
        return  check;
    }
}
