import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/myViews/HomeView.vue'
import CategoriesView from '@/views/myViews/CategoriesView.vue'
import ProductsView from '@/views/myViews/ProductsView.vue'
import MyUserView from '@/views/myViews/MyuserView.vue'
import SearchView from '@/views/myViews/SearchView.vue'
import SearchUserView from '@/views/myViews/UsersView.vue'
import Users from '@/components/myComponents/Users.vue'
import UsersView from '@/views/myViews/UsersView.vue'
import Login from '@/views/myViews/LoginView.vue'

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
      path : '/my-user',
      name : 'myUser',
      component : MyUserView,
    },
    {
      path : '/search',
      name : 'search',
      component : SearchView,
    },
    {
      path : '/login',
      name : 'login',
      component : Login,
    },
    {
      path : '/search/user/:id',
      name : 'searchUserId',
      component : SearchUserView,
    },
    {
      path : '/search/product/:id',
      name : 'searchProductId',
      component : ProductsView
    },

  ],
})

export default router
