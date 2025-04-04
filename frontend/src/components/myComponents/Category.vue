<template>
    <div class="category">
        <button class="cat_box" @click="goToCategory('clothing')">👔</button>
        <button class="cat_box" @click="goToCategory('electronics')">💻</button>
        <button class="cat_box" @click="goToCategory('vehicles')">🚗</button>
        <button class="cat_box" @click="goToCategory('home')">🏠</button>
        <button class="cat_box" @click="goToCategory('books')">📖</button>
        <button class="cat_box" @click="goToCategory('art')">🎨</button>
        <button class="cat_box" @click="goToCategory('tools')">🛠️</button>
        <button class="cat_box" @click="goToCategory('travel')">🌍</button>
        <button class="cat_box" @click="goToCategory('games')">🧩</button>
        <button class="cat_box" @click="goToCategory('other')">📦</button>
    </div>
</template>

<script setup>
    import { useRouter } from 'vue-router';

    const router = useRouter();

    function goToCategory(category) {
        router.push(`/category/${category}`);
    }
</script>

<style scoped>
.category {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
    padding: 20px;
}

.cat_box {
    font-size: 3rem;
    padding: 24px;
    background-color: #f5f5f5;
    border: 1px solid #ddd;
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.2s ease;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.cat_box:hover {
    background-color: #e0e0e0;
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0,0,0,0.15);
}

.cat_box:active {
    transform: translateY(1px);
    box-shadow: 0 1px 2px rgba(0,0,0,0.1);
}
</style>