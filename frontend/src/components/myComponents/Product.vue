<template>

    <!-- Product card component that displays product details and seller information -->
    <!-- The card expands to show more details when clicked -->
    <div class="product-card" :class="{ expanded: isExpanded }" @click="toggleExpand">
      <div class="product-preview">
        <img v-if="product.imageUrl" :src="product.imageUrl" alt="Product image" class="product-image">
        <div v-else class="product-image placeholder-image"></div>
        <div class="preview-details">
          <h3>{{ product.name }}</h3>
          <p class="price">{{ product.price }}$</p>
        </div>
      </div>
      
    <!-- Expanded content section starts here -->
      <div v-if="isExpanded" class="expanded-content">
        <p class="description">{{ product.description }}</p>
        <div class="product-details">
          <p class="category">Category ID: {{ product.categoryId }}</p>
          <p class="user">Seller ID: {{ product.userId }}</p>
          <p class="dates">Listed on: {{ new Date(product.createdAt).toLocaleDateString() }}</p>
        </div>
        
        <div class="user-details">
          <h2>Seller Information</h2>
          <p class="user">Name: {{ user.name }}</p>
          <p class="user">Email: {{ user.email }}</p>
          <p v-if="user.phoneNumber" class="user">Phone: {{ user.phoneNumber }}</p>
          <p v-if="user.address" class="user">Address: {{ user.address }}</p>
          <img v-if="user.imageUrl" :src="user.imageUrl" alt="User profile picture" class="user-image">
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  import type { Product } from '@/types/Product'
  import type { User } from '@/types/User'
  
  // Define props that accept a Product and a User object
  const props = defineProps<{
    product: Product,
    user: User
  }>()
  
  // State to track if the product details are expanded
  const isExpanded = ref(false)
  
  // Toggle the expanded state
  const toggleExpand = () => {
    isExpanded.value = !isExpanded.value
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