<template>
    <div class="profile">
        <h1>User Profile</h1>
        <p>Username: {{ userstore.username }}</p>
        <div class="profile-actions">
            <h3>Actions</h3>
            <button @click="executeLogout">Logout</button>
            <button @click="executeDeleteAccount">Delete Account</button>
            <h3>Listed Items</h3>
            <ProductList fetchType="user" :fetchQuery="userstore.username" />


        </div>
    </div>
</template>

<script lang="ts">
import { useUserStore } from "@/components/store/userstore";
import ProductList from "@/components/vue/ProductList.vue";
import axios from "axios";

export default {
    name: "Profile",
    data() {
        return {
            userstore: useUserStore(),
            listedItems: [],
        };
    },
    components: {
        ProductList,
    },
    methods: {
        executeLogout() {
            // Logic to handle logout
            this.userstore.clearUser();
            this.$router.push("/login"); // Redirect to login page after logout
        },
        executeDeleteAccount() {
            // Logic to handle account deletion
            if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
                axios.delete(`http://localhost:8080/api/user/${this.userstore.username}`, {
                    headers: {
                        Authorization: `Bearer ${this.userstore.token}`,
                    },
                })
                .then(() => {
                    alert("Account deleted successfully.");
                    this.userstore.clearUser(); // Clear user data from store
                    this.executeLogout(); // Logout after account deletion
                })
                .catch(error => {
                    console.error("Error deleting account:", error);
                    alert("Failed to delete account.");
                });
            }

        },
    },
};
</script>

<style scoped>
/* Add component-specific styles here */
</style>