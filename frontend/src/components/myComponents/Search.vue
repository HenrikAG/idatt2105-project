<template>
    <div class="search-component">
        <div class="search-product">
            <h3 class="search-label">Search Products</h3>
            <input type="text" v-model="searchString" placeholder="Search products..." />
            <label for="category">Select Category: </label>
            <select v-model="selectedCategory" id="category">
                <option value="" disabled selected>-- Select a category --</option>
                <!-- Change :value to use category.name instead of category.id -->
                <option v-for="category in categories" :key="category.id" :value="category.name">
                    {{ category.name }}
                </option>
            </select>
            <div></div>
            <button @click="searchCategory">Search</button>
        </div>
        <div class="search-user">
            <h3 class="search-label">Search User</h3>
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
        const selectedCategory = ref('');
        const searchString = ref('');
        const searchUserString = ref('');
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
            console.log("Selected category:", selectedCategory.value);
            console.log("Search string:", searchString.value);
            
            if (!selectedCategory.value) {
                alert("Please select a category");
                return;
            }
            
            if (!searchString.value) {
                alert("Please enter a search term");
                return;
            }
            
            router.push(`/categories/${selectedCategory.value}?search=${encodeURIComponent(searchString.value)}`);
        };


        const searchUser = () => {
            if (searchUserString.value) {
                router.push(`/user?search=${encodeURIComponent(searchUserString.value)}`);
            } else {
                alert("Please enter a search term for the user");
            }
        };

        return {
            categories,
            selectedCategory,
            searchString,
            searchUserString,
            searchCategory,
            searchUser
        };
    }
};
</script>

<style scoped>
/* Your styling remains the same */
.search-component {
    margin: 20px;
    display: flex;
    flex-direction: column;
    gap: 40px;
    max-width: 400px;
}
.search-product, .search-user {
    border: 1px solid #ccc;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}
label {
    margin-bottom: 5px;
    font-weight: lighter;
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