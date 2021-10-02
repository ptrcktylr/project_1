const url = "http://localhost:8090/";

console.log(document.cookie);
if (document.cookie) {
    var user_role_id = document.cookie.split('; ').find(row => row.startsWith('user_role_id=')).split('=')[1];
}


// Employee Buttons
if (user_role_id == 1) {
    const newReimButton = document.getElementById("newReimbursement");
    newReimButton.addEventListener("click", function () {
        window.location = "../html/new_reimbursement.html"
    });
    newReimButton.hidden = false;

    const myPendingReims = document.getElementById("myPendingReimbursements");
    myPendingReims.addEventListener("click", function () {
        window.location = "../html/my_pending_reimbursements.html"
    });
    myPendingReims.hidden = false;
    
    const myReims = document.getElementById("myReimbursements");
    myReims.addEventListener("click", function () {
        window.location = "../html/my_reimbursements.html"
    });
    myReims.hidden = false;
}


// Manager Buttons
if (user_role_id == 2) {

    const allPendingReims = document.getElementById("allPendingReimbursements");
    allPendingReims.addEventListener("click", function () {
        window.location = "../html/pending_reimbursements.html"
    });
    allPendingReims.hidden = false;

    const allReims = document.getElementById("allReimbursements");
    allReims.addEventListener("click", function () {
    window.location = "../html/reimbursements.html"
    });
    allReims.hidden = false;
}


 // Logout Button
const logoutButton = document.getElementById("logout_button");
logoutButton.addEventListener("click", logout);

async function logout() {
    let response = await fetch(url + "logout", {credentials: "include", method: "POST"});

    document.cookie = `user_id=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;
    document.cookie = `username=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;
    document.cookie = `user_role_id=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;
    document.cookie = `user_first_name=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;
    document.cookie = `user_last_name=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;

    if (response.status === 200) {
        window.location = "../html/login.html";
    } else {
        console.log("Already logged out!");
    }
}

// Login Button
const loginButton = document.getElementById("login_button");
loginButton.addEventListener("click", function () {
    window.location = "../html/login.html";
});