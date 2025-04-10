<template>
    <div class="user-list">
        <User v-for="user in DTOuser" :key="user.id" :DTOuser="user" />
    </div>
</template>

<script lang="ts">
import axios from "axios";
import { type DTOuser } from "@/types/DTOuser";
import { useRoute } from "vue-router";
import User from "@/components/vue/User.vue";
import { useUserStore } from "@/components/store/userstore";

export default {
    name: "UserList",
    data() {
        return {
            DTOuser: [] as DTOuser[],
        };
    },
    components: {
        User,
    },
    created() {
        this.fetchUsers();
    },
    methods: {
        async fetchUsers() {
            try {
                axios.get('http://localhost:8080/api/user/all')
                    .then((response) => {
                        this.DTOuser = response.data;
                    })
                    .catch((error) => {
                        console.error("Error fetching users:", error);
                    });
            } catch (error) {
                console.error("Error fetching users:", error);
            }
        },
    },
};
</script>

<style scoped>
</style>