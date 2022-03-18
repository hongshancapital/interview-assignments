import airpodsImg from '../assets/airpods.png';
import iphoneImg from '../assets/iphone.png';
import tabletImg from '../assets/tablet.png';
import { CarouselDataType } from '../typings/component';

const carouselData: CarouselDataType[] = [
  {
    id: 1,
    title: ['xPhone'],
    description: ['Lots to love.Less to spend', 'Starting as $399.'],
    color: 'white',
    backgroundImg: iphoneImg
  },
  {
    id: 2,
    title: ['Tablet'],
    description: ['Just the right amount of everything.'],
    color: 'black',
    backgroundImg: tabletImg
  },
  {
    id: 3,
    title: ['Buy a Tablet or xPhone for college', 'Get airpods'],
    description: [],
    color: 'black',
    backgroundImg: airpodsImg
  },
];
export default carouselData;