<template>
    <div class="my-profile-view">
        <Profile />
    </div>
</template>

<script lang="ts">
import Profile from "@/components/vue/Profile.vue";
export default {
    name: "MyProfileView",
    components: {
        Profile,
    },
    data() {
        return {
            // Define any data properties here if needed
        };
    },
    methods: {
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
</style>