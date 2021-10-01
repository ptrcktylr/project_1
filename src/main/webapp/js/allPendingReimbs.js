const url = "http://localhost:8090/";

const tableBody = document.getElementById("reimb-table-body");

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

            if (ticket.status_id != 1) {
                tableBody.innerHTML += `
                <td>${ticket.id}</td>
                <td>${ticket.amount}</td>
                <td>${types[ticket.type_id]}</td>
                <td>${statuses[ticket.status_id]}</td>
                <td>${ticket.author.first_name + " " + ticket.author.last_name}</td>
                <td>${ticket.submitted_at}</td>
            `;
            } else {
                tableBody.innerHTML += `
                <td>${ticket.id}</td>
                <td>${ticket.amount}</td>
                <td>${types[ticket.type_id]}</td>
                <td>${statuses[ticket.status_id]}</td>
                <td>${ticket.author.first_name + " " + ticket.author.last_name}</td>
                <td>${ticket.submitted_at}</td>
                `;
            }

            
        }

    } else {
        console.log("Not Authorized!")
    }
}

getMyReims();