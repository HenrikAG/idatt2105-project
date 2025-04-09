import { defineStore } from 'pinia';

export const useChatStore = defineStore('chat', {
  state: () => ({
    isOpen: false,
    activeContact: null as string | null,
    productContext: null as {
      id: number;
      name: string;
      price: number;
    } | null,
    forceSendMessage: false
    }),
  
  actions: {
    openChat(contactName: string) {
      this.isOpen = true;
      this.activeContact = contactName;
      this.productContext = null;
      this.forceSendMessage = false;
    },

    openChatWithProduct(contactName: string, productInfo: { id: number; name: string; price: number }) {
      this.isOpen = true;
      this.activeContact = contactName;
      this.productContext = productInfo;
      this.forceSendMessage = true; // Set flag to force sending a message
    },
    
    closeChat() {
      this.isOpen = false;
    },
    
    clearProductContext() {
      this.productContext = null;
      this.forceSendMessage = false;
    }
  }
});