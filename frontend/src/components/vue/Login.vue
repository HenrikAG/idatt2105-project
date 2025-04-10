<template>
    <div class="login">
        <form @submit.prevent="login">
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" v-model="DTOloginSubmit.username" required />
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" v-model="DTOloginSubmit.password" required />
            </div>
            <button type="submit">Login</button>
        </form>
    </div>
</template>

<script lang="ts">
import { useUserStore } from "@/components/store/userstore";
import { useRouter } from "vue-router";
import { type DTOloginSubmit } from "@/types/DTOlogin";
import { type DTOloginToken } from "@/types/DTOloginToken";
import axios from "axios";

// Login Forum Component
export default {
    name: "Login",
    data() {
        return {
            DTOloginSubmit: {} as DTOloginSubmit,
            DTOloginToken: {} as DTOloginToken,
            userstore: useUserStore(),
        };
    },
    methods: {
        async login() {
            try {

                // Send login request to the server
                const response = await axios.post("http://localhost:8080/api/user/login", 
                    this.DTOloginSubmit
                );
                
                // Get the token data from response
                this.DTOloginToken = response.data;
                
                // Save user data in the store
                this.userstore.saveUser(this.DTOloginToken);
                
                // Navigate to homepage or dashboard
                this.$router.push('/');

            } catch (error) {
                console.error('Login error:', error);
                alert('Login failed. Please check your credentials.');
                // Handle login error (show message to user, etc.)
            }
        }
    }
};
</script>

<style scoped>
/* Add your styles here */
</style>