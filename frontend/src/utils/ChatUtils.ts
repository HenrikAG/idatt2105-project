import axios from 'axios';
import type { Chat } from "@/types/Chat";
import { useUserStore } from '@/components/store/userstore.ts';
import type { ChatReuest } from '@/types/ChatRequest';

export async function fetchMessages(chat: Chat) {
    console.log("Fetch messages.")
    const userStore = useUserStore();

    try {
      const response = await axios.get(
        `http://localhost:8080/api/messages/chat/${chat.id}`,
        {
          headers: { 'Authorization': `Bearer ${userStore.token}` }
        }
      );
      
      let messages = response.data;
  
      //connectWebSocket(chat.id, handleIncommingMessage, userStore.token);
  
      return messages;
    } catch (err) {
      console.error("Failed do load messages");
    }
}

export async function fetchChats(username: string) {
    console.log(("Fetching chats"))
    
    const userStore = useUserStore();
    let chats = [];
    
    try {
      const response = await axios.get(
        `http://localhost:8080/api/chats/user/${username}`,
        {
          headers: { 'Authorization': `Bearer ${userStore.token}` }
        }
      );
      
      // Map API response to our chat format
      chats = response.data.map((chatData: any) => {
    
        return {
          id: chatData.id,
          user1Username: chatData.user1Username,
          user2Username: chatData.user2Username,
          timeLastUpdated: chatData.timeLastUpdated
        };
      });

      return chats;
      
    } catch (err) {
      console.error('Failed to fetch chats:', err);
    }
}

export async function fetchChat(username1: string, usernam2: string): Promise<Chat> {
    console.log("Fetching chat between users")
    const userStore = useUserStore();

    const chatRequest: ChatReuest = {
        user1Username: username1,
        user2Username: usernam2
    }

    try {
        const response = await axios.post(
          `http://localhost:8080/api/chats`,
          chatRequest,
          {
            headers: { 'Authorization': `Bearer ${userStore.token}` }
          }
        );

        const chat: Chat = response.data;

        return chat;
    } catch (err) {
        console.error('Error fetching chat', err)
        throw err;
    }
}