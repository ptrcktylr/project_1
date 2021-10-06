const url = "http://localhost:8090/";

// Global arrays for filtering
var allReimbs = []; // stores all the reimbs when request successfully finishes
var filteredReimbs = []; // stores filtered results, resets every filter

// Filter Button
const filterButton = document.getElementById("filterButton");
filterButton.addEventListener("click", filter);

// Filter Inputs
const typeFilter = document.getElementById("type_filter");
const statusFilter = document.getElementById("status_filter");
const authorFilter = document.getElementById("author_filter");
const resolverFilter = document.getElementById("resolver_filter");

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

            // add to allReimbs array for filtering
            allReimbs.push(ticket);

            // create table data from response
            if (ticket.status_id != 1) {
                tableBody.innerHTML += `
                <tr id="ticket-${ticket.id}">
                <td>${ticket.id}</td>
                <td>${ticket.amount.toFixed(2)}</td>
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
                <td>${ticket.amount.toFixed(2)}</td>
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

        
    createLinks();

    } else {
        console.log("Not Authorized!")
    }
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
    
    if (ticket_data.status_id != 1) {

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
                                <div><strong>Resolved By: </strong> ${ticket_data.resolver.first_name + " " + ticket_data.resolver.last_name}</div>
                                <div><strong>Submitted At: </strong> ${ticket_data.submitted_at}</div>
                                <div><strong>Resolved At: </strong> ${ticket_data.resolved_at}</div>
                                <div><img class="mt-3" id="ticketImg" ${ticket_img}"></div>
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
}

async function approve(reimb_id) {
    let response = await fetch(url + "approveTicket/" + reimb_id, {method:"PATCH", credentials: "include"});
    if (response.status === 200) {
        // console.log("ticket " + reimb_id + " approved");
        location.reload();
    } else {
        console.log("Not Authorized!")
    }
}

async function deny(reimb_id) {
    let response = await fetch(url + "denyTicket/" + reimb_id, {method:"PATCH", credentials: "include"});
    if (response.status === 200) {
        // console.log("ticket " + reimb_id + " denied");
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

// filters data and stores in a global array filteredReimbs 
function filterData() {
    // array to store filtered results
    filteredReimbs = []; 

    // get inputs from fields
    let type = typeFilter.value;
    let status = statusFilter.value;
    let submitter_name = authorFilter.value;
    let resolver_name = resolverFilter.value;

    for (let i = 0; i < allReimbs.length; i++) {
        let match = true;
        // filter by type if given
        if (type) {
            if (allReimbs[i].type_id != type) {
                match = false;
            }
        }
        // filter by status if given
        if (status) {
            if (allReimbs[i].status_id != status) {
                match = false;
            }
        }
        // filter by submitter name if given
        if (submitter_name != "") {
            let full_name = (allReimbs[i].author.first_name + " " + allReimbs[i].author.last_name).toLowerCase();
            if (!full_name.includes(submitter_name.toLowerCase())) {
                match = false;
            }
        }
        // filter by resolver name if given
        if (resolver_name != "") {
            if (allReimbs[i].resolver != undefined) { // if given resolver name AND ticket doesn't have one, don't include in the filtered results
                let full_name = (allReimbs[i].resolver.first_name + " " + allReimbs[i].resolver.last_name).toLowerCase();
                if (!full_name.includes(resolver_name.toLowerCase())) {
                    match = false;
                }
            } else {
                match = false;
            }
        }

        // if match, add to filteredReimbs
        if (match) {
            filteredReimbs.push(allReimbs[i]);
        }
    }
}

// takes user input and filters data based on input, updates html to display results
function filter() {
    // filter data
    filterData();

    // reset table html
    tableBody.innerHTML = "";

    // populate table html
    for (ticket of filteredReimbs) {
        if (ticket.status_id != 1) {
            tableBody.innerHTML += `
            <tr id="ticket-${ticket.id}">
            <td>${ticket.id}</td>
            <td>${ticket.amount.toFixed(2)}</td>
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
            <td>${ticket.amount.toFixed(2)}</td>
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

    // create new links for detailed view
    createLinks();
}