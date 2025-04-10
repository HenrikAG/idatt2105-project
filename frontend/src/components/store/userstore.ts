import { defineStore } from 'pinia';
import { jwtDecode } from 'jwt-decode';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import type { DTOloginToken } from '@/types/DTOloginToken';

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
        saveUser(tokendata : DTOloginToken) {
            this.token = tokendata.token;
            const decodedToken: any = jwtDecode(tokendata.token);
            this.username = decodedToken.sub;
            this.role = decodedToken.role;
        },
        clearUser() {
            this.username = '';
            this.token = '';
            this.role = '';
        }
    }
});

export { pinia };


