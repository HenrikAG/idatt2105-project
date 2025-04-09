<template>
  <div class="chat-overlay">
    <!-- Chat toggle button -->
    <button class="chat-button" @click="toggleChat" aria-label="Toggle chat">
      <span class="chat-button-text">{{ isOpen ? 'Close' : 'Chat' }}</span>
    </button>
    
    <!-- Chat window (conditionally rendered) -->
    <div v-if="isOpen" class="chat-window">
      <div class="chat-header">
        <h3>{{ currentChat ? getUserName(currentChat.contactName) : 'Contacts' }}</h3>
        <button class="close-button" @click="toggleChat" aria-label="Close chat">×</button>
      </div>
      
      <!-- Contacts list view (shown when no chat is selected) -->
      <div v-if="!currentChat" class="chat-contacts-container">
        <div v-if="isLoading" class="loading-state">
          <div class="loading-spinner"></div>
          <p>Loading your conversations...</p>
        </div>
        
        <div v-else-if="chats.length === 0" class="empty-state">
          <p>No conversations yet</p>
        </div>
        
        <div v-else class="contacts-list">
          <div 
            v-for="chat in chats" 
            :key="chat.contactName"
            class="contact-item"
            @click="selectChat(chat)"
          >
            <div class="contact-avatar">
              {{ getInitials(getUserName(chat.contactName)) }}
            </div>
            <div class="contact-info">
              <div class="contact-name">{{ getUserName(chat.contactName) }}</div>
              <div class="contact-preview">{{ getLastMessagePreview(chat) }}</div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Chat conversation view (shown when a chat is selected) -->
      <div v-else class="chat-content">
        <div class="chat-messages" ref="messagesContainer">
          <div v-if="currentChat.messages.length === 0" class="empty-chat-state">
            <p>No messages yet. Start the conversation!</p>
          </div>
          
          <template v-else>
            <div 
              v-for="(message, index) in currentChat.messages" 
              :key="index"
              :class="['message', message.isSender ? 'user-message' : 'contact-message']"
            >
              <div class="message-content">{{ message.content }}</div>
              <div class="message-time">{{ formatTime(message.timestamp) }}</div>
            </div>
          </template>
        </div>
        
        <div class="chat-input">
          <button 
            class="back-button" 
            @click="goBackToChats" 
            aria-label="Back to contacts"
          >
            ←
          </button>
          <input 
            type="text" 
            v-model="newMessage" 
            @keyup.enter="sendMessage"
            placeholder="Type your message here..."
            :disabled="isSending"
            ref="messageInput"
          />
          <button 
            @click="sendMessage" 
            :disabled="!newMessage.trim() || isSending"
            class="send-button"
            aria-label="Send message"
          >
            {{ isSending ? '...' : 'Send' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch, nextTick } from 'vue';
import axios from 'axios';
import type { Chat } from '@/types/Chat';
import type { Message } from '@/types/Message';
import { useUserStore } from '@/components/store/userstore.ts';
import { useChatStore } from '@/components/store/chatstore.ts';

interface User {
  id: number;
  name: string;
  email?: string;
  image_url?: string | null;
}

// State management
const isOpen = computed(() => chatStore.isOpen);
const storeActiveContact = computed(() => chatStore.activeContact);
const productContext = computed(() => chatStore.productContext);
const currentChat = ref<Chat | null>(null);
const chats = ref<Chat[]>([]);
const users = ref<User[]>([]);
const newMessage = ref('');
const messagesContainer = ref<HTMLElement | null>(null);
const messageInput = ref<HTMLInputElement | null>(null);
const userStore = useUserStore();
const chatStore = useChatStore();
const currentUsername = computed(() => userStore.username);
const isLoading = ref(false);
const isSending = ref(false);
const error = ref<string | null>(null);

// Lifecycle hooks
onMounted(async () => {
  await fetchChats();
});

watch(() => currentChat.value?.messages.length, () => {
  scrollToBottom();
});

watch(() => isOpen.value, (newValue) => {
  if (newValue && currentChat.value) {
    nextTick(() => {
      if (messageInput.value) messageInput.value.focus();
      scrollToBottom();
    });
  }
});

watch(storeActiveContact, (newContact) => {
  if (newContact) {
    // Find or create a chat with this contact
    let foundChat = chats.value.find(chat => chat.contactName === newContact);
    
    if (!foundChat) {
      // Create a new chat
      foundChat = {
        contactName: newContact,
        messages: []
      };
      chats.value.push(foundChat);
    }
    
    // Select this chat
    selectChat(foundChat);
    
    // If there's product context, send an initial message about the product
    if (productContext.value) {
      const initialMessage = `Hi, I'm interested in your "${productContext.value.name}" priced at $${productContext.value.price}. Is it still available?`;
      newMessage.value = initialMessage;
      nextTick(() => {
        sendMessage();
        chatStore.clearProductContext(); // Clear after sending
      });
    }
  }
}, { immediate: true });

// Methods for API interactions
async function fetchChats() {
  isLoading.value = true;
  error.value = null;
  
  try {
    const { data: chatData } = await axios.get(`/api/chats`, {
      params: { username: currentUsername.value }
    });

    chats.value = chatData.map((chat: any) => ({
      contactName: chat.otherUserName,
      messages: chat.messages.map((msg: any) => ({
        isSender: msg.senderId === currentUsername.value,
        content: msg.content,
        timestamp: new Date(msg.timestamp)
      }))
    }));

    const contactNames = chats.value.map(c => c.contactName);
    const { data: userData } = await axios.get(`/api/users`, {
      params: { names: contactNames.join(',') }
    });

    users.value = userData;
  } catch (err) {
    console.error('Failed to fetch chats:', err);
    error.value = 'Failed to load chats';
  } finally {
    isLoading.value = false;
  }
}

async function sendMessage() {
  if (!currentChat.value || !newMessage.value.trim() || isSending.value) return;
  
  const message: Message = {
    isSender: true,
    content: newMessage.value.trim(),
    timestamp: new Date()
  };
  
  currentChat.value.messages.push(message);
  const tempMessage = newMessage.value;
  newMessage.value = '';
  
  scrollToBottom();
  
  isSending.value = true;
  try {
    await axios.post('/api/messages', {
      senderId: currentUsername.value,
      receiverName: currentChat.value.contactName,
      content: tempMessage,
      timestamp: message.timestamp.toISOString()
    });
  } catch (err) {
    console.error('Failed to send message:', err);
  } finally {
    isSending.value = false;
    if (messageInput.value) messageInput.value.focus();
  }
}

// UI Helper functions
function toggleChat() {
  if (isOpen.value) {
    chatStore.closeChat();
  } else {
    chatStore.openChat('');
  }
}

function selectChat(chat: Chat) {
  currentChat.value = chat;
  
  nextTick(() => {
    scrollToBottom();
    if (messageInput.value) messageInput.value.focus();
  });
}

function goBackToChats() {
  currentChat.value = null;
}

function scrollToBottom() {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
    }
  });
}

