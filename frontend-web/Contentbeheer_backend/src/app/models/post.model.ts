export interface Post {
    id: number;
    title: string;
    content: string;
    author: string;
    date: string;
    status: 'DRAFT' | 'PUBLISHED' | 'PENDING';
    rejectionReason: string | null;
  }
  