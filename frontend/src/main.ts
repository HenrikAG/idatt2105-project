import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { pinia } from './components/store/userstore'
import './assets/main.css'

const app = createApp(App)

app.use(pinia)
app.use(router)

app.mount('#app')