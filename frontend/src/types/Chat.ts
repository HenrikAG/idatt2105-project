import type { Message } from "./Message";

export interface Chat {
    messages: any;
    id: number; // Unique identifier for the chat
    user1Username : string; // Username of the first user in the chat
    user2Username : string; // Username of the second user in the chat
    otherUsername : string; // Username of the other user in the chat
    timeLastUpdated : Date; // Timestamp of when the chat was last updated
}