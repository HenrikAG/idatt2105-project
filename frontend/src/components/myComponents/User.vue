<template>
    <!-- User card component that displays user details -->
    <!-- The card expands to show more details when clicked -->
    <div class="user-card" :class="{ expanded: isExpanded }" @click="toggleExpand">
      <div class="user-preview">
        <img v-if="user.image_url" :src="user.image_url" alt="User profile" class="user-image">
        <div v-else class="user-image placeholder-image">{{ getInitials(user.name) }}</div>
        <div class="preview-details">
          <h3>{{ user.name }}</h3>
          <p class="verification-status" :class="{ verified: user.validMarker }">
            {{ user.validMarker ? 'Verified' : 'Unverified' }}
          </p>
        </div>
      </div>
      
      <!-- Expanded content section starts here -->
      <div v-if="isExpanded" class="expanded-content">
        <div class="user-details">
          <p class="email">Email: {{ user.email }}</p>
          <p v-if="user.phoneNumber" class="phone">Phone: {{ user.phoneNumber }}</p>
          
          <div v-if="user.products && user.products.length > 0" class="user-products">
            <h4>Products Listed:</h4>
            <ul>
              <li v-for="product in user.products" :key="product.id">
                {{ product.item_name }} - ${{ product.price }}
              </li>
            </ul>
          </div>
          
          <div class="user-actions">
            <button class="contact-button" @click.stop="contactUser">Contact User</button>
            <button class="view-profile-button" @click.stop="viewUserProfile">View Full Profile</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import type { User } from '@/types/User'
  
  // Define props that accept a User object
  const props = defineProps<{
    user: User
  }>()
  
  const router = useRouter()
  
  // State to track if the user details are expanded
  const isExpanded = ref(false)
  
  // Toggle the expanded state
  const toggleExpand = () => {
    isExpanded.value = !isExpanded.value
  }
  
  // Function to get user initials for avatar placeholder
  const getInitials = (name: string): string => {
    return name
      .split(' ')
      .map(part => part.charAt(0))
      .join('')
      .toUpperCase()
      .substring(0, 2)
  }
  
  // Actions
  const contactUser = () => {
    // Logic to contact the user, e.g., open chat
    router.push(`/chat/${props.user.id}`)
  }
  
  const viewUserProfile = () => {
    router.push(`/users/${props.user.id}`)
  }
  </script>
  
  <style scoped>
  .user-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 16px;
    max-width: 500px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: all 0.3s ease;
    margin-bottom: 16px;
  }
  
  .user-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  }
  
  .user-card.expanded {
    max-width: 600px;
  }
  
  .user-preview {
    display: flex;
    align-items: center;
    gap: 16px;
  }
  
  .user-image {
    width: 60px;
    height: 60px;
    object-fit: cover;
    border-radius: 50%;
  }
  
  .placeholder-image {
    background-color: #007bff;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    width: 60px;
    height: 60px;
    border-radius: 50%;
  }
  
  .preview-details {
    flex: 1;
  }
  
  .preview-details h3 {
    margin: 0 0 8px 0;
    font-size: 1.2rem;
  }
  
  .verification-status {
    display: inline-block;
    padding: 2px 8px;
    font-size: 0.8rem;
    border-radius: 12px;
    background-color: #f0f0f0;
    color: #666;
  }
  
  .verification-status.verified {
    background-color: #e6f7e6;
    color: #2e7d32;
  }
  
  .expanded-content {
    margin-top: 16px;
    padding-top: 16px;
    border-top: 1px solid #eee;
    animation: expand 0.3s ease-out;
  }
  
  .user-details {
    display: grid;
    gap: 8px;
  }
  
  .user-products {
    margin-top: 16px;
  }
  
  .user-products h4 {
    margin-bottom: 8px;
  }
  
  .user-products ul {
    padding-left: 20px;
  }
  
  .user-actions {
    display: flex;
    gap: 8px;
    margin-top: 16px;
  }
  
  .contact-button, .view-profile-button {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s;
  }
  
  .contact-button {
    background-color: #007bff;
    color: white;
  }
  
  .contact-button:hover {
    background-color: #0069d9;
  }
  
  .view-profile-button {
    background-color: #f8f9fa;
    color: #212529;
    border: 1px solid #ddd;
  }
  
  .view-profile-button:hover {
    background-color: #e9ecef;
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