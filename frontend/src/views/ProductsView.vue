<template>
    <div class="products-view">
        <h1>Products View</h1>
        <div>
            <input 
                type="text" 
                v-model="searchQuery" 
                placeholder="Enter search query" 
            />
            <button @click="updateQuery">Search</button>
        </div>
        <ProductList 
            ref="productListRef"
            :fetchType="category"
            :fetchQuery="getCategoryName()"
            :fetchQuerySearch="getSearchQuery()" />
    </div>
</template>

<script lang="ts">
import { useUserStore } from "@/components/store/userstore";
import ProductList from "@/components/vue/ProductList.vue";

export default {
    name: "ProductsView",
    data() {
        return {
            searchQuery: this.$route.params.query || null,
            userstore: useUserStore(),
            ProductList: ProductList,
            category: "category" as string,
            currentCategory: '' as string,
        };
    },
    created() {
        this.currentCategory = this.getCategoryName();
    },
    components: {
        ProductList,
    },  
    methods: {
        getSearchQuery() : string | null {
            const search = this.$route.query.search;
            return Array.isArray(search) ? search.join(',') : search || null;
        },
        getCategoryName() : string {
            const pathParts = this.$route.path.split('/');
            return pathParts[pathParts.length - 1];
        },
        updateQuery() : void {
        if (this.searchQuery) {
            // Use the ref to access the component instance
            const productList = this.$refs.productListRef as any;
            productList.searchQuery = this.searchQuery;
            if (productList) {
                productList.fetchGet()
                alert(`Searching for: ${productList.searchQuery}`);
            }
        }
    },
    }
};
</script>

<style scoped>
</style>