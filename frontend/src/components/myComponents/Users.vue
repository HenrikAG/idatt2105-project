<template>
    <div class="users-container">
      <h1>Users</h1>
      <div v-if="loading" class="loading">
        <p>Loading users...</p>
      </div>
      <div v-else-if="error" class="error">
        <p>{{ error }}</p>
      </div>
      <div v-else class="users-list">
        <div v-for="user in users" :key="user.id">
          <User :user="user" />
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import User from '@/components/myComponents/User.vue';
  
  export default {
    name: 'Users',
    components: {
      User
    },
    data() {
      return {
        users: [],
        loading: true,
        error: null
      };
    },
    created() {
      this.fetchUsers();
    },
    methods: {
      async fetchUsers() {
        try {
          const searchQuery = this.$route.query.search?.toLowerCase();
          const response = await axios.get('http://localhost:8080/api/user/all');
          
          if (searchQuery) {
            // Filter users on the client side
            this.users = response.data.filter(user => 
              user.username.toLowerCase().includes(searchQuery)
            );
          } else {
            this.users = response.data;
          }
          
          this.loading = false;
        } catch (error) {
          console.error('Error fetching users:', error);
          this.error = 'Failed to load users. Please try again later.';
          this.loading = false;
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .users-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }
  
  .users-list {
    display: flex;
    flex-direction: column;
    gap: 16px;
  }
  
  .loading, .error {
    text-align: center;
    padding: 40px;
    color: #666;
  }
  
  .error {
    color: #e53935;
  }
  </style>