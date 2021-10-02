const url = "http://localhost:8090/";

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
const loginButton = document.getElementById("login_button");
loginButton.addEventListener("click", function () {
    window.location = "../html/login.html";
});

var types = {"1":"lodging",
"2":"travel",
"3":"food",
"4":"other"
};
var statuses = {"1":"pending",
   "2":"approved",
   "3":"denied"
   };

const tableBody = document.getElementById("reimb-table-body");

async function getMyPendingReims() {
    let response = await fetch(url + "myPending", {credentials: "include"});

    // clear table
    tableBody.innerHTML = "";

    if (response.status === 200) {

        let data = await response.json();

        for (let ticket of data) {
            tableBody.innerHTML += `
            <tr id="ticket-${ticket.id}">
            <td>${ticket.id}</td>
            <td>${ticket.amount}</td>
            <td>${types[ticket.type_id]}</td>
            <td>${statuses[ticket.status_id]}</td>
            <td>${ticket.submitted_at}</td>
            </tr>
            `;
        }

    } else {
        console.log("Not Authorized!")
    }
}

getMyPendingReims();

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
    let response = await fetch(url + `viewTicket/${reim_id}`, {credentials: "include"});

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
    
    if (ticket_data.status_id != 1) {

        ticket_body.innerHTML = `
        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-lg-5 col-md-7">
                    <div class="card">
                        <div class="card-body">
                            <div class="text-center mb-4">
                                <h2 class="mb-4">Ticket ${ticket_data.id}</h2>
                                <div><strong>Amount: </strong>${ticket_data.amount}</div>
                                <div><strong>Author: </strong> ${ticket_data.author.first_name + " " + ticket_data.author.last_name}</div>
                                <div><strong>Status: </strong> ${statuses[ticket_data.status_id]}</div>
                                <div><strong>Description: </strong> ${ticket_desc}</div>
                                <div><strong>Resolved By: </strong> ${ticket_data.resolver.first_name + " " + ticket_data.resolver.last_name}</div>
                                <div><strong>Submitted At: </strong> ${ticket_data.submitted_at}</div>
                                <div><strong>Resolved At: </strong> ${ticket_data.resolved_at}</div>
                            </div>
                        </div>
                        <button class="btn btn-danger" onclick="back()">Close</button>
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
                                <div><strong>Amount: </strong>${ticket_data.amount}</div>
                                <div><strong>Author: </strong> ${ticket_data.author.first_name + " " + ticket_data.author.last_name}</div>
                                <div><strong>Status: </strong> ${statuses[ticket_data.status_id]}</div>
                                <div><strong>Description: </strong> ${ticket_desc}</div>
                                <div><strong>Resolved By: </strong> </div>
                                <div><strong>Submitted At: </strong> ${ticket_data.submitted_at}</div>
                                <div><strong>Resolved At: </strong> </div>
                            </div>
                        </div>
                        <button class="btn btn-danger" onclick="back()">Close</button>
                    </div>
                </div>
            </div>
        </div>
        `;
    }
}


function back() {
    ticketsContainer.hidden = false;
    ticket_body.hidden = true;
}