<template>
    <div class="login">
        <h1>Login</h1>
        
        <!-- Error message -->
        <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
        </div>
        
        <form @submit.prevent="handleLogin">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" v-model="username" required :disabled="isSubmitting" />
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" v-model="password" required :disabled="isSubmitting" />
            </div>
            <button type="submit" :disabled="isSubmitting">
                {{ isSubmitting ? 'Logging in...' : 'Login' }}
            </button>
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
            errorMessage: "",
            isSubmitting: false
        };
    },
    methods: {
        handleLogin() {
            // Reset error message
            this.errorMessage = '';
            
            if (!this.username || !this.password) {
                this.errorMessage = 'Please enter both username and password';
                return;
            }
            
            this.isSubmitting = true;
            
            axios.post("http://localhost:8080/api/user/login", {
                username: this.username,
                password: this.password,
            })
            .then((response) => {
                console.log("Login successful:", response.data);
                // Handle successful login (e.g., redirect to homepage)
                this.$router.push("/");

                localStorage.setItem("token", response.data.token);
                localStorage.setItem("username", this.username);
                localStorage.setItem("role", response.data.role);

            })
            .catch((error) => {
                console.error("Login failed:", error);
                
                // Handle different error types
                if (error.response) {
                    // The server responded with an error status code
                    const status = error.response.status;
                    
                    if (status === 401) {
                        this.errorMessage = 'Invalid username or password';
                    } else if (status === 400) {
                        this.errorMessage = error.response.data.message || 'Invalid login data';
                    } else {
                        this.errorMessage = 'Login failed. Please try again.';
                    }
                } else if (error.request) {
                    // The request was made but no response was received
                    this.errorMessage = 'No response from server. Please check your connection.';
                } else {
                    // Something else caused the error
                    this.errorMessage = 'Login error. Please try again.';
                }
            })
            .finally(() => {
                this.isSubmitting = false;
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

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: normal;
}

.form-group input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.login button {
    width: 100%;
    padding: 10px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
}

.login button:hover:not(:disabled) {
    background-color: #0056b3;
}

.login button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
}

.error-message {
    background-color: #f2dede;
    color: #a94442;
    padding: 15px;
    margin-bottom: 20px;
    border-radius: 4px;
    border: 1px solid #ebccd1;
}
</style>