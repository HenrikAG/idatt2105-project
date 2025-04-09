<template>
    <div class="register">
        <h1>Register</h1>
        
        <!-- Success message -->
        <div v-if="registrationSuccess" class="success-message">
            Registration successful! You can now login.
        </div>
        
        <!-- Error message -->
        <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
        </div>
        
        <form @submit.prevent="register" v-if="!registrationSuccess">
            <div class="form-group">
                <label for="username">Username:</label>
                <input 
                    type="text" 
                    id="username" 
                    v-model="username" 
                    required 
                    :disabled="isSubmitting"
                />
            </div>
            
            <div class="form-group">
                <label for="password">Password:</label>
                <input 
                    type="password" 
                    id="password" 
                    v-model="password" 
                    required 
                    :disabled="isSubmitting"
                />
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Confirm Password:</label>
                <input 
                    type="password" 
                    id="confirmPassword" 
                    v-model="confirmPassword" 
                    required 
                    :disabled="isSubmitting"
                />
                <div v-if="passwordMismatch" class="validation-error">
                    Passwords do not match
                </div>
            </div>
            
            <!-- Category Selection Section -->
            <div class="form-group categories-section">
                <label>Favorite Categories:</label>
                <div v-if="categoriesLoading" class="loading-categories">
                    Loading categories...
                </div>
                <div v-else-if="categoriesError" class="categories-error">
                    {{ categoriesError }}
                </div>
                <div v-else class="categories-checkboxes">
                    <div v-for="category in categories" :key="category.name" class="category-checkbox">
                        <input 
                            type="checkbox"
                            :id="'category-' + category.name"
                            :value="category.name"
                            v-model="selectedCategories"
                            :disabled="isSubmitting"
                        />
                        <label :for="'category-' + category.name">{{ category.name }}</label>
                    </div>
                </div>
            </div>
            
            <button 
                type="submit" 
                :disabled="isSubmitting || passwordMismatch || !formIsValid"
            >
                {{ isSubmitting ? 'Registering...' : 'Register' }}
            </button>
        </form>
    </div>
</template>

<script lang="ts">
import axios from 'axios';

interface RegisterData {
    username: string;
    password: string;
    confirmPassword: string;
    isSubmitting: boolean;
    categories: { name: string }[]; // All available categories
    selectedCategories: string[];  // Selected category names
    categoriesLoading: boolean;
    categoriesError: string;
    registrationSuccess: boolean;
    errorMessage: string;
}

export default {
    name: 'Register',
    data(): RegisterData {
        return {
            username: '',
            password: '',
            confirmPassword: '',
            isSubmitting: false,
            categories: [],
            selectedCategories: [],
            categoriesLoading: true,
            categoriesError: '',
            registrationSuccess: false,
            errorMessage: '',
        };
    },
    computed: {
        passwordMismatch() {
            return this.password && this.confirmPassword 
                && this.password !== this.confirmPassword;
        },
        formIsValid() {
            return this.username && this.password && this.confirmPassword 
                && this.password === this.confirmPassword;
        }
    },
    created() {
        // Fetch categories when component is created
        this.fetchCategories();
    },
    methods: {
        async fetchCategories() {
            this.categoriesLoading = true;
            this.categoriesError = '';
            
            try {
                const response = await axios.get('http://localhost:8080/api/categories');
                this.categories = response.data;
                console.log('Fetched categories:', this.categories);
            } catch (error: any) {
                console.error('Error fetching categories:', error);
                this.categoriesError = 'Failed to fetch categories. Please try again later.';
            } finally {
                this.categoriesLoading = false;
            }
        },
        
        async register() {
            // Reset error message
            this.errorMessage = '';
            
            // Form validation
            if (this.passwordMismatch) {
                this.errorMessage = 'Passwords do not match';
                return;
            }
            
            if (!this.formIsValid) {
                this.errorMessage = 'Please fill in all fields';
                return;
            }
            
            this.isSubmitting = true;
            
            try {
                const response = await axios.post('http://localhost:8080/api/user/register', {
                    username: this.username,
                    password: this.password,
                    favoriteCategories: this.selectedCategories
                });
                
                console.log('Registration successful:', response.data);
                this.registrationSuccess = true;
            } catch (error : any) {
                console.error('Error during registration:', error);
                
                // Handle different error types
                if (error.response) {
                    // The server responded with an error status code
                    const status = error.response.status;
                    
                    if (status === 409) {
                        this.errorMessage = 'Username already exists. Please choose another one.';
                    } else if (status === 400) {
                        this.errorMessage = error.response.data.message || 'Invalid registration data';
                    } else {
                        this.errorMessage = 'Registration failed. Please try again.';
                    }
                } else if (error.request) {
                    // The request was made but no response was received
                    this.errorMessage = 'No response from server. Please check your connection.';
                } else {
                    // Something else caused the error
                    this.errorMessage = 'Registration error. Please try again.';
                }
            } finally {
                this.isSubmitting = false;
            }
        }
    }
};
</script>

<style scoped>
.register {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.register h1 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: normal;
}

.form-group input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button[type="submit"] {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

button[type="submit"]:hover:not(:disabled) {
  background-color: #0056b3;
}

button[type="submit"]:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.error-message {
  background-color: #f2dede;
  color: #a94442;
  padding: 15px;
  margin-bottom: 20px;
  border-radius: 4px;
  border: 1px solid #ebccd1;
}

.success-message {
  background-color: #dff0d8;
  color: #3c763d;
  padding: 15px;
  margin-bottom: 20px;
  border-radius: 4px;
  border: 1px solid #d6e9c6;
}

.validation-error {
  color: #a94442;
  font-size: 12px;
  margin-top: 5px;
}

/* Category styles */
.categories-section {
  margin-top: 20px;
  margin-bottom: 15px;
}

.categories-checkboxes {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 10px;
}

.category-checkbox {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  background-color: #f8f8f8;
  border-radius: 4px;
  border: 1px solid #e0e0e0;
}

.category-checkbox input {
  width: auto;
  margin-right: 8px;
}

.category-checkbox label {
  margin-bottom: 0;
  cursor: pointer;
  font-weight: normal;
}

.loading-categories, .categories-error {
  padding: 10px;
  text-align: center;
  color: #555;
  background-color: #f5f5f5;
  border-radius: 4px;
  margin-top: 10px;
}

.categories-error {
  color: #a94442;
  background-color: #f2dede;
  border: 1px solid #ebccd1;
}
</style>