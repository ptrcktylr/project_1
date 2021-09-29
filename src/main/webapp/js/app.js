const url = "http://localhost:8090/"

const loginButton = document.getElementById("loginButton");
loginButton.addEventListener("click", loginFunc);

const sessionInfo = document.getElementById("sessionInfo");

let cookies = document.cookie;
document.getElementById("user_info").innerHTML = cookies;


async function loginFunc() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let user = {
        "username":username,
        "password":password,
        "user_role_id":2 // change this..
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
        document.getElementById("login-form").innerText = "Welcome!";
        document.cookie = `username=${user.username}; SameSite=None; Secure`
        document.cookie = `user_role_id=${user.user_role_id}; SameSite=None; Secure`
    } else {
        document.getElementById("login-form").innerText = "Login failed!";
    }

}