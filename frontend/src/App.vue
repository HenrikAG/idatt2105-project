<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';
import ChatOverlay from '@/components/myComponents/ChatOverlay.vue';
import { computed, ref } from 'vue';
import { useUserStore } from '@/components/store/userstore';

const userstore = useUserStore();
const isLoggedIn = computed(() => !!userstore.username);
const isAdmin = computed(() => userstore.username && userstore.role === 'ADMIN');
</script>

<template>
  <header>
    <nav>
      <RouterLink to="/">Home</RouterLink> |
      <RouterLink to="/categories">Categories</RouterLink> |
      <RouterLink to="/search">Search</RouterLink> |
      <RouterLink v-if="!isLoggedIn" to="/login">Login</RouterLink>
      <RouterLink v-if="isLoggedIn" to="/my-user">{{ userstore.username }}</RouterLink>
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