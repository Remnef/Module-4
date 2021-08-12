<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Loc's store</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="resource/js/jquery.validate.min.js"></script>
    <style>
        #mess{
            width: 300px;
            position: fixed;
            bottom: 0;
            left: 0;
            margin-bottom: 0;
        }
        .inputPass{
            width: 80%;
            padding: 5px;
            margin: 7px 7px 7px 50px;
            border-radius: 15px;
            border-collapse: collapse;
        }
        .form-group{
            padding: 3px;
            margin:0px;
        }
        div.modal-content{
            padding: 0px;
        }
        div.modal-header{
            padding: 0px;
        }
        label.error{
            color: red !important;
        }
    </style>
<body>
<header>
    <div class="container">
        <div>
            <form style="float: right" action="/home" method="post" class="form-inline my-2 my-lg-0">
                <div class="input-group input-group-sm">
                    <input name="txt" value="${txtS}" type="text" class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Search by name:">
                    <div class="input-group-append">
                        <button name="submit" type="submit" value="Search" class="btn btn-secondary btn-number">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <div id="mess" class="${mess=="Successful !"?"alert alert-success":(mess==null)?"":"alert alert-danger"}" role="alert">
            <p style="text-align: center; font-size: larger">${mess}</p>
        </div>
    </div>
</header>
<%--table--%>

<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2>Manage <b>Customer</b></h2>
                </div>
                <div class="col-sm-6">
                    <a href="#addEmployeeModal"  class="btn btn-success" data-toggle="modal"><i class="fa fa-pencil-square-o"></i> <span>Add New Customer</span></a>
                    <a href="/home?action=history"  class="btn btn-warning"><i class="fa fa-history"></i> <span>History</span></a>
                </div>
            </div>
        </div>
        <table class="table table-striped table-hover" id="tblListProducts">
            <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Salary</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listC}" var="o">
                <tr>
                    <td>${o.id}</td>
                    <td>${o.name}</td>
                    <td>${o.email}</td>
                    <td>${o.phone}</td>
                    <td>${o.salary}</td>
                    <td>${o.status}</td>
                    <td>
                        <a data-id="${o.id}" data-name="${o.name}" data-email="${o.email}" data-phone="${o.phone}" data-salary="${o.salary}" class="edit" data-toggle="modal">
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                        </a>
                        <a data-id="${o.id}" class="delete" data-toggle="modal">
                            <i class="fa fa-trash-o" aria-hidden="true"></i>
                        </a>
                        <a class="transfer" href="/transfer?action=${o.id}">
                            <i class="fa fa-exchange" aria-hidden="true"></i>
                        </a>
                        <a class="deposit" data-id="${o.id}">
                            <i class="fa fa-plus" aria-hidden="true"></i>
                        </a>
                        <a class="withdraw" data-id="${o.id}">
                            <i class="fa fa-minus" aria-hidden="true"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<!-- Delete Modal HTML -->
<div id="deleteEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action ="/home" method="post">
                <div class="modal-header">
                    <h4 class="modal-title">Delete Product</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete these Records?</p>
                    <p class="text-warning"><small>This action cannot be undone.</small></p>
                </div>
                <input type="text" id="id" name="id" style="display: none">
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" name="submit" class="btn btn-danger" value="Delete">
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Add Modal HTML -->
<div id="addEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/home" method="post" id="addModal">
                <div class="modal-header">
                    <h4 class="modal-title">Add Account</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>User name</label>
                        <input type="text" name="username" class="form-control" placeholder="User Name:">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password" class="form-control" placeholder="Password:">
                    </div>
                    <div class="form-group">
                        <label>Retype password</label>
                        <input type="password" name="repassword" class="form-control" placeholder="Retype Password:">
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" name="fullName" class="form-control" placeholder="Type name:">
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" class="form-control" placeholder="Type email:">
                    </div>
                    <div class="form-group">
                        <label>Phone</label>
                        <input type="phone" name="phone" class="form-control" placeholder="Type email:">
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <select name="status" class="form-select" aria-label="Default select example">
                            <option value="0">Locked</option>
                            <option value="1">Unlock</option>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" name="submit" class="btn btn-success" value="Add" id="addData">
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit Modal HTML -->
<div id="editEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="/home" method="post" id="editModal">
                <div class="modal-header">
                    <h4 class="modal-title">Edit Account</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <input type="number" id="idEdit" name="id" style="display: none">
                <div class="modal-body">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" name="name" class="form-control" id="nameEdit" placeholder="Type name:">
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" class="form-control" id="emailEdit" placeholder="Type email:">
                    </div>
                    <div class="form-group">
                        <label>Phone</label>
                        <input type="phone" name="phone" class="form-control" id="phoneEdit" placeholder="Type phone:">
                    </div>
                    <div class="form-group">
                        <label>Salary</label>
                        <input type="number" name="salary" id="salaryEdit" class="form-control" placeholder="Type salary:">
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <select name="status" class="form-select" aria-label="Default select example">
                            <option value="0">Locked</option>
                            <option value="1">Unlock</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" name="submit" class="btn btn-success" value="Edit">
                </div>
            </form>
        </div>
    </div>
