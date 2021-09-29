const url = "http://localhost:8090/";

const tableBody = document.getElementById("reimb-table-body");

async function getMyReims() {
    let response = await fetch(url + "myTickets", {credentials: "include"});

    // clear table
    tableBody.innerHTML = "";

    if (response.status === 200) {

        let data = await response.json();

        for (let ticket of data) {
            tableBody.innerHTML += `
            <td>${ticket.id}</td>
            <td>${ticket.amount}</td>
            <td>${ticket.type_id}</td>
            <td>${ticket.status_id}</td>
            `;
        }

    } else {
        console.log("Not Authorized!")
    }
}

getMyReims();