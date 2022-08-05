import iphoneWebp from '../assets/iphone.webp';
import airpodsWebp from '../assets/airpods.webp';
import tabletWebp from '../assets/tablet.webp';

export type ItemProps = {
  id: number;
  title: string;
  content?: string;
  imgUrl: string;
  style: React.CSSProperties;
};

export const items: ItemProps[] = [
  {
    id: 1,
    title: 'xPhone',
    content: 'Lots to love. Less to spend. \n Starting at $399.',
    imgUrl: iphoneWebp,
    style: {
      backgroundColor: '#111',
      color: '#fff'
    }
  },
  {
    id: 2,
    title: 'Tablet',
    content: 'Just the right amount of everything.',
    imgUrl: tabletWebp,
    style: {
      backgroundColor: '#FAFAFA',
      color: '#111'
    }
  },
  {
    id: 3,
    title: 'Buy a Tablet or xPhone for college. \n Get airPods.',
    imgUrl: airpodsWebp,
    style: {
      backgroundColor: '#f1f1f3',
      color: '#111'
    }
  }
];
