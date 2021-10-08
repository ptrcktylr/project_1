const url = "http://localhost:8090/";

// Logout Button
const logoutButton = document.getElementById("logout_button");
logoutButton.addEventListener("click", logout);

// Form elements
var amount = document.getElementById("amount");
var description = document.getElementById("description");
var type = document.getElementById("type");
var receipt = document.getElementById("receipt");
var receipt_value = null;

// Result notification
var result = document.getElementById("result");

// Get values
const submitButton = document.getElementById("newReimbButton");
submitButton.addEventListener("click", submitTicket);

const cancelButton = document.getElementById("cancelButton");
cancelButton.addEventListener("click", goHome)

async function submitTicket() {
    
    var amount_value = amount.value;
    var description_value = description.value;
    var type_value = type.value;

    let newTicket = {
        "amount":parseFloat(amount_value),
        "type_id":parseInt(type_value),
        "description":description_value,
    }

    // if there's an image, add it to the body
    if (receipt_value) {
        newTicket["receipt"] = Array.from(receipt_value);
    }

    let response = await fetch(url + "createTicket", {
        method: "POST",
        body: JSON.stringify(newTicket),
        credentials: "include"
    });

    if (response.status === 200) {
        result.innerHTML = `
        <div class="alert alert-success alert-dismissible fade show mt-2" role="alert">
            Reimbursement successfully submitted!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>`;
        resetForm();
    } else {
        result.innerHTML = `
        <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert">
            Reimbursement failed to submit! Invalid inputs!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>`;
        resetForm();
    }
}

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
// const loginButton = document.getElementById("login_button");
// loginButton.addEventListener("click", function () {
//     window.location = "../html/login.html";
// });

function goHome() {
    window.location = "../html/index.html";
}

function resetForm() {
    amount.value = "";
    description.value = "";
    receipt.value = "";

    receipt_value = null;

}

// Get receipt image
function getReceipt() {
    const file = receipt.files[0];
    const reader = new FileReader();
  
    reader.addEventListener("load", function () {
      // convert image file to base64 string
      receipt_value = convertBase64ToArrayBuffer(extractBase64(reader.result));
    }, false);
  
    if (file) {
      reader.readAsDataURL(file);
    }
}

function extractBase64(dataUrl) {
    let parts = dataUrl.split(";base64,");
    parts[0].replace("data:", "");
    let base64 = parts[1];
    return base64;
}

function convertBase64ToArrayBuffer(base64) {
    const btyeChars = atob(base64);
    const byteNums = new Array(btyeChars.length);
    for (let i = 0; i < btyeChars.length; i++) {
        byteNums[i] = btyeChars.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNums);
    return byteArray;
}