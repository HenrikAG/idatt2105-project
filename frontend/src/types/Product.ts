export interface Product {
    item_id: number;
    item_name: string;
    description: string;
    price: number;
    image_url?: string;
    category: string;
    location?: string;
    seller_name: string;
  }