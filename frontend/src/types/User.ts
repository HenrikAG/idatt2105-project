import type { Product } from "./Product";

export interface User {
    id: number;
    name: string;
    email: string;
    phoneNumber?: string;
    products?: Product[];
    imageUrl?: string;
    validMarker: boolean;
}