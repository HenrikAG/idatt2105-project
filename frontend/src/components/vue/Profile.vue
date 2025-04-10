<template>
    <div class="profile">
        <h1>User Profile</h1>
        <p>Username: {{ userstore.username }}</p>
        <div class="profile-actions">
            <h3>Actions</h3>
            <button @click="executeLogout">Logout</button>
            <button @click="executeDeleteAccount">Delete Account</button>
            <h3>Listed Items</h3>
            <ProductList fetchType="user" :fetchQuery="userstore.username" />
            <h3>Add New Item</h3>
            <form @submit.prevent="executeAddItem">
                <div>
                    <label for="item_name">Item Name:</label>
                    <input type="text" id="item_name" v-model="newItem.item_name" required />

                    <label for="price">Price:</label>
                    <input type="number" id="price" v-model="newItem.price" required />

                    <label for="description">Description:</label>
                    <textarea id="description" v-model="newItem.description" required></textarea>

                    <label for="category">Category:</label>
                    <select id="category" v-model="newItem.category" required>
                        <option v-for="category in categories" :key="category" :value="category">
                            {{ category }}
                        </option>
                    </select>

                    <label for="image_url">Image URL:</label>
                    <input type="url" id="image_url" v-model="newItem.image_url" required />
                </div>
                <button type="submit">Add Item</button>
            </form>

        </div>
    </div>
</template>

<script lang="ts">
import { useUserStore } from "@/components/store/userstore";
import ProductList from "@/components/vue/ProductList.vue";
import { type DTOitemRegister } from "@/types/DTOitemRegister";
import { type DTOcategory } from "@/types/DTOcategory";
import axios from "axios";

export default {
    name: "Profile",
    data() {
        return {
            userstore: useUserStore(),
            listedItems: [],
            newItem: {} as DTOitemRegister,
            categories: [] as string[],
        };
    },
    components: {
        ProductList,
    },
    mounted() {
        // Fetch categories from the server
        axios.get("http://localhost:8080/api/categories")
        .then(response => {
            this.categories = response.data.map((category: DTOcategory) => category.name);
        })
        .catch(error => {
            console.error("Error fetching categories:", error);
        });
    },
    methods: {
        executeLogout() {
            // Logic to handle logout
            this.userstore.clearUser();
            this.$router.push("/login"); // Redirect to login page after logout
        },
        executeDeleteAccount() {
            // Logic to handle account deletion
            if (confirm("Are you sure you want to delete your account? This action cannot be undone.")) {
                axios.delete(`http://localhost:8080/api/user/${this.userstore.username}`, {
                    headers: {
                        Authorization: `Bearer ${this.userstore.token}`,
                    },
                })
                .then(() => {
                    alert("Account deleted successfully.");
                    this.userstore.clearUser(); // Clear user data from store
                    this.executeLogout(); // Logout after account deletion
                })
                .catch(error => {
                    console.error("Error deleting account:", error);
                    alert("Failed to delete account.");
                });
            }
        },
        executeAddItem() {
            // Logic to handle adding a new item
            this.newItem.seller_name = this.userstore.username; // Set the seller name to the logged-in user
            axios.post("http://localhost:8080/api/items/post", 
                this.newItem, 
                {
                    headers: {
                        Authorization: `Bearer ${this.userstore.token}`,
                    },
                }
            )
            .then(response => {
                alert("Item added successfully.");
                this.newItem = {} as DTOitemRegister; // Reset the form
            })
            .catch(error => {
                console.error("Error adding item:", error);
                alert("Failed to add item.");
            });
        }
    },
};
</script>

<style scoped>
/* Add component-specific styles here */
</style>