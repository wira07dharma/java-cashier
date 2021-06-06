<%-- 
    Document   : cashier
    Created on : Jul 27, 2017, 8:44:22 PM
    Author     : Regen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="./../styles/bootstrap3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="./../styles/bootstrap3.1/css/AdminLTE.css">
    <style>
      .padding-0 { padding: 0 !important; padding-top: 0 !important; padding-bottom: 0 !important; padding-right: 0 !important; padding-left: 0 !important; }
      .qty-number { padding: 15px; width: 100%; font-size: 16px; cursor: pointer; font-weight: bold; text-align: center; }
      .default { background: #f6f8f9; /* Old browsers */ background: -moz-linear-gradient(top, #f6f8f9 0%, #e5ebee 50%, #d7dee3 51%, #f5f7f9 100%); /* FF3.6-15 */ background: -webkit-linear-gradient(top, #f6f8f9 0%,#e5ebee 50%,#d7dee3 51%,#f5f7f9 100%); /* Chrome10-25,Safari5.1-6 */ background: linear-gradient(to bottom, #f6f8f9 0%,#e5ebee 50%,#d7dee3 51%,#f5f7f9 100%); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */ filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#f6f8f9', endColorstr='#f5f7f9',GradientType=0 ); /* IE6-9 */ outline: none !important; border: 1px solid #b5b5b5; }
      .menu-button { height: 55px; width: 100%;  }
      .padding-bottom-2 { padding-bottom: 2px; }
    </style>

    <!-- Latest compiled and minified JavaScript -->
    <script src="./../styles/bootstrap3.1/js/jquery.min.js"></script>
    <script src="./../styles/bootstrap3.1/js/jquery-ui-1.10.3.min.js"></script>
    <script src="./../styles/bootstrap3.1/js/bootstrap.min.js"></script>
  </head>
  <body>
    <div class="container-fluid" style="position: absolute; width: calc(100% - 10px); height: calc(100% - 10px); padding: 5px;">
      <div style="background-color: white; position: absolute; height: 100%;" class="col-xs-3 padding-0">
        <div style="width: calc(100% - 50px);">
          <div style="width: 100%; padding-right: 1px; left: 0;">
            <input class="form-control" />
          </div>
          <table class="table table-bordered">
            <thead>
              <tr>
                <th>Description</th>
                <th>#</th>
                <th><span class="pull-right">Amount</span></th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Fried Rice Omellete</td>
                <td></td>
                <td><span class="pull-right">35,750</span></td>
              </tr>
            </tbody>
          </table>
          <div style="position: absolute; bottom: 0; width: 80%; padding: 10px; background-color: black;">
            <strong style="color: white;">TOTAL</strong>
            <div class="pull-right"><span style="font-size: 25px; color: #5fff6c;">189,970</span></div>
          </div>
        </div>
        <div style=" width: 45px; position: absolute; right: 0; top: 0; margin-left: 5px;" class="padding-0">
          <button class="qty-number">0</button>
          <button class="qty-number">1</button>
          <button class="qty-number">2</button>
          <button class="qty-number">3</button>
          <button class="qty-number">4</button>
          <button class="qty-number">5</button>
          <button class="qty-number">6</button>
          <button class="qty-number">7</button>
          <button class="qty-number">8</button>
          <button class="qty-number">9</button>
          <button class="qty-number">R</button>
        </div>
      </div>
      <div style="right: 0; position: absolute;" class="col-xs-9 padding-0">
        <div>
          <div style="padding: 0 15px !important;" class="col-xs-10 padding-0">
            <div style="padding-left: 2px; padding-right: 2px" class="col-xs-3 padding-bottom-2"><button class="menu-button">Oxtail Soup</button></div>
            <div style="padding-left: 2px; padding-right: 2px" class="col-xs-3 padding-bottom-2"><button class="menu-button">Oxtail Soup</button></div>
            <div style="padding-left: 2px; padding-right: 2px" class="col-xs-3 padding-bottom-2"><button class="menu-button">Oxtail Soup</button></div>
            <div style="padding-left: 2px; padding-right: 2px" class="col-xs-3 padding-bottom-2"><button class="menu-button">Oxtail Soup</button></div>
            <div style="padding-left: 2px; padding-right: 2px" class="col-xs-3 padding-bottom-2"><button class="menu-button">Oxtail Soup</button></div>
            <div style="padding-left: 2px; padding-right: 2px" class="col-xs-3 padding-bottom-2"><button class="menu-button">Oxtail Soup</button></div>
            <div style="padding-left: 2px; padding-right: 2px" class="col-xs-3 padding-bottom-2"><button class="menu-button">Oxtail Soup</button></div>
            <div style="padding-left: 2px; padding-right: 2px" class="col-xs-3 padding-bottom-2"><button class="menu-button">Oxtail Soup</button></div>
            <div style="padding-left: 2px; padding-right: 2px" class="col-xs-3 padding-bottom-2"><button class="menu-button">Oxtail Soup</button></div>
          </div>
          <div class="col-xs-2 padding-0">
            <button class="qty-number default">Dessert</button>
            <button class="qty-number default">Drinks</button>
            <button class="qty-number default">Extra</button>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
