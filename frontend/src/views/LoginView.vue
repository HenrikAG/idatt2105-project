<template>
    <div class="login-view">
        <h1>Login</h1>
        <Login />
        <h1>OR</h1>
        <h2>Register</h2>
        <Register />
    </div>
</template>

<script>
import Login from "@/components/vue/Login.vue";
import Register from "@/components/vue/Register.vue";
import { useUserStore } from "@/components/store/userstore";
import { computed } from "vue";
import { useRouter } from "vue-router";

export default {
    name: "LoginView",
    components: {
        Login,
        Register,
    }
};
</script>

<style scoped>
</style>