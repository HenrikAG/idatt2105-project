<template>

  <!-- Product card component that displays product details and seller information -->
  <!-- The card expands to show more details when clicked -->
  <div class="product-card" :class="{ expanded: isExpanded }" @click="toggleExpand">
    <div class="product-preview">
      <img v-if="product.image_url" :src="product.image_url" alt="Product image" class="product-image">
      <div v-else class="product-image placeholder-image"></div>
      <div class="preview-details">
        <h3>{{ product.item_name }}</h3>
        <p class="price">{{ product.price }}$</p>
      </div>
    </div>
    
  <!-- Expanded content section starts here -->
    <div v-if="isExpanded" class="expanded-content">
      <p class="description">{{ product.description }}</p>
      <div class="product-details">
        <p class="category">Category ID: {{ product.category }}</p>
        <p class="product-id">Product ID: {{ product.item_id }}</p>
      </div>
      
      <div class="user-details">

        <p class="user">Seller: {{ product.username }}</p>
        <button v-if="userStore.username !== product.username" id="contact-seller" @click="executeContactSeller(userData.name || props.product.username)">Contact Seller</button>
        <button 
          id="delete-product" 
          v-if="userStore.role === 'ADMIN'" 
          @click="(event) => executeDeleteProduct(product.item_id, event)"
        >
          Delete Product
        </button>
      
      </div>
    </div>
  </div>
</template>
  
<script setup lang="ts">
import { ref } from 'vue'
import type { Product } from '@/types/Product'
import type { User } from '@/types/User'
import { useChatStore } from '@/components/store/chatstore.ts'
import { useUserStore } from '@/components/store/userstore.ts'
import axios from 'axios'

// Define props that accept a Product and a User object
const props = defineProps<{
  product: Product,
  user: User
}>()

// State to track if the product details are expanded
const isExpanded = ref(false)
  
// Define local state for user data that can be updated
const userData = ref<User>({ ...props.user });
const chatStore = useChatStore()
const userStore = useUserStore()

// Toggle the expanded state
const toggleExpand = () => {
  isExpanded.value = !isExpanded.value;
}


const executeContactSeller = (username: string) => {
  // Open chat with seller and include product context
  chatStore.openChat(username, {
    id: props.product.item_id,
    name: props.product.item_name,
    price: props.product.price
  });
  
  // Prevent the card from toggling when clicking the button
  event?.stopPropagation();
}

const executeDeleteProduct = (productId: number, event?: MouseEvent) => {
  // Stop event propagation to prevent the card from toggling
  event?.stopPropagation();
  
  // Function to delete the product
  axios.delete(`http://localhost:8080/api/items/${productId}`, {
    headers: {
      Authorization: `Bearer ${userStore.token}`
    }
  })
    .then(() => {
      alert('Product deleted successfully');
    })
    .catch(error => {
      console.error('Error deleting product:', error);
      alert('Failed to delete product');
    });
}
</script>

<style scoped>
.product-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  max-width: 500px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s ease;
}

.product-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.product-card.expanded {
  max-width: 600px;
}

.product-preview {
  display: flex;
  align-items: center;
  gap: 16px;
}

.product-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
}

.placeholder-image {
  background-color: #f0f0f0;
  width: 100px;
  height: 100px;
  border-radius: 4px;
}

.preview-details {
  flex: 1;
}

.preview-details h3 {
  margin: 0 0 8px 0;
  font-size: 1.2rem;
}

.price {
  font-weight: bold;
  color: #e53935;
  margin: 0;
  font-size: 1.1rem;
}

.expanded-content {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid #eee;
  animation: expand 0.3s ease-out;
}

.description {
  color: #666;
  margin-bottom: 16px;
}

.product-details {
  display: grid;
  gap: 8px;
  margin-bottom: 16px;
}

.user-details {
  background-color: #f9f9f9;
  padding: 12px;
  border-radius: 4px;
  margin-top: 16px;
}

.user-details h2 {
  font-size: 1.1rem;
  margin-top: 0;
  margin-bottom: 8px;
}

.user-image {
  max-width: 80px;
  border-radius: 50%;
  margin-top: 8px;
}

#contact-seller {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

#delete-product {
  background-color: #e53935;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

@keyframes expand {
  from {
    opacity: 0;
    max-height: 0;
  }
  to {
    opacity: 1;
    max-height: 1000px;
  }
}
</style>