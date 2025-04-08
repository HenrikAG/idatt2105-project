export interface Message {
    isSender: boolean; // Indicates if the message is sent by the user or received
    content: string; // Content of the message
    timestamp: Date; // Timestamp of when the message was sent
}