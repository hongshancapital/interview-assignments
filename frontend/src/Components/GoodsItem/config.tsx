import { CSSProperties } from 'react';

export type GoodsItemType = {
  title: string | string[];
  description?: string | string[];
  styles?: Partial<CSSProperties>;
};

export const dataSource: GoodsItemType[] = [
  {
    title: 'xPhone',
    description: ['Lots to Love. Less to spend.', 'Starting at $399.'],
    styles: {
      color: '#fff',
      backgroundImage: `url(${require('../../assets/iphone.png')})`,
      backgroundColor: '#111',
      backgroundSize: '50% auto',
    },
  },
  {
    title: 'Tablet',
    description: 'Just the right amount of every thing',
    styles: {
      color: '#000',
      backgroundImage: `url(${require('../../assets/tablet.png')})`,
      backgroundColor: '#fafafa',
    },
  },
  {
    title: ['Buy a Tablet or xPhone for college.', 'Get airPods'],
    styles: {
      color: '#000',
      backgroundImage: `url(${require('../../assets/airpods.png')})`,
      backgroundColor: '#f1f1f3',
    },
  },
];
