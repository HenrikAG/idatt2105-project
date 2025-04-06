<template>
    <div>
        <h1>Products</h1>
        <div v-for="product in products" :key="product.id">
            <Product :product="product" />
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Product from '@/components/myComponents/Product.vue';

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
                const categoryId = this.$route.params.id;
                let url = `http://localhost:8080/api/items/${categoryId}`;
                const response = await axios.get(url);
                this.products = response.data;

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