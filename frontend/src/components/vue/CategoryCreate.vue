<template>
    <div class="category-create">
        <h1>Create a New Category</h1>
        <form @submit.prevent="createCategory">
            <div>
                <label for="name">Category Name:</label>
                <input type="text" id="name" v-model="DTOcategoryRegister.name" required />
            </div>
            <button type="submit">Create</button>
        </form>
    </div>
</template>

<script lang="ts">
import axios from "axios";
import { useUserStore } from "@/components/store/userstore";
import { type DTOcategoryRegister } from "@/types/DTOcategoryRegister";

export default {
    name: "CategoryCreate",
    data() {
        return {
            userStore: useUserStore(),
            DTOcategoryRegister: { name: '' } as DTOcategoryRegister,
        };
    },
    methods: {
        async createCategory() {
            try {
                // Send category creation request to the server
                const response = await axios.post("http://localhost:8080/api/categories", this.DTOcategoryRegister, {
                    headers: {
                        Authorization: `Bearer ${this.userStore.token}`,
                    },
                });
                console.log("Category created successfully:", response.data);
                alert("Category created successfully!");
                this.DTOcategoryRegister = { name: ''} as DTOcategoryRegister; // Reset the form
            } catch (error) {
                console.error("Error creating category:", error);
                alert("Failed to create category. Please try again.");
            }
        },
    },
};
</script>

<style scoped>
</style>