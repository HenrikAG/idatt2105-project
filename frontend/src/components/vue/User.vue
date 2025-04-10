<template>
    <div class="user">
        <p>Name: {{ user.username }}</p>
        <button v-if="userstore.role === 'ADMIN'" @click="executePressed">Delete User</button>
    </div>
</template>

<script lang="ts">
import { type DTOuser } from "@/types/DTOuser";
import { type PropType } from "vue";
import { useUserStore } from "@/components/store/userstore";
import axios from "axios";
export default {
    name: "User",

    data() {
        return {
            user: {} as DTOuser,
            userstore: useUserStore(),
        };
    },
    props: {
        DTOuser: {
            type: Object as PropType<DTOuser>,
            required: true,
        },
    },
    created() {
        this.user = this.DTOuser;
    },
    methods: {
        executePressed() : void {
            // Logic to handle account deletion
            if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
                axios.delete(`http://localhost:8080/api/user/${this.userstore.username}`, {
                    headers: {
                        Authorization: `Bearer ${this.userstore.token}`,
                    },
                })
                .then(() => {
                    alert("Account deleted successfully.");
                })
                .catch(error => {
                    console.error("Error deleting account:", error);
                    alert("Failed to delete account.");
                });
            }
        },
    }

};
</script>

<style scoped>
/* Add your styles here */
</style>