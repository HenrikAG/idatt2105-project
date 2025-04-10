<template>
    <div class="home-view">
        <h1>Welcome to HomeView</h1>
        <div v-if="userStore.token && userStore.token !== '' && categoryFavorites.length > 0" class="show-favorites">
            <ProductList 
            v-for="category in categoryFavorites" 
            :key="category" 
            :fetchType="'category'" 
            :category="category" 
            />
        </div>
        <div v-else class="show-all">
            <ProductList :fetchType="'all'" />
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useUserStore } from '@/components/store/userstore';
import ProductList from '@/components/vue/ProductList.vue';

export default defineComponent({
    name: 'HomeView',
    components: {
        ProductList
    },
    data() {
        return {
            userStore: useUserStore() as any,
            categoryFavorites: [] as string[],
        };
    },
});
</script>

<style scoped>
</style>