<template>
    <div class="myuser-container">
      <div class="user-profile">
        <h1>My Profile: {{ userStore.username }}</h1>
        
        <div v-if="loading" class="loading">
          <p>Loading your data...</p>
        </div>
        
        <div v-else>
          <!-- Create Product Form -->
          <div class="create-product-form">
            <h2>List a New Product</h2>
            <form @submit.prevent="createProduct">
              <div class="form-group">
                <label for="product-name">Product Name:</label>
                <input 
                  type="text" 
                  id="product-name" 
                  v-model="newProduct.item_name" 
                  required
                />
              </div>
              
              <div class="form-group">
                <label for="product-price">Price ($):</label>
                <input 
                  type="number" 
                  id="product-price" 
                  v-model="newProduct.price" 
                  min="0.01" 
                  step="0.01" 
                  required
                />
              </div>
              
              <div class="form-group">
                <label for="product-category">Category:</label>
                <select 
                  id="product-category" 
                  v-model="newProduct.category" 
                  required
                >
                  <option value="" disabled selected>-- Select a category --</option>
                  <option v-for="category in categories" :key="category.name" :value="category.name">
                    {{ category.name }}
                  </option>
                </select>
              </div>
              
              <div class="form-group">
                <label for="product-description">Description:</label>
                <textarea 
                  id="product-description" 
                  v-model="newProduct.description" 
                  rows="3"
                  required
                ></textarea>
              </div>
              
              <div class="form-group">
                <label for="product-image">Image URL:</label>
                <input 
                  type="text" 
                  id="product-image" 
                  v-model="newProduct.image_url" 
                  placeholder="https://example.com/image.jpg"
                />
                <p class="hint">Provide a direct link to your product image</p>
              </div>
              
              <button 
                type="submit" 
                class="add-product-button" 
                :disabled="isSubmitting"
              >
                {{ isSubmitting ? 'Adding...' : 'Add Product' }}
              </button>
            </form>
          </div>
          
          <!-- User's Products List -->
          <div class="user-products">
            <h2>My Listed Items</h2>
            
            <div v-if="products.length === 0" class="no-products">
              <p>You haven't listed any products yet.</p>
            </div>
            
            <div v-else class="products-list">
              <div v-for="product in products" :key="product.item_id" class="product-item">
                <div class="product-image">
                  <img v-if="product.image_url" :src="product.image_url" alt="Product image" />
                  <div v-else class="placeholder-image"></div>
                </div>
                <div class="product-info">
                  <h3>{{ product.item_name }}</h3>
                  <p class="price">${{ product.price }}</p>
                  <p class="category">{{ product.category }}</p>
                </div>
                <div class="product-actions">
                  <button class="delete-product" @click="deleteProduct(product.item_id)">Delete</button>
                </div>
              </div>
            </div>
          </div>
          
          <!-- Account Action -->
          <div class="account-actions">
            <h2>Account Actions</h2>
            <button class="logout-button" @click="logout">Logout</button>
          </div>

          <!-- Delete User Account -->
          <div class="danger-zone">
            <h2>Danger Zone</h2>
            <p>Once you delete your account, there is no going back. Please be certain.</p>
            <button class="delete-account" @click="confirmDeleteAccount">Delete My Account</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useUserStore } from '@/components/store/userstore.ts';
  import { useRouter } from 'vue-router';
  
  export default {
    name: "MyUser",
    setup() {
      const userStore = useUserStore();
      const router = useRouter();
      const products = ref([]);
      const categories = ref([]);
      const loading = ref(true);
      const isSubmitting = ref(false);
      const error = ref(null);
      
      const newProduct = ref({
        item_name: '',
        price: '',
        category: '',
        description: '',
        image_url: ''
      });
      
      // Fetch user's products
      const fetchUserProducts = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/api/items/user/${userStore.username}`, {
            headers: { Authorization: `Bearer ${userStore.token}` }
          });
          products.value = response.data;
        } catch (err) {
          console.error('Error fetching products:', err);
          error.value = 'Failed to load your products';
        }
      };
      
      // Fetch available categories
      const fetchCategories = async () => {
        try {
          const response = await axios.get('http://localhost:8080/api/categories');
          categories.value = response.data;
        } catch (err) {
          console.error('Error fetching categories:', err);
        }
      };
      
      // Create a new product
      const createProduct = async () => {
        if (isSubmitting.value) return;
        
        isSubmitting.value = true;
        
        try {
          const productData = {
            seller_name: userStore.username,
            item_name: newProduct.value.item_name,
            price: parseFloat(newProduct.value.price),
            description: newProduct.value.description,
            category: newProduct.value.category,
            image_url: newProduct.value.image_url || null
          };
          
          await axios.post('http://localhost:8080/api/items/post', productData, {
            headers: { 
              'Authorization': `Bearer ${userStore.token}`,
              'Content-Type': 'application/json'
            }
          });
          
          // Reset form
          newProduct.value = {
            item_name: '',
            price: '',
            category: '',
            description: '',
            image_url: ''
          };
          
          // Reload products
          await fetchUserProducts();
          
          alert('Product added successfully!');
        } catch (err) {
          console.error('Error creating product:', err);
          alert(err.response?.data?.message || 'Failed to add product. Please try again.');
        } finally {
          isSubmitting.value = false;
        }
      };

      // Logout user
      const logout = () => {
        userStore.clearUser();
        router.push('/login');
      };
      
      // Delete a product
      const deleteProduct = async (productId) => {
        if (!confirm('Are you sure you want to delete this product?')) return;
        
        try {
          await axios.delete(`http://localhost:8080/api/items/item/${productId}`, {
            headers: { Authorization: `Bearer ${userStore.token}` }
          });
          
          // Remove product from list
          products.value = products.value.filter(p => p.item_id !== productId);
          
          alert('Product deleted successfully');
        } catch (err) {
          console.error('Error deleting product:', err);
          alert('Failed to delete product. Please try again.');
        }
      };
      
      // Delete user account
      const confirmDeleteAccount = () => {
        const confirmed = confirm('Are you absolutely sure you want to delete your account? This action is irreversible.');
        
        if (confirmed) {
          deleteAccount();
        }
      };
      
      const deleteAccount = async () => {
        try {
          await axios.delete(`http://localhost:8080/api/user`, {
            headers: { Authorization: `Bearer ${userStore.token}` }
          });
          
          // Log out
          userStore.clearUser();
          
          // Redirect to home
          router.push('/');
          
          alert('Your account has been successfully deleted.');
        } catch (err) {
          console.error('Error deleting account:', err);
          alert('Failed to delete your account. Please try again.');
        }
      };
      
      onMounted(async () => {
        try {
          // Load both data sources in parallel
          await Promise.all([fetchUserProducts(), fetchCategories()]);
        } finally {
          loading.value = false;
        }
      });
      
      return {
        userStore,
        products,
        categories,
        loading,
        isSubmitting,
        error,
        newProduct,
        createProduct,
        deleteProduct,
        confirmDeleteAccount,
        logout,
        deleteAccount
      };
    }
  };
  </script>
  
  <style scoped>
  .myuser-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }
  
  h1 {
    margin-bottom: 20px;
  }
  
  h2 {
    margin-top: 30px;
    margin-bottom: 15px;
    font-size: 1.5rem;
  }
  
  .loading {
    text-align: center;
    padding: 40px;
    color: #666;
  }
  
  .create-product-form, .user-products, .danger-zone {
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 20px;
    margin-bottom: 30px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: normal;
  }
  
  .form-group input,
  .form-group textarea,
  .form-group select {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .hint {
    font-size: 0.8rem;
    color: #666;
    margin-top: 5px;
  }
  
  .add-product-button {
    background-color: #4CAF50;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
  }
  
  .add-product-button:hover:not(:disabled) {
    background-color: #45a049;
  }
  
  .add-product-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  
  .no-products {
    text-align: center;
    padding: 20px;
    background-color: #f8f8f8;
    border-radius: 4px;
  }
  
  .products-list {
    display: grid;
    grid-template-columns: 1fr;
    gap: 15px;
  }
  
  @media (min-width: 600px) {
    .products-list {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  .product-item {
    border: 1px solid #eee;
    border-radius: 4px;
    overflow: hidden;
    display: flex;
    flex-direction: column;
  }
  
  .product-image {
    height: 150px;
    background-color: #f5f5f5;
  }
  
  .product-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .placeholder-image {
    width: 100%;
    height: 100%;
    background-color: #eee;
  }
  
  .product-info {
    padding: 15px;
    flex-grow: 1;
  }
  
  .product-info h3 {
    margin: 0 0 10px 0;
    font-size: 1.2rem;
  }
  
  .price {
    font-weight: bold;
    color: #e53935;
  }
  
  .category {
    font-size: 0.9rem;
    color: #666;
  }
  
  .product-actions {
    padding: 0 15px 15px 15px;
  }
  
  .delete-product {
    background-color: #f44336;
    color: white;
    border: none;
    padding: 6px 12px;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .delete-product:hover {
    background-color: #d32f2f;
  }
  
  .danger-zone {
    background-color: #fff8f8;
    border-color: #ffcccc;
  }
  
  .delete-account {
    background-color: #f44336;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
  }
  
  .delete-account:hover {
    background-color: #d32f2f;
  }

  .logout-button {
  background-color: #2196F3;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  margin-right: 20px;
  transition: background-color 0.2s ease;
}

.logout-button:hover {
  background-color: #0b7dda;
}

/* Container for account actions */
.account-actions {
  display: flex;
  align-items: center;
  padding: 15px;
  margin-bottom: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
  </style>