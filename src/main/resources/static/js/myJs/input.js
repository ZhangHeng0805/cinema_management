function titleInput(input,h_title,max) {
    // alert($("#title").val().length);
    var i=$("#"+input).val().length;
    var h_t=document.getElementById(h_title);
    h_t.innerText="字数限制"+max+"字,已输入："+i+"字";
    if (i>max){
        h_t.style.color="red";
    }else {
        h_t.style.color="#4e4f6b";
    }
}