<template>
    <div class="products-container">
      <h1>Products in {{ categoryName }}</h1>
      
      <div v-if="loading" class="loading-state">
        <p>Loading products...</p>
      </div>
      
      <div v-else-if="error" class="error-state">
        <p>{{ error }}</p>
      </div>
      
      <div v-else-if="products.length === 0" class="empty-state">
        <p>No products found in this category.</p>
      </div>
      
      <div v-else class="products-grid">
        <div v-for="product in products" :key="product.item_id" class="product-grid-item">
          <Product :product="product" />
        </div>
      </div>
    </div>
  </template>
  
  <script>
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
        loading: true,
        error: null,
        categoryName: ''
      };
    },
    created() {
      this.fetchProducts();
    },
    watch: {
      // Re-fetch products when route changes
      '$route.params.id': function() {
        this.fetchProducts();
      }
    },
    methods: {
      async fetchProducts() {
        this.loading = true;
        this.error = null;
        
        try {
          const category = this.$route.params.id;
          this.categoryName = category;
          
          if (!category) {
            throw new Error('No category specified');
          }
          
          const response = await axios.get(`http://localhost:8080/api/items/${category}`, {
            headers: { 
              'Authorization': `Bearer ${this.userStore.token}` 
            }
          });
          
          const searchParam = this.$route.query.search ? String(this.$route.query.search).toLowerCase() : null;
          
          this.products = searchParam
            ? response.data.filter(product => 
                product.item_name.toLowerCase().includes(searchParam))
            : response.data;
        } catch (error) {
          console.error('Error fetching products:', error);
          this.error = 'Failed to load products. Please try again later.';
        } finally {
          this.loading = false;
        }
      }
    },
  };
  </script>
  
  <style scoped>
  /* Keep your existing styles */
  .loading-state, .error-state, .empty-state {
    text-align: center;
    padding: 40px;
    background-color: #f8f8f8;
    border-radius: 8px;
    margin: 20px 0;
  }
  
  .error-state {
    color: #e53935;
    background-color: #ffebee;
  }
  </style>