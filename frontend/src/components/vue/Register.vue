<template>
    <div class="register">
        <form @submit.prevent="executeRegister">
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username-register" v-model="DTOregisteruser.username" required />
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password-register" v-model="DTOregisteruser.password" required />
            </div>
            <div>
                <label for="passwordRepeat">Repeat Password:</label>
                <input type="password" id="passwordRepeat" v-model="DTOregisteruser.passwordRepeat" required />
            </div>
            <div v-for="(category, index) in SelectedCategory" :key="index">
                <label>
                    <input type="checkbox" v-model="category.selected" @change="calculateSelectedCategories" />
                    {{ category.DTOcategory.name }}
                </label>
            </div>
            <button type="submit">Register</button>
        </form>
    </div>
</template>

<script lang="ts">
import { type DTOregisteruser } from '@/types/DTOregisteruser';
import { useUserStore } from "@/components/store/userstore";
import { type DTOcategory } from "@/types/DTOcategory";
import router from '@/router';
import axios from 'axios';

export interface SelectedCategory {
    DTOcategory: DTOcategory;
    selected: boolean;
}

export default {
    name: "Register",
    methods: {
        executeRegister() {

            // check if atleast one category is selected
            if (this.SelectedCategory.filter(category => category.selected).length === 0) {
                alert("Please select at least one category.");
                return;
            }

            return axios.post('http://localhost:8080/api/user/register', {
                username: this.DTOregisteruser.username,
                password: this.DTOregisteruser.password,
                favoriteCategories: this.DTOregisteruser.favoriteCategories,
            })
            .then(response => {
                console.log('User registered successfully:', response.data);
                alert('Registration successful! You can now log in.');
                this.DTOregisteruser = {} as DTOregisteruser; // Reset the form
            })
            .catch(error => {
                console.error('Error registering user:', error);
                alert('Registration failed. Please try again.');
            });
        },
        fetchAllCategories() : Promise<SelectedCategory[]> {    
            return axios.get("http://localhost:8080/api/categories", {
                headers: {
                    Authorization: `Bearer ${this.userstore.token}`,
                },
            })
            .then(response => {
                this.SelectedCategory = response.data.map((category: DTOcategory) => ({
                    DTOcategory: category,
                    selected: false
                }));
                return this.SelectedCategory;
            })
            .catch(error => {
                console.error("Error fetching categories:", error);
                return [] as SelectedCategory[];
            });
        },
        calculateSelectedCategories() {
            this.DTOregisteruser.favoriteCategories = this.SelectedCategory
                .filter(category => category.selected)
                .map(category => category.DTOcategory.name);
        },
    },
    data() {
        return {
            DTOregisteruser: {} as DTOregisteruser,
            SelectedCategory: [] as SelectedCategory[],
            userstore: useUserStore(),
        };
    },
    mounted() {
        this.fetchAllCategories().then((categories: SelectedCategory[]) => {
            this.SelectedCategory = categories;
        });
    },
};
</script>

<style scoped>
/* Add your styles here */
</style>