<template>
    <div class="product">
        <div>
            <button @click="toggleExpand">{{ isExpanded ? 'Collapse' : 'Expand' }}</button>
            <img v-if="DTOitem.image_url" :src="DTOitem.image_url"/>
            <h2>{{ DTOitem.item_name }}</h2>
            <div v-if="isExpanded">
                <p>{{ DTOitem.description }}</p>
                <p>Price: ${{ DTOitem.price }}</p>
                <p>Seller: {{ DTOitem.seller_name }}</p>
                <p>Category: {{ DTOitem.category }}</p>
                <button v-if="showDelete()" @click="deleteProduct">Delete</button>
                <button v-if="showContact()" @click="contactSeller">Contact Seller</button>
                <button v-if="showLogin()" @click="gotoLogin">Login to Contact</button>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import { type DTOitem } from "@/types/DTOitem";
import { type PropType } from "vue";
import { useUserStore } from "@/components/store/userstore";
import axios from "axios";

export default {
    name: "Product",
    props: {
        DTOitem: {
            type: Object as PropType<DTOitem>,
            required: true,
        },
    },
    data() {
        return {
            userstore: useUserStore(),
            isExpanded: false,
        };
    },
    methods: {
        contactSeller() {
            // Logic to contact the seller
            alert(`Contacting seller: ${this.DTOitem.seller_name}`);
        },
        gotoLogin() {
            // Logic to redirect to login page
            this.$router.push('/login');
        },
        deleteProduct() {
            // Logic to delete the product
            alert(`Deleting product: ${this.DTOitem.item_id}`);
            axios.delete(`http://localhost:8080/api/items/item/${this.DTOitem.item_id}`, {
                headers: {
                    Authorization: `Bearer ${this.userstore.token}`,
                },
            })
            .then(() => {
                alert("Product deleted successfully.");
            })
            .catch(error => {
                console.error("Error deleting product:", error);
                alert("Failed to delete product.");
            });
        },
        isLoggedIn() {
            return !!this.userstore.username;
        },
        isAdmin() {
            return this.userstore.username && this.userstore.role === 'ADMIN';
        },
        isSeller() {
            return this.userstore.username && this.userstore.username === this.DTOitem.seller_name;
        },
        showDelete() {
            return this.isAdmin() || this.isSeller();
        },
        showLogin() {
            return !this.isLoggedIn();
        },
        showContact() {
            return this.isLoggedIn() && !this.isSeller();
        },
        toggleExpand() {
            this.isExpanded = !this.isExpanded;
        },
    },
};
</script>

<style scoped>
.product {
    /* Add styles here */
}
</style>