import SockJS from 'sockjs-client'
import Stomp, { Client } from 'webstomp-client'

let stompClient: Client | null = null;

export const connectWebSocket = (token: string) => {
    const socket = new SockJS('http://localhost:8080/ws')
    stompClient = Stomp.over(socket);

    stompClient.connect(
        { Authorization: `Bearer ${token}` },
        () => {
            console.log('Websocket connected');
        },
        (error) => {
            console.log('Websocket error', error);
        }
    );
}

export function subscribeToChat(chatId: number, callback: (msg: any) => void) {
    if (!stompClient || !stompClient.connected) return;

    stompClient.subscribe(`/topic/chat/${chatId}`, (message) => {
        const body = JSON.parse(message.body);
        callback(body);
    });
}

export function disconnectWebSocket() {
    if (stompClient) {
        stompClient.disconnect(() => {
            console.log('WebSocket disconnected')
            stompClient = null;
        });
    }
}