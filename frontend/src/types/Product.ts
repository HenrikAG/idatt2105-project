export interface Product {
    id: number;
    item_name: string;
    description: string;
    price: number;
    imageURL?: string;
    category: string;
    location?: string;
    userId: number;
  }