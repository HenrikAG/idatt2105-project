<template>
    <div class="categories-select">
        <div class="categories-list">
            <div v-for="category in DTOcategory" :key="category.id">
                <CategoryGoto :DTOcategory="category" />
                <button v-if="userstore.role === 'ADMIN'" class="delete-category" @click="deleteCategory(category.name)">Delete</button>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { useUserStore } from "@/components/store/userstore";
import CategoryGoto from "@/components/vue/CategoryGoto.vue";
import axios from "axios";
import { type DTOcategory } from "@/types/DTOcategory";

export default {
    name: "CategoriesSelect",
    data() {
        return {
            userstore: useUserStore(),
            DTOcategory: [] as DTOcategory[],
        };
    },
    components: {
        CategoryGoto,
    },
    mounted() {
        this.fetchAll().then(categories => {
            this.DTOcategory = categories;
        });
    },
    methods: {
        fetchAll() : Promise<DTOcategory[]> {    
            return axios.get("http://localhost:8080/api/categories", {
                headers: {
                    Authorization: `Bearer ${this.userstore.token}`,
                },
            })
            .then(response => {
                this.DTOcategory = response.data as DTOcategory[];
                return this.DTOcategory;
            })
            .catch(error => {
                console.error("Error fetching categories:", error);
                return [] as DTOcategory[];
            });
        },
        deleteCategory(categoryname: string) {
            if (confirm("Are you sure you want to delete this category?")) {
                axios.delete(`http://localhost:8080/api/categories/${categoryname}`, {
                    headers: {
                        Authorization: `Bearer ${this.userstore.token}`,
                    },
                })
                .then(() => {
                    alert("Category deleted successfully.");
                })
                .catch(error => {
                    console.error("Error deleting category:", error);
                    alert("Failed to delete category.");
                });
            }
        },
    },
};
</script>

<style scoped>
/* Add your styles here */
</style>