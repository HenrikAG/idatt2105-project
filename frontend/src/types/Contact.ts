import type { Message } from "./Message";

export interface Contact {
    ownerId: number; // Unique identifier for the contact
    contactId: number; // Unique identifier for the contact
    messages: Message[] // Array of messages exchanged with this contact
}