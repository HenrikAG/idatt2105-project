<template>
    <div v-if="loading" class="loading">
      <p>Loading categories...</p>
    </div>
    <div v-else-if="foundCategories" class="category-grid">
      <!-- Apply directly to the v-for items -->
      <div v-for="category in categories" :key="category.name" class="category-item">
        <Category 
          :category="category" 
          @select-category="goToCategory"
        />
      </div>
    </div>
    <div v-else class="error">
      <p>Error: Unable to fetch categories.</p>
    </div>
  </template>
  
  <script lang="ts">
  import { ref, onMounted } from 'vue';
  import { useRouter } from 'vue-router';
  import axios from 'axios';
  import Category from '@/components/myComponents/Category.vue';
  
  export default {
    name: 'Categories',
    components: {
      Category
    },
    setup() {
      const categories = ref([]);
      const router = useRouter();
      const foundCategories = ref<boolean>(false);
      const loading = ref<boolean>(true); // Add loading state
      
      const fetchCategories = async () => {
        loading.value = true; // Set loading to true when starting the request
        try {
          const response = await axios.get('http://localhost:8080/api/categories');
          categories.value = response.data;
          foundCategories.value = true;
        } catch (error) {
          console.error('Error fetching categories:', error);
          foundCategories.value = false; // Explicitly set to false on error
        } finally {
          loading.value = false; // Set loading to false when request completes
        }
      };
  
      const goToCategory = (categoryName: string) => {
        router.push(`/categories/${categoryName}`);
      };
  
      onMounted(() => {
        fetchCategories();
      });
  
      return {
        categories,
        goToCategory,
        foundCategories,
        loading // Add loading to the return object
      };
    }
  };
  </script>
    
  <style scoped>
  .category-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
    padding: 20px;
  }
  
  .category-item {
    width: 100%;
  }
  
  .loading, .error {
    display: flex;
    justify-content: center;
    padding: 40px;
    font-size: 1.1rem;
  }
  
  .error {
    color: #e53935;
  }
  </style>