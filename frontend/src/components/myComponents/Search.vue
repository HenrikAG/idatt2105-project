<template>
    <div class="search-component">
        <div class="search-product">
            <select v-model="selectedCategory">
                <option v-for="category in categories" :key="category.id" :value="category.id">
                    {{ category.name }}
                </option>
            </select>
            <input type="text" v-model="searchString" placeholder="Search products..." />
            <button @click="searchCategory">Search</button>
        </div>
        <div class="search-user">
            <input type="text" v-model="searchUserString" placeholder="Search users..." />
            <button @click="searchUser">Search User</button>
        </div>
    </div>
</template>

<script>
import { ref, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

export default {
    setup() {
        const categories = ref([]);
        const selectedCategory = ref(null);
        const searchString = ref('');
        const searchUserString = ref(''); // Moved inside setup
        const router = useRouter();
        const route = useRoute();

        const fetchCategories = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/categories');
                categories.value = response.data;
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        };

        fetchCategories();

        const searchCategory = () => {
            if (selectedCategory.value && searchString.value) {
                router.push(`/categories/${selectedCategory.value}?search=${encodeURIComponent(searchString.value)}`);
            }
        };

        const searchUser = () => {
            if (searchUserString.value) {
                router.push(`/user?search=${encodeURIComponent(searchUserString.value)}`);
            }
        };

        return {
            categories,
            selectedCategory,
            searchString,
            searchUserString, // Added this
            searchCategory, // Changed from search to searchCategory
            searchUser        // Added this
        };
    }
};
</script>

<style scoped>
/* Your styling remains the same */
.search-component {
    margin: 20px;
}
input {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
ul {
    list-style-type: none;
    padding: 0;
}
li {
    padding: 5px 0;
}
p {
    color: #888;
}
</style>