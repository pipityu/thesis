$(function() {
    var dpicker = !!document.getElementById('datePicker');
    if(dpicker !== false){
        document.getElementById('datePicker').setAttribute("min", new Date().toISOString().split("T")[0]);
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
