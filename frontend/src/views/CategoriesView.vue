<template>
    <div class="categories-view">
        <CategoriesSelect />
    </div>
</template>

<script lang="ts">
import CategoriesSelect from "@/components/vue/CategoriesSelect.vue";

export default {
    name: "CategoriesView",
    components: {
        CategoriesSelect,
    },
};
</script>

<style scoped>
.categories-view {
    padding: 20px;
}
</style>