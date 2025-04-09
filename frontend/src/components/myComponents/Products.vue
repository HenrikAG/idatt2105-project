<template>
    <div class="products-container">
      <h1>Products</h1>
      <div class="products-grid">
        <div v-for="product in products" :key="product.item_id" class="product-grid-item">
            <Product :product="product" :user="currentUser" />
        </div>
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
        userStore: useUserStore(),
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
      async fetchProductsSearch(searchString: string) {
        try {
          const category = this.$route.params.id;
          const userStore = useUserStore();
          const token = userStore.token;
          let url = `http://localhost:8080/api/items/${category}`;
          axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
          const response = await axios.get(url);
          this.products = response.data.filter((product) => {
            return product.item_name.toLowerCase().includes(searchString.toLowerCase());
          });
        } catch (error) {
          console.error('Error fetching products:', error);
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .products-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }
  
  h1 {
    text-align: center;
    margin: 30px 0;
  }
  
  .products-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
  }
  
  .product-grid-item {
    min-height: 350px;
    display: flex;
  }
  </style>