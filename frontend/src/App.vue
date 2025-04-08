<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';
import ChatOverlay from '@/components/myComponents/ChatOverlay.vue';
import { computed } from 'vue';

const isLoggedIn = computed(() => !!localStorage.getItem("token"));
const isAdmin = computed(() => localStorage.getItem("role") === "admin");
const username = computed(() => {
  const user = localStorage.getItem("user");
  return user ? JSON.parse(user).username : '';
});
</script>

<template>
  <header>
    <nav>
      <RouterLink to="/">Home</RouterLink> |
      <RouterLink to="/categories">Categories</RouterLink> |
      <RouterLink to="/search">Search</RouterLink> |
      <RouterLink v-if="!isLoggedIn" to="/login">Login</RouterLink>
      <RouterLink v-if="isLoggedIn" to="/my-user">{{ username }}</RouterLink>
      <RouterLink v-if="isAdmin" to="/admin">   Admin</RouterLink>
    </nav>
  </header>
  <main>
    <RouterView />
  </main>
  <footer> 
    <ChatOverlay />
  </footer>
</template>

<style scoped>
</style>