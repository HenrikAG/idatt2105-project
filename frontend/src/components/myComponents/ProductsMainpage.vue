<template>
    <div class="featured-products">
      <h2>Recently Listed</h2>
      <div v-if="loading" class="loading">
        <p>Loading products...</p>
      </div>
      <div v-else-if="error" class="error">
        <p>{{ error }}</p>
      </div>
      <div v-else-if="products.length === 0" class="no-products">
        <p>No products found</p>
      </div>
      <div v-else class="product-grid">
        <div v-for="product in products" :key="product.id" class="product-item">
          <Product :product="product" :user="getUser(product.username)" />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import Product from '@/components/myComponents/Product.vue';
  import { useUserStore } from '@/components/store/userstore.ts';
  
  export default {
    name: 'ProductsMainpage',
    components: {
      Product
    },
    data() {
      return {
        products: [],
        users: {},
        loading: true,
        error: null,
        userStore: useUserStore()
      };
    },
    computed: {
      isLoggedIn() {
        return this.userStore.token != null;
      }
    },
    created() {
      this.fetchRecentProducts();
    },
    methods: {
      async fetchRecentProducts() {
        this.loading = true;
        
        try {
          let response;
          
          if (this.isLoggedIn) {
            console.log("Getting favorite item")
            // If logged in, get items from user's favorite categories
            response = await axios.get(`http://localhost:8080/api/user/${this.userStore.username}/favorite-items`, {
              headers: {
                'Authorization': `Bearer ${this.userStore.token}`
              }
            });
          } else {
            console.log("Getting categoryitems")
            // If not logged in, get items from all categories
            // First, get all categories
            const categoriesResponse = await axios.get('http://localhost:8080/api/categories');
            const categories = categoriesResponse.data.slice(0, 3); // Limit to top 3 categories for recent items
            
            // Then fetch items from each category
            const itemPromises = categories.map(category => 
              axios.get(`http://localhost:8080/api/items/${category.name}`)
            );
            
            const responses = await Promise.all(itemPromises);
            
            // Combine items from all categories
            const allItems = responses.flatMap(res => res.data);
            
            response = { data: allItems };
          }
          
          // Limit to 5 items
          this.products = response.data.slice(0, 5);
          
        } catch (error) {
          console.error('Error fetching recent products:', error);
          this.error = 'Failed to load recent products';
        } finally {
          this.loading = false;
        }
      },
      
      getUser(username) {
        // Return user data or a default user object if not found
        return this.users[username] || { 
          username: username,
          validMarker: false
        };
      }
    }
  };
  </script>
  
  <style scoped>
  .featured-products {
    margin: 40px 0;
    padding: 0 20px;
  }
  
  h2 {
    text-align: center;
    margin-bottom: 20px;
    font-size: 1.8rem;
    color: #333;
  }
  
  .loading, .error, .no-products {
    text-align: center;
    padding: 30px;
    color: #666;
    background-color: #f8f8f8;
    border-radius: 8px;
    margin: 20px 0;
  }
  
  .error {
    color: #e53935;
    background-color: #ffebee;
  }
  
  .product-grid {
    display: block;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
  }
  
  .product-item {
    display: flex;
    justify-content: left;
    padding: 10px;
  }
  </style>