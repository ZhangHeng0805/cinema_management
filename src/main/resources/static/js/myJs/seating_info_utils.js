//解析座位的json数据
function jsonToList(json) {
    var s=json.replace('[','').replace(']','').replaceAll("\"","");
    // console.log(s);
    var arr=s.split(",");
    // console.log(arr.length);
    return arr;
}
//初始化界面
function writeHtml() {
    li.splice(0,li.length);//清空数组
    sitVal();//
    var json=$("#s1").val();
    // console.log(json);
    var arr=jsonToList(json);
    // console.log(arr);
    var html="";
    for (var i=0;i<arr.length;i++) {
        var s=(i+1).toString();
        while (s.length<(arr.length).toString().length){
            s="0"+s;
        }
        if (arr[i] === 't') {
            html += '<button class="btn btn-success" id="B'+i+'" onclick="cli(' + i + ');return false;">['+s+']有座</button>';
        } else if (arr[i] === 'f') {
            html += '<button class="btn btn-danger" id="B'+i+'" disabled>['+s+']无座</button>';
        }
        if ((i+1) % 10 === 0 ) {
            html += "<br>"
        }
    }
    // console.log(html);
    document.getElementById("label-info").innerText="请选择座位：";
    document.getElementById("panel-body").innerHTML=html;
}
var li=new Array();
//选座的点击事件
function cli(i) {
    var btn=document.getElementById("B"+i);

    // console.log(i);
    var btn=document.getElementById("B"+i);
    var cla=btn.getAttribute("class");
    // console.log(btn.getAttribute("class"));
    if (cla==="btn btn-success") {
        btn.setAttribute("class","btn btn-info");
        li.push(i)
    }else if (cla==="btn btn-info"){
        btn.setAttribute("class","btn btn-success");
        li.splice(li.indexOf(i),1)
    }
    if (li.length>0){
        var lab="已选座位：";
        for (var i=0;i<li.length;i++){
            if (i==li.length-1) {
                lab+=li[i]+1+'号.'
            }else {
                lab+=li[i]+1+'号,'
            }
        }
    }else {
        var lab="请选择座位：";
    }
    limit_sit();
    document.getElementById("label-info").innerText=lab;
    sitVal();
}
//限制选座上限
function limit_sit() {
    var itemRoom=document.getElementsByClassName("btn btn-success");
    if (li.length>=4){
        for(var n = 0; n < itemRoom.length; n++){
            itemRoom[n].setAttribute("disabled",true);
        }
        alert("注意：最多只能选4个座位");
    }else {
        for(var m = 0; m < itemRoom.length; m++){
            itemRoom[m].removeAttribute("disabled");
        }
    }
}
//将选座信息赋值给表单中input
function sitVal() {
    document.getElementById("sitNum").value=li.toString();
    total_price();

}
var total=0.00;
//计算座位数和总票价
function total_price() {
    var f=$("#fare").val();
    if (li.length>0){
        document.getElementById("sit_num").innerText=' × '+li.length;
        total=f*li.length;
       document.getElementById("sub").value="提交订单（"+total+"元）";
       document.getElementById("orderPrice").value=total;
    } else {
        document.getElementById("sit_num").innerText='';
        total=f*li.length;
        document.getElementById("sub").value="提交订单（"+total+"元）";
        document.getElementById("orderPrice").value=null;
    }
    console.log(total)
}
writeHtml();