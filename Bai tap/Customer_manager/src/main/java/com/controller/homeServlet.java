package com.controller;

import com.DAO.CustomerDao;
import com.DAO.TransactionDao;
import com.model.Customer;
import com.model.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "homeServlet", urlPatterns = "/home")
public class homeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit= request.getParameter("submit");
        switch (submit){
            case "Edit":
            {
                edit(request,response);
                break;
            }
            case "Delete":
            {
                Delete(request,response);
                break;
            }
            case "Add":
            {
                Add(request,response);
                break;
            }
            case "Search":
            {
//                search(request,response);
                break;
            }
            default:{
                showListCustomer(request,response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action==null){
            action="";
        }
        if (action.equals("history")){
            showHistory(request,response);
        }else{
            showListCustomer(request,response);
        }
    }

    protected void showListCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDao dao = new CustomerDao();
        List<Customer> customerList = dao.customerList();
        request.setAttribute("listC",customerList);
        request.getRequestDispatcher("views/home.jsp").forward(request, response);
    }

    protected void showHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TransactionDao dao = new TransactionDao();
        List<Transaction> transactionList = dao.getList();
        request.setAttribute("listT",transactionList);
        request.getRequestDispatcher("views/History.jsp").forward(request,response);
    }

    protected void Delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDao dao = new CustomerDao();
        String id =  request.getParameter("id");
        Boolean check = dao.deleteCustomerById(id);
        request.setAttribute("mess",(check)?"Successful !":"Something is wrong !!!");
        showListCustomer(request,response);
    }

    protected void Add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDao dao = new CustomerDao();
        String username = request.getParameter("username");
        Boolean check = true;
        List<Customer> customerList = dao.customerList();
        for (Customer customer: customerList) {
            if (username.equals(customer.getUsername())){
                check = false;
            }
        }
        if (check){
            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");
            if (password.equals(repassword)){
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String mess = "";
                if (username == "") mess+= "Username is NULL. ";
                if (password == "") mess+= "Password is NULL. ";
                if (fullName == "") mess+= "Name is NULL. ";
                if (email == "") mess+= "Email is NULL. ";
                if (phone == "") mess+= "Phone is NULL. ";
                if (mess==""){
                    boolean status = Integer.parseInt(request.getParameter("status"))==1?true:false;
                    Customer customer = new Customer(username,password,fullName,email,phone,status);
                    boolean checked = dao.addNewCustomer(customer);
                    request.setAttribute("mess",(checked)?"Successful !":"Something is wrong !!!");
                    showListCustomer(request,response);
                }else{
                    request.setAttribute("mess","Wrong is: "+mess);
                    showListCustomer(request,response);
                }
            }else{
                request.setAttribute("mess","The password is not equal retype !!!");
                showListCustomer(request,response);
            }
        }else{
            request.setAttribute("mess","Username is exist !!!");
            showListCustomer(request,response);
        }

    }

    protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDao dao = new CustomerDao();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String mess = "";
        if (name == "") mess+= "Name is NULL. ";
        if (email == "") mess+= "Email is NULL. ";
        if (phone == "") mess+= "Phone is NULL. ";
        if (request.getParameter("salary")=="") mess+= "Salary is NULL. ";
        if (mess==""){
            float salary = Float.parseFloat(request.getParameter("salary"));
            boolean status = Integer.parseInt(request.getParameter("status"))==1?true:false;
            Boolean check = dao.editCustomerById(id,name,email,phone,salary,status);
            request.setAttribute("mess",(check)?"Successful !":"Something is wrong !!!");
            showListCustomer(request,response);
        }else{
            request.setAttribute("mess","Wrong is: "+mess);
            showListCustomer(request,response);
        }
    }
}