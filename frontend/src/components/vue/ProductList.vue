<template>
    <div class="product-list">
        <div v-if="fetchType">
            <div v-for="(DTOitem, index) in itemList.slice(0, limit)" :key="DTOitem.id">
                <Product :DTOitem="DTOitem" />
            </div>
        </div>
    </div>
</template>

<script lang="ts">

/**
 * Needed input props:
 * - fetchType: string (all, category, user, query)
 * - fetchQuery: string | null (default: null)
 */

import Product from "@/components/vue/Product.vue";
import { type DTOitem } from "@/types/DTOitem";
import { useUserStore } from "@/components/store/userstore";

import axios from "axios";

export default {
    name: "ProductList",
    components: {
        Product,
    },
    props: {
        fetchType: {
            type: String,
            required: true,
            validator: (value: string) => ['all', 'category', 'user'].includes(value)
        },
        fetchQuery: {
            type: String,
            required: false,
            default: null
        },
        fetchQuerySearch: {
            type: String,
            required: false,
            default: null
        },
    },
    data() {
        return {
            itemList: [] as DTOitem[],
            userstore: useUserStore(),
            category: '' as string,
            limit: 100 as number,
        };
    },
    mounted() {
        this.fetchGet().then(items => {
            this.itemList = items;
        });
    },
    methods: {

        // Method to fetch all products
        fetchAll(): Promise<DTOitem[]> {
            return axios.get("http://localhost:8080/api/items", {
                headers: {
                    Authorization: `Bearer ${this.userstore.token}`,
                },
            })
            .then(response => {
                return response.data as DTOitem[];
            })
            .catch(error => {
                console.error("Error fetching products:", error);
                return [];
            });
        },

        // Method to fetch products based on the category
        fetchCategory(): Promise<DTOitem[]> {
            return axios.get("http://localhost:8080/api/items/" + this.fetchQuery, {
                headers: {
                    Authorization: `Bearer ${this.userstore.token}`,
                },
            })
            .then(response => {
                const DTOreturn = response.data as DTOitem[];
                if (this.fetchQuerySearch) {
                    return DTOreturn.filter((item: DTOitem) => item.item_name.toLowerCase().includes(this.fetchQuerySearch.toLowerCase()));
                } else {
                    return DTOreturn;
                }
                
            })
            .catch(error => {
                console.error("Error fetching products:", error);
                return [];
            });
        },

        // Method to fetch products based on the username
        fetchUser(): Promise<DTOitem[]> {
            return axios.get("http://localhost:8080/api/user/" + this.fetchQuery + "/listed-items", {
                headers: {
                    Authorization: `Bearer ${this.userstore.token}`,
                },
            })
            .then(response => {
                return response.data as DTOitem[];
            })
            .catch(error => {
                console.error("Error fetching products:", error);
                return [];
            });
        },


        // Fetch products based on the fetchType and fetchQuery props
        fetchGet() {
            switch (this.fetchType) {
                case "all":
                    return this.fetchAll();
                case "category":
                    return this.fetchCategory();
                case "user":
                    return this.fetchUser();
                default:
                    console.error("Invalid fetchType:", this.fetchType);
                    return Promise.resolve([]);
            }
        },
    },
};
</script>

<style scoped>
</style>