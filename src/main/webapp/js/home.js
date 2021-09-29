const url = "http://localhost:8090/";

// Employee Buttons
let newReimButton = document.getElementById("newReimbursement");

let myPendingReims = document.getElementById("myPendingReimbursements");
myPendingReims.addEventListener("click", function () {
    window.location = "../html/my_pending_reimbursements.html"
});

let myReims = document.getElementById("myReimbursements");
myReims.addEventListener("click", function () {
    window.location = "../html/my_reimbursements.html"
});

// Manager Buttons
let allPendingReims = document.getElementById("allPendingReimbursements");

let allReims = document.getElementById("allReimbursements");