</div>
<%--Deposit Modal--%>
<div id="depositModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action ="/transfer" method="post" id="deposit">
                <div class="modal-header">
                    <h4 class="modal-title">Deposit</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <input class="inputPass" type="number" name="deposit" placeholder="Deposit" required>
                </div>
                <input type="text" id="idDeposit" name="id" style="display: none">
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" name="submit" class="btn btn-danger" value="Deposit">
                </div>
            </form>
        </div>
    </div>
</div>
<%--Withdraw Modal--%>
<div id="withModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action ="/transfer" method="post" id="withdraw">
                <div class="modal-header">
                    <h4 class="modal-title">Withdraw</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <input class="inputPass" type="number" name="withdraw" placeholder="Withdraw" required>
                </div>
                <input type="text" id="idWithdraw" name="id" style="display: none">
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" name="submit" class="btn btn-danger" value="Withdraw">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script>


    // edit function
    $(".edit").click(function () {
        let ids = $(this).attr('data-id');
        $("#idEdit").val( ids );
        ids = $(this).attr('data-name');
        $("#nameEdit").val( ids );
        ids = $(this).attr('data-email');
        $("#emailEdit").val( ids );
        ids = $(this).attr('data-phone');
        $("#phoneEdit").val( ids );
        ids = $(this).attr('data-salary');
        $("#salaryEdit").val( ids );
        $('#editEmployeeModal').modal('show');
    });

    // delete function
    $("#tblListProducts").on("click", ".delete", function () {
        let ids = $(this).attr('data-id');
        $("#id").val( ids );
        $('#deleteEmployeeModal').modal('show');
    });
    // deposit function
    $(".deposit").click(function () {
        let ids = $(this).attr('data-id');
        $("#idDeposit").val( ids );
        $('#depositModal').modal('show');
    });
    // deposit function
    $(".withdraw").click(function () {
        let ids = $(this).attr('data-id');
        $("#idWithdraw").val( ids );
        $('#withModal').modal('show');
    });
    $(".alert").fadeTo(2000, 500).fadeOut(1000);

    // validate fuction
    $('#addModal').validate({
        rules: {
            username: {
                required : true
            },
            password: {
                required : true
            },
            repassword: {
                required : true
            },
            fullname: {
                required : true
            },
            email: {
                required : true
            },
            phone: {
                required : true
            }
        },
        message: {
            username: {
                required:"get Input!"
            },
            password: {
                required : "get Input!"
            },
            repassword: {
                required : "get Input!"
            },
            fullname: {
                required : "get Input!"
            },
            email: {
                required : "get Input!"
            },
            phone: {
                required : "get Input!"
            }
        }
    });

    $('#editModal').validate({
        rules: {
            name: {
                required : true
            },
            email: {
                required : true
            },
            phone: {
                required : true
            },
            salary:{
                required:true
            }
        },
        message: {
            name: {
                required : "get Input!"
            },
            email: {
                required : "get Input!"
            },
            phone: {
                required : "get Input!"
            },
            salary: {
                required : "get Input!"
            }

        }
    });

    $('#deposit').validate({
        rules:{
            deposit:{
                required: true
            }
        },
        message:{
            deposit:{
                required: "Get input"
            }
        }
    })

    $('#withdraw').validate({
        rules:{
            withdraw:{
                required: true
            }
        },
        message:{
            withdraw:{
                required: "Get input"
            }
        }
    })

</script>
</html>