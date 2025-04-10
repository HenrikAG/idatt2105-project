<template>
    <div class="categories-view">
        <h1>Categories</h1>
        <CategoriesSelect />
        <CategoryCreate v-if="userStore.role === 'ADMIN'"/>
    </div>
</template>

<script lang="ts">
import CategoriesSelect from "@/components/vue/CategoriesSelect.vue";
import CategoryCreate from "@/components/vue/CategoryCreate.vue";
import { useUserStore } from "@/components/store/userstore";

export default {
    name: "CategoriesView",
    components: {
        CategoriesSelect,
        CategoryCreate,
    },
    data() {
        return {
            userStore: useUserStore(),
        };
    },
};
</script>

<style scoped>
</style>