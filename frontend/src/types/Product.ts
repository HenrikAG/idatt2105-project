export interface Product {
    id: number;
    item_name: string;
    description: string;
    price: number;
    image_url?: string;
    category: string;
    location?: string;
    userId: number;
  }