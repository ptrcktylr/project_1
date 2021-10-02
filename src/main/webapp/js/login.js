const url = "http://localhost:8090/";

const loginButton = document.getElementById("loginButton");
loginButton.addEventListener("click", loginFunc);

const sessionInfo = document.getElementById("sessionInfo");

async function loginFunc() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let user = {
        "username":username,
        "password":password
    }

    let response = await fetch(url + "login", {
        method: "POST",
        body: JSON.stringify(user),
        credentials: "include"
    });

    if (response.status === 200) {
        let data = await response.json();

        // Set cookies
        document.getElementById("login-form").innerText = "Welcome!";
        document.cookie = `user_id=${data.id}; SameSite=None; max-age=600; Secure`;
        document.cookie = `username=${data.username}; SameSite=None; max-age=600; Secure`;
        document.cookie = `user_role_id=${data.role_id}; SameSite=None; max-age=600; Secure`;
        document.cookie = `user_first_name=${data.first_name}; SameSite=None; max-age=600; Secure`;
        document.cookie = `user_last_name=${data.last_name}; SameSite=None; max-age=600; Secure`;
        window.location = "../html/index.html";
    } else {

        // Clear cookies
        document.getElementById("login-form").innerText = "Login failed!";
        document.cookie = `user_id=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;
        document.cookie = `username=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;
        document.cookie = `user_role_id=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;
        document.cookie = `user_first_name=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;
        document.cookie = `user_last_name=; expires=Thu, 01 Jan 1970 00:00:00 GMT;SameSite=None;`;
    }

}