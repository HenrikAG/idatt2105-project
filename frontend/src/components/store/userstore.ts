import { defineStore } from 'pinia';
import { jwtDecode } from 'jwt-decode';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

export const useUserStore = defineStore('token', {
    state: () => ({
        username: '',
        token: '',
        role: '',
    }),

    persist: {
        storage: localStorage,
    },

    actions: {
        saveUser(userData: any) {
            this.token = userData.token;
            const decodedToken: any = jwtDecode(userData.token);
            this.username = decodedToken.sub;
            this.role = decodedToken.role;

            console.log('Decoded token:', decodedToken);
            console.log('User ID:', this.username);
            console.log('User role:', this.role);
        },
        clearUser() {
            this.username = '';
            this.token = '';
            this.role = '';
        }
    }
});

export { pinia };


