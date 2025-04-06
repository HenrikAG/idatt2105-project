export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    imageUrl?: string;
    categoryId: number;
    location?: string;
    //createdAt: string;
    //updatedAt: string;
    userId: number;
  }