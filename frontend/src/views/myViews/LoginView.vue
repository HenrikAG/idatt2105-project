<script setup lang="ts">
import Login from '@/components/myComponents/Login.vue';
import Register from '@/components/myComponents/Register.vue';
</script>

<template>
  <main>
    <div>
        <Login />
    </div>
    <h1 id="sep">
        OR
    </h1>
    <div>
        <Register />
    </div>
  </main>
</template>

<style scoped>
    #sep {
        text-align: center;
        margin: 1rem 0;
    }
</style>