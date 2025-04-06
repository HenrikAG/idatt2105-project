<template>
    <div class="login">
        <h1>Login</h1>
        <form @submit.prevent="handleLogin">
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" v-model="username" required />
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" v-model="password" required />
            </div>
            <button type="submit">Login</button>
        </form>
    </div>
</template>

<script>
import axios from "axios";
export default {
    name: "Login",
    data() {
        return {
            username: "",
            password: "",
        };
    },
    methods: {
        handleLogin() {
            console.log("Logging in with", this.username, this.password);
            axios
                .post("http://localhost:8080/api/login", {
                    username: this.username,
                    password: this.password,
                })
                .then((response) => {
                    console.log("Login successful:", response.data);
                    // Handle successful login (e.g., redirect to homepage)
                    this.$router.push("/");
                })
                .catch((error) => {
                    console.error("Login failed:", error);
                    // Handle login failure (e.g., show error message)
                    alert("Login failed. Please check your credentials.");
                });
        },
    },
};
</script>

<style scoped>
.login {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.login h1 {
    text-align: center;
    margin-bottom: 20px;
}

.login form div {
    margin-bottom: 15px;
}

.login label {
    display: block;
    margin-bottom: 5px;
}

.login input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
}

.login button {
    width: 100%;
    padding: 10px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.login button:hover {
    background-color: #0056b3;
}
</style>