function formatTime(date: Date | string): string {
  if (typeof date === 'string') {
    date = new Date(date);
  }
  return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

function getUserName(contactName: string): string {
  const user = users.value.find(u => u.name === contactName);
  return user ? user.name : contactName;
}

function getInitials(name: string): string {
  return name
    .split(' ')
    .map(part => part.charAt(0))
    .join('')
    .toUpperCase()
    .substring(0, 2);
}

function getLastMessagePreview(chat: Chat): string {
  if (chat.messages.length === 0) return 'No messages yet';
  
  const lastMessage = chat.messages[chat.messages.length - 1];
  const prefix = lastMessage.isSender ? 'You: ' : '';
  const content = lastMessage.content.length > 20
    ? lastMessage.content.substring(0, 20) + '...'
    : lastMessage.content;
    
  return prefix + content;
}
</script>

<style scoped>
.chat-overlay {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
}

.chat-button {
  background-color: #0084ff;
  color: white;
  border: none;
  border-radius: 30px;
  padding: 12px 24px;
  font-size: 14px;
  cursor: pointer;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  position: relative;
  transition: all 0.2s ease;
}

.chat-button:hover {
  background-color: #0077e6;
}

.chat-window {
  position: absolute;
  bottom: 60px;
  right: 0;
  width: 320px;
  height: 400px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  animation: slideIn 0.2s ease-out;
}

.chat-header {
  padding: 15px;
  background-color: #f5f5f5;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-shrink: 0;
}

.chat-header h3 {
  margin: 0;
  font-size: 16px;
  color: #333;
}

.close-button {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  color: #999;
}

.close-button:hover {
  color: #333;
}

/* Contacts container */
.chat-contacts-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.loading-state, .empty-state {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #666;
}

.loading-spinner {
  width: 30px;
  height: 30px;
  border: 3px solid #f3f3f3;
  border-top: 3px solid #0084ff;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 10px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.contacts-list {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
}

.contact-item {
  display: flex;
  align-items: center;
  padding: 12px 15px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  position: relative;
}

.contact-item:hover {
  background-color: #f8f8f8;
}

.contact-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #0084ff;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: 500;
  margin-right: 12px;
  flex-shrink: 0;
}

.contact-info {
  flex: 1;
  min-width: 0; /* Important for text truncation */
}

.contact-name {
  font-weight: 500;
  color: #333;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.contact-preview {
  font-size: 12px;
  color: #666;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* Chat content */
.chat-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.chat-messages {
  flex: 1;
  padding: 15px;
  overflow-y: auto;
  overflow-x: hidden;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.empty-chat-state {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #888;
  font-style: italic;
  text-align: center;
  padding: 20px;
}

.message {
  max-width: 80%;
  padding: 10px 15px;
  border-radius: 18px;
  position: relative;
  word-wrap: break-word;
  overflow-wrap: break-word;
}

.user-message {
  align-self: flex-end;
  background-color: #0084ff;
  color: white;
  border-bottom-right-radius: 5px;
}

.contact-message {
  align-self: flex-start;
  background-color: #e5e5ea;
  color: #333;
  border-bottom-left-radius: 5px;
}

.message-content {
  font-size: 14px;
  line-height: 1.4;
}

.message-time {
  font-size: 10px;
  opacity: 0.7;
  margin-top: 4px;
  text-align: right;
}

.chat-input {
  padding: 10px;
  background-color: white;
  border-top: 1px solid #e0e0e0;
  display: flex;
  gap: 8px;
  flex-shrink: 0;
}

.back-button {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: #888;
  padding: 0 5px;
  display: flex;
  align-items: center;
}

.back-button:hover {
  color: #333;
}

.chat-input input {
  flex: 1;
  border: 1px solid #e0e0e0;
  border-radius: 20px;
  padding: 8px 15px;
  font-size: 14px;
  min-width: 0; /* Important for flexbox */
}

.chat-input input:focus {
  outline: none;
  border-color: #0084ff;
}

.send-button {
  background-color: #0084ff;
  color: white;
  border: none;
  border-radius: 20px;
  padding: 8px 15px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s;
  white-space: nowrap;
}

.send-button:hover:not(:disabled) {
  background-color: #0077e6;
}

.send-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>