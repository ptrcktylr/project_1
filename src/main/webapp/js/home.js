const url = "http://localhost:8090/";

// Employee Buttons
let newReimButton = document.getElementById("newReimbursement");

let myPendingReims = document.getElementById("myPendingReimbursements");

let myReims = document.getElementById("myReimbursements");
myReims.addEventListener("click", function () {
    window.location = "../html/reimbursements.html"
})

// Manager Buttons
let allPendingReims = document.getElementById("allPendingReimbursements");

let allReims = document.getElementById("allReimbursements");