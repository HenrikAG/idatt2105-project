<template>
    <div class="admin-user-view">
        <h1>Admin User Management</h1>
        <UserList :isAdmin="true" />
    </div>
</template>

<script lang="ts">
import UserList from '@/components/vue/UserList.vue';
export default {
    name: "AdminUserView",
    components: {
        UserList,
    },
};
</script>

<style scoped>
</style>