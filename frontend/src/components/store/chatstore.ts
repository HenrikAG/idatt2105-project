import type { Chat } from '@/types/Chat';
import { defineStore } from 'pinia';

export const useChatStore = defineStore('chat', {
  state: () => ({
    isOpen: false as boolean,
    activeContact: null as string | null,
    currentChat: null as Chat | null,
    }),
  
  actions: {
    openChat(contactName: string, currentChat: Chat | null) {
      this.isOpen = true;
      this.activeContact = contactName;
      this.currentChat =currentChat;
    },
    
    closeChat() {
      this.isOpen = false;
      this.activeContact = null;
    },
  }
});