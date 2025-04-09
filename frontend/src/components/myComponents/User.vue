<template>
  <div class="user-card" :class="{ expanded: isExpanded }" @click="toggleExpand">
    <div class="user-preview">
      <div class="preview-details">
        <h3>{{ user.username }}</h3>
      </div>
    </div>
    
    <!-- Expanded content section -->
    <div v-if="isExpanded" class="expanded-content">
      <div class="user-details">
        <p v-if="user.email" class="email">Email: {{ user.email }}</p>
        
        <div v-if="user.products && user.products.length > 0" class="user-products">
          <h4>Products Listed:</h4>
          <ul>
            <li v-for="product in user.products" :key="product.id">
              {{ product.item_name }} - ${{ product.price }}
            </li>
          </ul>
        </div>
        
        <div class="user-actions">
          <button v-if="userStore.role === 'ADMIN'" 
                  class="delete-button" 
                  @click.stop="deleteUser">Delete User</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import type { User } from '@/types/User'
import { useUserStore } from '@/components/store/userstore'

// Define props that accept a User object
const props = defineProps<{
  user: User
}>()

const router = useRouter()
const userStore = useUserStore()
const isExpanded = ref(false)

// Toggle the expanded state
const toggleExpand = () => {
  isExpanded.value = !isExpanded.value
}

// Delete user (admin only)
const deleteUser = async () => {
  if (!confirm(`Are you sure you want to delete user ${props.user.username}?`)) {
    return
  }
  
  try {
    await axios.delete(`http://localhost:8080/api/user/${props.user.username}`, {
      headers: {
        'Authorization': `Bearer ${userStore.token}`
      }
    })
    
    alert(`User ${props.user.username} deleted successfully`)
    // Emit event to parent component to refresh user list
    // You'll need to implement this in the parent component (Users.vue)
    const event = new CustomEvent('user-deleted')
    window.dispatchEvent(event)
  } catch (error) {
    console.error('Error deleting user:', error)
    alert('Failed to delete user. Please try again.')
  }
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
  font-size: 0.8rem;
  overflow: hidden;
  padding: 5px;
  text-align: center;
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
}

.user-actions {
  display: flex;
  gap: 8px;
  margin-top: 16px;
}

.view-profile-button, .delete-button {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.view-profile-button {
  background-color: #f8f9fa;
  color: #212529;
  border: 1px solid #ddd;
}

.delete-button {
  background-color: #dc3545;
  color: white;
}

.delete-button:hover {
  background-color: #c82333;
}
</style>