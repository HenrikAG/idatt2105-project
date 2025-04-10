<template>
    <div class="my-profile-view">
        <Profile />
    </div>
</template>

<script lang="ts">
import Profile from "@/components/vue/Profile.vue";
export default {
    name: "MyProfileView",
    components: {
        Profile,
    },
    data() {
        return {
            // Define any data properties here if needed
        };
    },
};
</script>

<style scoped>
.my-profile-view {
    /* Styles go here */
}
</style>