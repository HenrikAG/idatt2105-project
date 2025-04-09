export interface Message {
    id: number; // Unique identifier for the message
    chatId : number; // id for the current chat
    senderUsername: string; // Username of the sender
    content: string; // Content of the message
    timestamp : Date; // Timestamp of when the message was sent
}