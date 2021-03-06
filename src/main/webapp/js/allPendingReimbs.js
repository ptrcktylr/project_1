const url = "http://localhost:8090/";

// Loading icon
var loadingIcon = document.getElementById("loadingIcon");

// Logout Button
const logoutButton = document.getElementById("logout_button");
logoutButton.addEventListener("click", logout);

// Ticket Table Container
var ticketsContainer = document.getElementById("ticketsContainer");

// Full Ticket View Container
var ticket_body = document.getElementById("ticketbody");
 
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

const tableBody = document.getElementById("reimb-table-body");

// ticket types
var types = {"1":"lodging",
            "2":"travel",
            "3":"food",
            "4":"other"
            };
var statuses = {"1":"pending",
                "2":"approved",
                "3":"denied"
                };

async function getMyReims() {
    let response = await fetch(url + "pendingTickets", {credentials: "include"});

    // clear table
    tableBody.innerHTML = "";

    if (response.status === 200) {

        let data = await response.json();
        var types = {"1":"lodging",
                     "2":"travel",
                     "3":"food",
                     "4":"other"
                    };
        var statuses = {"1":"pending",
                        "2":"approved",
                        "3":"denied"
                        };

        for (let ticket of data) {
            tableBody.innerHTML += `
            <tr id="ticket-${ticket.id}">
            <td>${ticket.id}</td>
            <td>${ticket.amount.toFixed(2)}</td>
            <td>${types[ticket.type_id]}</td>
            <td>${statuses[ticket.status_id]}</td>
            <td>${ticket.author.first_name + " " + ticket.author.last_name}</td>
            <td>${ticket.submitted_at}</td>
            `;
        }

        createLinks();

    } else {
        console.log("Not Authorized!")
    }
    loadingIcon.hidden = true;
}

getMyReims();

function createLinks() {
    allTickets = document.querySelectorAll("[id^='ticket-']");
    for (let ticket_ele of allTickets) {
        ticket_ele.addEventListener("click", function () {
            getReim(ticket_ele.id.substring(7));
        });
        ticket_ele.style.cursor = "pointer";
    }
}


async function getReim(reim_id) {
    let response = await fetch(url + `ticket/${reim_id}`, {credentials: "include"});

    if (response.status === 200) {

        let data = await response.json();

        displayTicket(data);

    } else {
        console.log("Not Authorized!")
    }
}

function displayTicket(ticket_data) {
    // clear table
    ticketsContainer.hidden = true;
    ticket_body.hidden = false;

    let ticket_desc = (ticket_data.description || " ");
    var ticket_img = null;
    if (ticket_data.receipt) {
        ticket_img = `style="width: 100%;" src="data:image/png;base64,` + bytesToBase64(ticket_data.receipt) + `"`;
    }

    ticket_body.innerHTML = `
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-7">
                <div class="card">
                    <div class="card-body">
                        <div class="text-center mb-4">
                            <h2 class="mb-4">Ticket ${ticket_data.id}</h2>
                            <div><strong>Amount: </strong>${ticket_data.amount.toFixed(2)}</div>
                            <div><strong>Author: </strong> ${ticket_data.author.first_name + " " + ticket_data.author.last_name}</div>
                            <div><strong>Status: </strong> ${statuses[ticket_data.status_id]}</div>
                            <div><strong>Description: </strong> ${ticket_desc}</div>
                            <div><strong>Resolved By: </strong> </div>
                            <div><strong>Submitted At: </strong> ${ticket_data.submitted_at}</div>
                            <div><strong>Resolved At: </strong> </div>
                            <div><img class="mt-3" id="ticketImg" ${ticket_img}"></div>
                        </div>
                        <div class="text-center">
                            <button id="approveTicketButton" class="btn btn-success me-3" onclick="approve(${ticket_data.id})">Approve</button>
                            <button id="denyTicketButton" class="btn btn-danger" onclick="deny(${ticket_data.id})">Deny</button>
                        </div>
                    </div>
                    <button class="btn btn-danger" onclick="back()">Close</button>
                </div>
            </div>
        </div>
    </div>
    `;
}

async function approve(reimb_id) {
    let response = await fetch(url + "approveTicket/" + reimb_id, {method:"PATCH", credentials: "include"});
    if (response.status === 200) {
        console.log("ticket " + reimb_id + " approved");
        location.reload();
    } else {
        console.log("Not Authorized!")
    }
}

async function deny(reimb_id) {
    let response = await fetch(url + "denyTicket/" + reimb_id, {method:"PATCH", credentials: "include"});
    if (response.status === 200) {
        console.log("ticket " + reimb_id + " denied");
        location.reload();
    } else {
        console.log("Not Authorized!")
    }
}

function back() {
    ticketsContainer.hidden = false;
    ticket_body.hidden = true;
}

function bytesToBase64(byteA) {
    let base64 = "";
    const bytes = new Uint8Array(byteA);
    for (let i = 0; i < bytes.byteLength; i++) {
        base64 += String.fromCharCode(bytes[i]);
    }
    return btoa(base64);
}