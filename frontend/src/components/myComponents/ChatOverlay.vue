<template>
  <div class="chat-overlay">
    <!-- Chat toggle button - always visible -->
    <button class="chat-button" @click="toggleChat" aria-label="Toggle chat">
      <span class="chat-button-text">{{ isOpen ? 'Close' : 'Chat' }}</span>
      <span v-if="unreadCount > 0" class="notification-badge" aria-label="Unread messages">
        {{ unreadCount }}
      </span>
    </button>
    
    <!-- Chat window -->
    <div v-if="isOpen" class="chat-window">
      <div class="chat-header">
        <h3>{{ currentContact ? getUserName(currentContact.contactId) : 'Contacts' }}</h3>
        <button class="close-button" @click="toggleChat" aria-label="Close chat">×</button>
      </div>
      
      <!-- Contacts list view -->
      <div v-if="!currentContact" class="chat-contacts-container">
        <div v-if="isLoading" class="loading-state">
          <div class="loading-spinner"></div>
          <p>Loading your conversations...</p>
        </div>
        
        <div v-else-if="contacts.length === 0" class="empty-state">
          <p>No conversations yet</p>
        </div>
        
        <div v-else class="contacts-list">
          <div 
            v-for="contact in contacts" 
            :key="contact.contactId"
            class="contact-item"
            @click="selectContact(contact)"
          >
            <div class="contact-avatar">
              {{ getInitials(getUserName(contact.contactId)) }}
            </div>
            <div class="contact-info">
              <div class="contact-name">{{ getUserName(contact.contactId) }}</div>
              <div class="contact-preview">{{ getLastMessagePreview(contact) }}</div>
            </div>
            <div 
              v-if="getUnreadCount(contact)" 
              class="unread-badge"
              aria-label="Unread message count"
            >
              {{ getUnreadCount(contact) }}
            </div>
          </div>
        </div>
      </div>
      
      <!-- Chat conversation view -->
      <div v-else class="chat-content">
        <div class="chat-messages" ref="messagesContainer">
          <div v-if="currentContact.messages.length === 0" class="empty-chat-state">
            <p>No messages yet. Start the conversation!</p>
          </div>
          
          <template v-else>
            <div 
              v-for="(message, index) in currentContact.messages" 
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
            @click="goBackToContacts" 
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

// Using the provided interfaces
interface Message {
  isSender: boolean;
  content: string;
  timestamp: Date;
  read?: boolean; // Optional property to track read status
}

interface Contact {
  contactId: number;
  messages: Message[];
}

interface User {
  id: number;
  name: string;
  email?: string;
  image_url?: string | null;
}

// State management
const isOpen = ref(false);
const currentContact = ref<Contact | null>(null);
const contacts = ref<Contact[]>([]);
const users = ref<User[]>([]);
const newMessage = ref('');
const messagesContainer = ref<HTMLElement | null>(null);
const messageInput = ref<HTMLInputElement | null>(null);
const currentUserId = ref(1); // Would come from auth system in real app
const isLoading = ref(false);
const isSending = ref(false);
const error = ref<string | null>(null);
const unreadCounts = ref<Record<number, number>>({});

// Computed values
const unreadCount = computed(() => 
  Object.values(unreadCounts.value).reduce((total, count) => total + count, 0)
);

// Lifecycle hooks
onMounted(async () => {
  await fetchContacts();
});

// Watch for changes to trigger UI updates
watch(() => currentContact.value?.messages.length, () => {
  scrollToBottom();
});

watch(() => isOpen.value, (newValue) => {
  if (newValue && currentContact.value) {
    nextTick(() => {
      if (messageInput.value) messageInput.value.focus();
      scrollToBottom();
    });
  }
});

// Methods for API interactions
async function fetchContacts() {
  isLoading.value = true;
  error.value = null;
  
  try {
    // In a real app, you'd fetch from your API
    const { data: chatData } = await axios.get(`/api/chats`, {
      params: { userId: currentUserId.value }
    });
    
    // Mock data for development
    if (!chatData || !chatData.length) {
      // Fallback to mock data
      contacts.value = mockContacts();
      users.value = mockUsers();
    } else {
      // Transform API response to match our interfaces
      contacts.value = chatData.map((chat: any) => ({
        contactId: chat.otherUserId,
        messages: chat.messages.map((msg: any) => ({
          isSender: msg.senderId === currentUserId.value,
          content: msg.content,
          timestamp: new Date(msg.timestamp),
          read: msg.read || false
        }))
      }));
      
      // Fetch user data for contacts
      const contactIds = contacts.value.map(c => c.contactId);
      const { data: userData } = await axios.get(`/api/users`, {
        params: { ids: contactIds.join(',') }
      });
      
      users.value = userData || mockUsers();
    }
    
    // Calculate unread counts
    calculateUnreadCounts();
  } catch (err) {
    console.error('Failed to fetch contacts:', err);
    error.value = 'Failed to load contacts';
    
    // Fallback to mock data in development
    contacts.value = mockContacts();
    users.value = mockUsers();
    calculateUnreadCounts();
  } finally {
    isLoading.value = false;
  }
}

