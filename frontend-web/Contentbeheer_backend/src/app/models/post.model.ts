export interface Post {
    id: number;
    title: string;
    content: string;
    author: string;
    dateCreated: string;
    status: 'DRAFT' | 'PUBLISHED';
  }
  