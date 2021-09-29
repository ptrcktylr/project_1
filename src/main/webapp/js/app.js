const url = "http://localhost:8090/"

const loginButton = document.getElementById("loginButton");
loginButton.addEventListener("click", loginFunc);

async function loginFunc() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let user = {
        "username":username,
        "password":password,
        "user_role_id":2
    }

    console.log(user);

    let response = await fetch(url + "login", {
        method: "POST",
        body: JSON.stringify(user),
        credentials: "include" // required to ensure cookie is captured
        // future fetches will also require this "include" value to send the cookie back
    });

    // control flow based on success or failed login
    console.log(response.status);

    if (response.status === 200) {
        document.getElementById("login-row").innerText = "Welcome!";
    } else {
        document.getElementById("login-row").innerText = "Login failed!";
    }

}