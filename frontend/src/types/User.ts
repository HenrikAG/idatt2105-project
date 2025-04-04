import type { Product } from "./Product";

export interface User {
    id: number;
    name: string;
    email: string;
    phoneNumber?: string;
    address?: string;
    createdAt: string;
    updatedAt: string;
    products?: Product[];
    imageUrl?: string;
    validMarker: boolean;
}