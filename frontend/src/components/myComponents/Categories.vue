<template>
    <div class="category-grid">
      <div v-if="foundCategories">
        <!-- Move the Category component inside the v-for loop -->
        <div v-for="category in categories" :key="category.name" class="category-item">
          <Category 
            :category="category" 
            @select-category="goToCategory"
          />
        </div>
      </div>
      <div v-else>
        <p>Error: Unable to fetch categories.</p>
      </div>
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
  
      const fetchCategories = async () => {
        try {
          // Changed to use the full URL, matching your other components
          const response = await axios.get('http://localhost:8080/api/categories');
          categories.value = response.data;
          foundCategories.value = true;
        } catch (error) {
          console.error('Error fetching categories:', error);
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
        foundCategories // Added foundCategories to the return object
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
  </style>