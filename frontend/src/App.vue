<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';
import ChatOverlay from '@/components/myComponents/ChatOverlay.vue';
</script>

<template>
  <header>
    <nav>
      <RouterLink to="/">Home</RouterLink> |
      <RouterLink to="/categories">Categories</RouterLink> |
      <RouterLink to="/search">Search</RouterLink> |
      <RouterLink to="/login">Login</RouterLink>
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