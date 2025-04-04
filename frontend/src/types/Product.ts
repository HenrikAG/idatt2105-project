export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    imageUrl?: string;
    categoryId: number;
    createdAt: string;
    updatedAt: string;
    userId: number;
  }