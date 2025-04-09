import { defineStore } from 'pinia';

export const useChatStore = defineStore('chat', {
  state: () => ({
    isOpen: false,
    activeContact: null as string | null,
    productContext: null as {
      id: number;
      name: string;
      price: number;
    } | null
  }),
  
  actions: {
    openChat(contactName: string, productInfo?: { id: number; name: string; price: number }) {
      this.isOpen = true;
      this.activeContact = contactName;
      this.productContext = productInfo || null;
    },
    
    closeChat() {
      this.isOpen = false;
    },
    
    clearProductContext() {
      this.productContext = null;
    }
  }
});