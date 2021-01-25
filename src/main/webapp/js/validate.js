//注册界面校验
$(function () {
    $("#registForm").validate({
        rules:{
            "username":{
                "required":true,
                 "validateName":true
            },
            "password":{
                "required":true,
                "rangelength":[6,12]
            },
            "confirmpwd":{
                "requried":true,
                "equalTo":"#password"
            },
            "sex":{
                "required":true
            },
            "email":{
                "requried":true,
                "email":true
            },
            "telephone":{
                "requried":true,
            },
            "address":{
                "requried":true,
            },
            "name":{
                "requried":true,
            },
           " birthday":{
               "requried":true,
               "date":true
            }

        },
        message:{
            "username":{
                "required":"昵称必填",
                "validateName":"此昵称已存在"
            },
            "password":{
                "required":"密码必填",
                "rangelength":"密码长度为6~12"
            },
            "confirmpwd":{
                "requried":"确认密码必填",
                "equalTo":"两次输入的密码要一致"
            },
            "sex":{
                "required":"性别必填"
            },
            "email":{
                "requried":"邮箱必填",
                "email":"邮箱格式不正确"
            },
            "telephone":{
                "requried":"电话必填",

            },
            "address":{
                "requried":"地址必填",
            },
            "name":{
                "requried":"姓名必填",
            },
            " birthday":{
                "requried":"生日必填",
                "date":"生日格式不正确"

            }
        },
        errorPlacement: function (error, element) { //指定错误信息位置
            if (element.is(':radio') || element.is(':checkbox')) { //如果是radio或checkbox
                //将错误信息添加当前元素的祖父结点后面

                error.appendTo(element.parent().parent()); }
            else {
                //将错误信息直接插入到当前元素的后面
                error.insertAfter(element);
            }
        }
    });
});

//自定义昵称校验规则

$.validator.addMethod("validateName",function (value,element,params){
    var flag=false;
    $.ajax({
        async:false,
        type:"post",
        url:$("#path").val()+"/user?method=validate",
        data:{"username":value},
        dataType:"json",
        success:function (rs) {
            flag=rs.flag;
        }

    });
    return !flag;//flag  true代表存在此昵称
})
