<template>
  <div class="categories-container">
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
    <div v-if="isAdmin" class="admin-setup">
      <h3>Admin Panel</h3>
      <div class="category-c">
        <label for="category-create">Create Category:</label>
        <input type="text" id="category-create" v-model="newCategoryName" />
        <button @click="createCategory">Create</button>
        <p v-if="createMessage" class="create-message">{{ createMessage }}</p>
      </div>
      <div class="category-d">
        <label for="category-delete">Delete Category:</label>
        <input type="text" id="category-delete" v-model="deleteCategoryName" />
        <button @click="deleteCategory">Delete</button>
        <p v-if="deleteMessage" class="delete-message">{{ deleteMessage }}</p>
      </div>
    </div>
  </div>
</template>
  
<script lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';
import Category from '@/components/myComponents/Category.vue';
import { useUserStore } from '@/components/store/userstore.ts';

export default {
  name: 'Categories',
  components: {
    Category
  },
  setup() {
    const categories = ref<{ name: string }[]>([]);
    const router = useRouter();
    const foundCategories = ref<boolean>(false);
    const loading = ref<boolean>(true); // Add loading state
    const userstore = useUserStore();
    const isAdmin = computed(() => userstore.role === 'ADMIN');
    const newCategoryName = ref<string>('');
    const deleteCategoryName = ref<string>('');
    const deleteMessage = ref<string>('');
    const createMessage = ref<string>('');
    
    const fetchCategories = async () => {
      loading.value = true; // Set loading to true when starting the request
      try {
          const response = await axios.get('http://localhost:8080/api/categories', {
          headers: {
            Authorization: `Bearer ${userstore.token}`
          }
          });
        categories.value = response.data;
        foundCategories.value = true;
      } catch (error) {
        console.error('Error fetching categories:', error);
        foundCategories.value = false; // Explicitly set to false on error
      } finally {
        loading.value = false; // Set loading to false when request completes
      }
    };

    const createCategory = async () => {
      if (!newCategoryName.value) {
        createMessage.value = 'Please enter a category name';
        setTimeout(() => { createMessage.value = ''; }, 3000);
        return;
      }
      
      // Clear any previous messages
      createMessage.value = '';
      
      try {
        await axios.post('http://localhost:8080/api/categories', {
          name: newCategoryName.value
        }, {
          headers: {
            Authorization: `Bearer ${userstore.token}`
          }
        });
        
        // Success message
        createMessage.value = `Category "${newCategoryName.value}" created successfully!`;
        newCategoryName.value = ''; // Clear input after creation
        fetchCategories(); // Refresh categories
        
        // Auto-clear success message after 3 seconds
        setTimeout(() => {
          createMessage.value = '';
        }, 3000);
        
      } catch (error : any) {
        console.error('Error creating category:', error);
        // Error message
        createMessage.value = error.response?.data?.message || 
                          'Failed to create category. Please try again.';
      }
    };

    const deleteCategory = async () => {
      if (!deleteCategoryName.value) {
        deleteMessage.value = 'Please enter a category name to delete';
        setTimeout(() => { deleteMessage.value = ''; }, 3000);
        return;
      }
      
      // Clear any previous messages
      deleteMessage.value = '';
      
      try {
        await axios.delete(`http://localhost:8080/api/categories/${deleteCategoryName.value}`, {
          headers: {
            Authorization: `Bearer ${userstore.token}`
          }
        });
        
        // Success message
        deleteMessage.value = `Category "${deleteCategoryName.value}" deleted successfully!`;
        deleteCategoryName.value = ''; // Clear input after deletion
        fetchCategories(); // Refresh categories
        
        // Auto-clear success message after 3 seconds
        setTimeout(() => {
          deleteMessage.value = '';
        }, 3000);
        
      } catch (error : any) {
        console.error('Error deleting category:', error);
        // Error message
        deleteMessage.value = error.response?.data?.message || 
                          'Failed to delete category. Please try again.';
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
      loading,
      isAdmin,
      userstore,
      newCategoryName,
      deleteCategoryName,
      deleteMessage,
      createMessage,
      createCategory,
      deleteCategory
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
  @media (max-width: 600px) {
    grid-template-columns: 1fr; /* Single column on small screens */
  }
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

.admin-setup {
  margin-top: 20px;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.category-c, .category-d {
  margin-bottom: 15px;
}

.category-c label, .category-d label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.category-c input, .category-d input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 8px;
}

button {
  padding: 8px 16px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

.create-message, .delete-message {
  margin-top: 8px;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 0.9rem;
  background-color: #f5f5f5;
  color: #000000;
  border: 1px solid #616161;
}
</style>