async function sendMessage() {
  if (!currentContact.value || !newMessage.value.trim() || isSending.value) return;
  
  const message: Message = {
    isSender: true,
    content: newMessage.value.trim(),
    timestamp: new Date(),
    read: true
  };
  
  // Optimistic UI update
  currentContact.value.messages.push(message);
  const tempMessage = newMessage.value;
  newMessage.value = '';
  
  scrollToBottom();
  
  // Send to API
  isSending.value = true;
  try {
    await axios.post('/api/messages', {
      senderId: currentUserId.value,
      receiverId: currentContact.value.contactId,
      content: tempMessage,
      timestamp: message.timestamp.toISOString()
    });
  } catch (err) {
    console.error('Failed to send message:', err);
    // Could show an error state on the message or retry option
  } finally {
    isSending.value = false;
    if (messageInput.value) messageInput.value.focus();
  }
}

// UI Helper functions
function toggleChat() {
  isOpen.value = !isOpen.value;
}

function selectContact(contact: Contact) {
  currentContact.value = contact;
  
  // Mark messages as read
  if (contact.messages.length > 0) {
    contact.messages.forEach(msg => {
      if (!msg.isSender) msg.read = true;
    });
    
    // Update unread counts
    calculateUnreadCounts();
  }
  
  nextTick(() => {
    scrollToBottom();
    if (messageInput.value) messageInput.value.focus();
  });
}

function goBackToContacts() {
  currentContact.value = null;
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

function getUserName(userId: number): string {
  const user = users.value.find(u => u.id === userId);
  return user ? user.name : `User ${userId}`;
}

function getInitials(name: string): string {
  return name
    .split(' ')
    .map(part => part.charAt(0))
    .join('')
    .toUpperCase()
    .substring(0, 2);
}

function getLastMessagePreview(contact: Contact): string {
  if (contact.messages.length === 0) return 'No messages yet';
  
  const lastMessage = contact.messages[contact.messages.length - 1];
  const prefix = lastMessage.isSender ? 'You: ' : '';
  const content = lastMessage.content.length > 20
    ? lastMessage.content.substring(0, 20) + '...'
    : lastMessage.content;
    
  return prefix + content;
}

function getUnreadCount(contact: Contact): number {
  return unreadCounts.value[contact.contactId] || 0;
}

function calculateUnreadCounts() {
  const counts: Record<number, number> = {};
  
  contacts.value.forEach(contact => {
    const unreadMessages = contact.messages.filter(msg => !msg.isSender && !msg.read);
    if (unreadMessages.length > 0) {
      counts[contact.contactId] = unreadMessages.length;
    }
  });
  
  unreadCounts.value = counts;
}

// Mock data for development
function mockContacts(): Contact[] {
  return [
    {
      contactId: 2,
      messages: [
        { isSender: false, content: 'Hi, is this item still available?', timestamp: new Date(Date.now() - 3600000) },
        { isSender: true, content: 'Yes, it is!', timestamp: new Date(Date.now() - 3000000) },
        { isSender: false, content: 'Great! I\'d like to buy it.', timestamp: new Date(Date.now() - 2400000), read: false },
      ]
    },
    {
      contactId: 3,
      messages: [
        { isSender: false, content: 'Hello, do you ship internationally?', timestamp: new Date(Date.now() - 86400000) },
        { isSender: true, content: 'Yes, but there\'s an additional fee.', timestamp: new Date(Date.now() - 82800000) },
      ]
    },
    {
      contactId: 4,
      messages: []
    }
  ];
}

function mockUsers(): User[] {
  return [
    { id: 2, name: 'John Doe', email: 'john@example.com' },
    { id: 3, name: 'Jane Smith', email: 'jane@example.com' },
    { id: 4, name: 'New Contact', email: 'new@example.com' }
  ];
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

.notification-badge {
  position: absolute;
  top: -8px;
  right: -8px;
  background-color: #ff4d4d;
  color: white;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  font-size: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
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

.unread-badge {
  background-color: #ff4d4d;
  color: white;
  border-radius: 50%;
  min-width: 18px;
  height: 18px;
  font-size: 11px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 5px;
  margin-left: 8px;
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