const url = "http://localhost:8090/";

// Employee Buttons
let newReimButton = document.getElementById("newReimbursement");
newReimButton.addEventListener("click", function () {
    window.location = "../html/new_reimbursement.html"
});

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
allPendingReims.addEventListener("click", function () {
    window.location = "../html/pending_reimbursements.html"
});

let allReims = document.getElementById("allReimbursements");
allReims.addEventListener("click", function () {
    window.location = "../html/reimbursements.html"
});