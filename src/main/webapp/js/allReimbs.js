const url = "http://localhost:8090/";

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
    let response = await fetch(url + "allTickets", {credentials: "include"});

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

            if (ticket.status_id != 1) {
                tableBody.innerHTML += `
                <tr id="ticket-${ticket.id}">
                <td>${ticket.id}</td>
                <td>${ticket.amount}</td>
                <td>${types[ticket.type_id]}</td>
                <td>${statuses[ticket.status_id]}</td>
                <td>${ticket.author.first_name + " " + ticket.author.last_name}</td>
                <td>${ticket.resolver.first_name + " " + ticket.resolver.last_name}</td>
                <td>${ticket.submitted_at}</td>
                <td>${ticket.resolved_at}</td>
                </tr>
            `;
            } else {
                tableBody.innerHTML += `
                <tr id="ticket-${ticket.id}">
                <td>${ticket.id}</td>
                <td>${ticket.amount}</td>
                <td>${types[ticket.type_id]}</td>
                <td>${statuses[ticket.status_id]}</td>
                <td>${ticket.author.first_name + " " + ticket.author.last_name}</td>
                <td></td>
                <td>${ticket.submitted_at}</td>
                <td></td>
                </tr>
                `;
            }
            
        }

    } else {
        console.log("Not Authorized!")
    }
}

getMyReims();

function createLinks() {
    setTimeout(function () {
        allTickets = document.querySelectorAll("[id^='ticket-']");
        for (let ticket_ele of allTickets) {
            ticket_ele.addEventListener("click", function () {
                getReim(ticket_ele.id.substring(7));
            });
            ticket_ele.style.cursor = "pointer";
        }
    }, 3000)
}

createLinks();


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
    var ticket_body = document.getElementById("ticketbody");

    let ticket_desc = (ticket_data.description || " ");
    
    if (ticket_data.status_id != 1) {

        ticket_body.innerHTML = `
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-lg-5 col-md-7">
                    <div class="card">
                        <div class="card-body">
                            <div class="text-center mb-4">
                                <h2 class="mb-4">Ticket ${ticket_data.id}</h2>
                                <div>Amount: ${ticket_data.amount}</div>
                                <div>Author: ${ticket_data.author.first_name + " " + ticket_data.author.last_name}</div>
                                <div>Status: ${statuses[ticket_data.status_id]}</div>
                                <div>Description: ${ticket_desc}</div>
                                <div>Resolved By: ${ticket_data.resolver.first_name + " " + ticket_data.resolver.last_name}</div>
                                <div>Submitted At: ${ticket_data.submitted_at}</div>
                                <div>Resolved At: ${ticket_data.resolved_at}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        `;
    } else {
        ticket_body.innerHTML = `
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-lg-5 col-md-7">
                    <div class="card">
                        <div class="card-body">
                            <div class="text-center mb-4">
                                <h2 class="mb-4">Ticket ${ticket_data.id}</h2>
                                <div>Amount: ${ticket_data.amount}</div>
                                <div>Author: ${ticket_data.author.first_name + " " + ticket_data.author.last_name}</div>
                                <div>Status: ${statuses[ticket_data.status_id]}</div>
                                <div>Description: ${ticket_desc}</div>
                                <div>Resolved By: </div>
                                <div>Submitted At: ${ticket_data.submitted_at}</div>
                                <div>Resolved At: </div>
                            </div>
                            <div class="text-center">
                                <button id="approveTicketButton" class="btn btn-success" onclick="approve(${ticket_data.id})">Approve</button>
                                <button id="denyTicketButton" class="btn btn-danger" onclick="deny(${ticket_data.id})">Deny</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `;
    }
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