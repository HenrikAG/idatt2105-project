<template>
    <div>
        <h1>Products</h1>
        <div v-for="product in products" :key="product.item_id">
            <Product :product="product" />
        </div>
    </div>
</template>

<script lang="ts">
import axios from 'axios';
import Product from '@/components/myComponents/Product.vue';
import { useUserStore } from '../store/userstore';

export default {
    name: 'ProductsView',
    components: {
        Product,
    },
    data() {
        return {
            products: [],
        };
    },
    created() {
        this.fetchProducts();
    },
    methods: {
        async fetchProducts() {
            try {
                const category = this.$route.params.id;
                const userStore = useUserStore();
                const token = userStore.token;
                let url = `http://localhost:8080/api/items/${category}`;
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
                const response = await axios.get(url);
                this.products = response.data;
            } catch (error) {
                console.error('Error fetching products:', error);
            }
        },
        async fetchProductsSearch(searchString : string) {
            try {
                const category = this.$route.params.id;
                const userStore = useUserStore();
                const token = userStore.token;
                let url = `http://localhost:8080/api/items/${category}`;
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
                const response = await axios.get(url);
                this.products = response.data.filter((product: { name: string }) => {
                    return product.name.toLowerCase().includes(searchString.toLowerCase());
                });
            } catch (error) {
                console.error('Error fetching products:', error);
            }
        },
    },
};
</script>

<style scoped>
/* Add your styles here */
</style>