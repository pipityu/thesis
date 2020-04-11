$(function() {
    var dpicker = !!document.getElementById('datePicker');
    if(dpicker !== false){

        let now = new Date().getTime();
        now = new Date(now).toLocaleString().split(" ");
        let now1 = now[0].replace(".","-");
        now1 = now1.concat(now[1].replace(".","-"));
        let min = now[3].split(":");
        now1 = now1.concat(now[2].replace(".",""),"T",min[0],":",min[1]);
       // alert(now1);

        document.getElementById('datePicker').setAttribute("min", now1);//.split("T")[0]
    }

});

function status(){
    $("#status").css("color","green");
}

function openForm() {
    document.getElementById("newStepForm").style.display = "block";
}

function closeForm() {
    document.getElementById("newStepForm").style.display = "none";
}
