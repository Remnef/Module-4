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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "transferServlet", urlPatterns = "/transfer")
public class transferServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
        if (submit == null){
            response.sendRedirect("/home");
        }else{
            switch (submit){
                case "Transfer":
                {
                    transfer(request,response);
                    break;
                }
                case "Deposit":
                {
                    Deposit(request,response);
                    break;
                }
                case "Withdraw":
                {
                    Withdraw(request,response);
                    break;
                }
                default:{
                    response.sendRedirect("/home");
                    break;
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("action");
        if (id == null){
            response.sendRedirect("/home");
        }else{
            showTransfer(request,response,id);
        }
    }

    protected void transfer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String idR = request.getParameter("idReceiver");
        String mess="";
        if (request.getParameter("salaryReceiver")==null) mess+="Have wrong: Salary is Null.";
        if (mess == ""){
            float salaryR = Integer.parseInt(request.getParameter("salaryReceiver"));
            CustomerDao dao = new CustomerDao();
            Customer customerS = dao.selectCustomerById(id);
            Customer customerR = dao.selectCustomerById(idR);
            boolean check = salaryR <= ((customerS.getSalary() * 90) / 100);
            if (check){
                customerS.setSalary(customerS.getSalary()-(salaryR*105/100));
                customerR.setSalary(customerR.getSalary()+salaryR);
                dao.updateSalary(customerR.getId(),customerR.getSalary());
                dao.updateSalary(customerS.getId(),customerS.getSalary());
                Transaction transaction = new Transaction(
                        customerS.getName()+"(#"+customerS.getId()+")",
                        "Transfer(#"+customerR.getId()+")",
                        salaryR, salaryR/100*5,
                        new SimpleDateFormat("HH:mm dd/MM/YYYY").format(new Date()));
                TransactionDao daoT = new TransactionDao();
                try {
                    daoT.addList(transaction);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                request.setAttribute("mess","Successful !");
                showTransfer(request,response,id);
            }else{
                request.setAttribute("mess","Tham lam !");
                showTransfer(request,response,id);
            }
        }else{
            request.setAttribute("mess",mess);
            showTransfer(request,response,id);
        }
    }

    protected void Deposit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("deposit")!=""){
            int ID = Integer.parseInt(request.getParameter("id"));
            float newSalary = Float.parseFloat(request.getParameter("deposit"));
            CustomerDao dao = new CustomerDao();
            Customer customer = dao.selectCustomerById(Integer.toString(ID));
            boolean check = dao.updateSalary(ID,customer.getSalary()+newSalary);
            List<Customer> customerList = dao.customerList();
            request.setAttribute("listC",customerList);
            if (check){
                Transaction transaction = new Transaction(
                        customer.getName()+"(#"+customer.getId()+")",
                        "Deposit",
                        newSalary, 0,
                        new SimpleDateFormat("HH:mm dd/MM/YYYY").format(new Date()));
                TransactionDao daoT = new TransactionDao();
                try {
                    daoT.addList(transaction);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            request.setAttribute("mess",(check)?"Successful !":"Something is Wrong !!!");
            request.getRequestDispatcher("views/home.jsp").forward(request, response);
        }else{
            CustomerDao dao = new CustomerDao();
            List<Customer> customerList = dao.customerList();
            request.setAttribute("listC",customerList);
            request.setAttribute("mess","Deposit is NULL");
            request.getRequestDispatcher("views/home.jsp").forward(request, response);
        }
    }

    protected void Withdraw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("withdraw")!=""){
            int ID = Integer.parseInt(request.getParameter("id"));
            float newSalary = Float.parseFloat(request.getParameter("withdraw"));
            CustomerDao dao = new CustomerDao();
            Customer customer = dao.selectCustomerById(Integer.toString(ID));
            boolean check = newSalary <=customer.getSalary()*0.9;
            if(check){
                boolean checked = dao.updateSalary(ID,customer.getSalary()-newSalary);
                if (checked){
                    Transaction transaction = new Transaction(
                            customer.getName()+"(#"+customer.getId()+")",
                            "Withdraw",
                            newSalary, 0,
                            new SimpleDateFormat("HH:mm dd/MM/YYYY").format(new Date()));
                    TransactionDao daoT = new TransactionDao();
                    try {
                        daoT.addList(transaction);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                List<Customer> customerList = dao.customerList();
                request.setAttribute("listC",customerList);
                request.setAttribute("mess",(checked)?"Successful !":"Something is Wrong !!!");
                request.getRequestDispatcher("views/home.jsp").forward(request, response);
            }else{
                List<Customer> customerList = dao.customerList();
                request.setAttribute("listC",customerList);
                request.setAttribute("mess","Something is Wrong !!!");
                request.getRequestDispatcher("views/home.jsp").forward(request, response);
            }
        }else{
            CustomerDao dao = new CustomerDao();
            List<Customer> customerList = dao.customerList();
            request.setAttribute("listC",customerList);
            request.setAttribute("mess","Value is NULL");
            request.getRequestDispatcher("views/home.jsp").forward(request, response);
        }
    }
    protected void showTransfer(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException {
        CustomerDao dao = new CustomerDao();
        Customer customer = dao.selectCustomerById(id);
        List<Customer> customers = dao.customerList();
        request.setAttribute("customer",customer);
        request.setAttribute("listC",customers);
        request.getRequestDispatcher("views/transfer.jsp").forward(request,response);
    }


}
