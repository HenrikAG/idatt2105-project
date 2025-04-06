import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/myViews/HomeView.vue'
import CategoryView from '@/views/myViews/CategoryView.vue'
import ProductsView from '@/views/myViews/ProductsView.vue'

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
      component: CategoryView,
    },
    {
      path : '/category/:id',
      name : 'categoryId',
      component : ProductsView,
    },
    {
      path : '/my-user',
      name : 'myUser',
      redirect: '/',
    },
    {
      path : '/user/:id',
      name : 'userId',
      redirect: '/',
    },
    {
      path : '/search',
      name : 'search',
      redirect: '/',
    },
    {
      path : '/search/user/:id',
      name : 'searchUserId',
      redirect: '/',
    },
    {
      path : '/search/product/:id',
      name : 'searchProductId',
      redirect: '/',
    },

  ],
})

export default router
