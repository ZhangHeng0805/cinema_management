function submit() {
    var pwd1=$("#password0").val();
    var pwd2=$("#password").val();
    if (pwd1.length>5){
        if (pwd1===pwd2){
            return true;
        } else {
            alert("两次密码输入不一致");
            return false;
        }
    } else {
        alert("密码长度至少为6位");
        return false;
    }
}