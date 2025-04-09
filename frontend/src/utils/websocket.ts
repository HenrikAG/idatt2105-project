import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

let stompClient = null;

export const connectWebSocket = (token: string, onMessageReceived: (msg: any) => void) => {
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
    if (!stompClient || !stompClient.connected)
}