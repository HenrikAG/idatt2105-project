<template>
    <div class="home-view">
        <h1>Welcome to HomeView</h1>
        <ProductList fetchType="all"/>
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
            
        };
    },
});
</script>

<style scoped>
.home-view {
    text-align: center;
    margin-top: 20px;
}
</style>