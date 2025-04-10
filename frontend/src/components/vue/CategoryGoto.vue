<template>
    <div class="category-goto">
        <button class="category-name" @click="executePressed">{{ DTOcategory.name }}</button>
    </div>
</template>

<script lang="ts">
import { type DTOcategory } from "@/types/DTOcategory";
import { type PropType } from "vue";
import { useUserStore } from "@/components/store/userstore";
import axios from "axios";

export default {
    name: "CategoryGoto",
    props: {
        DTOcategory: {
            type: Object as PropType<DTOcategory>,
            required: true,
        },
    },
    data() {
        return {
            userstore: useUserStore(),
            category: {} as DTOcategory,
        };
    },
    methods: {
        executePressed() : void {
            this.$router.push(`/categories/${this.DTOcategory.name}`);
        },
    },
};
</script>

<style scoped>
/* Add your styles here */
</style>