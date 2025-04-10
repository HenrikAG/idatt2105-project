import { defineStore } from 'pinia';

export const useChatStore = defineStore('chat', {
  state: () => ({
    isOpen: false as boolean,
    activeContact: null as string | null,
    }),
  
  actions: {
    openChat(contactName: string) {
      this.isOpen = true;
      this.activeContact = contactName;
    },
    
    closeChat() {
      this.isOpen = false;
      this.activeContact = null;
    },
  }
});