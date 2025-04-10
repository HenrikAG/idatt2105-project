import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import CategoriesView from '@/views/CategoriesView.vue'
import MyProfileView from '@/views/MyProfileView.vue'
import ProductsView from '@/views/ProductsView.vue'
import AdminUserView from '@/views/AdminUserView.vue'
import LoginView from '@/views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },  
    {
      path: '/categories',
      name: 'categories',
      component: CategoriesView,
    },
    {
      path : '/categories/:id',
      name : 'categoriesId',
      component : ProductsView,
    },
    {
      path : '/profile',
      name : 'profile',
      component : MyProfileView,
    },
    {
      path : '/user',
      name : 'user',
      component : AdminUserView,
    },
    {
      path : '/login',
      name : 'login',
      component : LoginView,
    }

  ],
})

export default router
