import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/myViews/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/category',
      name: 'category',
      component: () => import('../views/myViews/CategoryView.vue'),
    },
    {
      path : '/category/:id',
      name : 'categoryId',
    },
    {
      path : '/user/:id',
      name : 'userId',
    },
    {
      path : '/search',
      name : 'search',
    },
    {
      path : '/search/user/:id',
      name : 'searchUserId',
    },
    {
      path : '/search/product/:id',
      name : 'searchProductId',
    },

  ],
})

export default router
