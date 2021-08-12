<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/27/2021
  Time: 8:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
<html>
<head>
    <title>User Management Application</title>
    <style>
        .message{
            color:white;
        }
        .error{
            color: red;
            font-family: Arial;
            font-weight: bold;
        }
        .success{
            color: lightgreen;
            font-weight: bold;
            font-family: Arial;
        }
        #mess{
            width: 300px;
            position: fixed;
            bottom: 0;
            left: 0;
            margin-bottom: 0;
        }
        header{
            text-align: center;
        }
    </style>
</head>
<body>
<header>
    <h1>User Management</h1>
    <h2>
        <a href="/home">List All Users</a>
    </h2>
</header>
<div align="center" class="container">
    <form class="needs-validation" action="/transfer" novalidate method="post">
        <h2>Transfer Transaction</h2>
        <div id="mess" class="${mess=="Successful !"?"alert alert-success":(mess==null)?"":"alert alert-danger"}" role="alert">
            <p style="text-align: center; font-size: larger">${mess}</p>
        </div>
        <div class="form-group row">
            <div class="col-md-4 mb-3">
                <label for="validationCustom01">ID Sender</label>
                <input disabled type="text" class="form-control" id="validationCustom01" value="${customer.id}" required>
            </div>
            <input type="text" name="id" style="display: none" value="${customer.id}">
            <div class="col-md-4 mb-3">
                <label >Customer Name</label>
                <input disabled type="text" name="name" size="30" class="form-control"
                       value="<c:out value='${customer.name}'/>" required>
            </div>
            <div class="col-md-4 mb-3">
                <label >Fee</label>
                <input disabled type="text" name="amount" id="amount" size="30" class="form-control"
                       value="" placeholder="0" required>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-md-4 mb-3">
                <label class="col-sm-12 col-form-label">Salary</label>
                <div class="col-md-12">
                    <input disabled type="text" name="salary" class="form-control" value="${customer.salary}" >
                </div>
            </div>
            <div class="col-md-4 mb-3">
                <label class="col-sm-12 col-form-label">Customer Receiver</label>
                <div class="col-md-12">
                    <select name="idReceiver" class="form-select custom-select" aria-label="Default select example">
                        <c:forEach var="item" items="${listC}">
                            <c:if test="${item.id != customer.id}">
                                <option class="form-control" value="<c:out value='${item.id}'/>">${item.username}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-4 mb-3">
                <label class="col-sm-12 col-form-label">Amount</label>
                <div class="col-md-12">
                    <input type="number" name="salaryReceiver" placeholder="5% fee" size="15" oninput="calculatorFee()" id="salaryR" class="form-control" required/>
                </div>
            </div>
        </div>
        <button class="btn btn-primary" type="submit" value="Transfer" name="submit">Transfer Transaction</button>
    </form>

    <script>
        // Example starter JavaScript for disabling form submissions if there are invalid fields
        (function() {
            'use strict';
            window.addEventListener('load', function() {
                // Fetch all the forms we want to apply custom Bootstrap validation styles to
                var forms = document.getElementsByClassName('needs-validation');
                // Loop over them and prevent submission
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
        function calculatorFee() {
            let salary = parseFloat(document.getElementById("salaryR").value);
            if (isNaN(salary)) {
                document.getElementById("amount").value = 0;
            } else {
                let feeAmount = salary * 5 /100;
                document.getElementById("amount").value = feeAmount;
            }
        }
        $(".alert").fadeTo(2000, 500).fadeOut(1000);
    </script>
</div>
</body>
</html>