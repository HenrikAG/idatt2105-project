import type { Message } from "./Message";

export interface Chat {
    contactName: string; // Unique identifier for the contact
    messages: Message[] // Array of messages exchanged with this contact
}