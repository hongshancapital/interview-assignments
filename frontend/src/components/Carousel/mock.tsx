import { CarouselContents } from './Carousel';
import Airpods from '../../assets/airpods.png';
import Iphone from '../../assets/iphone.png';
import Tablet from '../../assets/tablet.png';

export const mock: CarouselContents[] = [
  {
    poster: Iphone,
    heading: <h1>xPhone</h1>,
    subHeading: (
      <div>
        <h2>Lots to love, Less to spend</h2>
        <h2>Starting at $399</h2>
      </div>
    ),
    backgroundColor: '#101010',
    color: '#ffffff',
  },
  {
    poster: Tablet,
    heading: <h1>Tablet</h1>,
    subHeading: <h2>Just the right amount of everything</h2>,
    backgroundColor: '#fafafa',
    color: '#000000',
  },
  {
    poster: Airpods,
    heading: (
      <>
        <h1>Buy a Tablet or xPhone for college</h1>
        <h1>Get arPods</h1>
      </>
    ),
    backgroundColor: '#f1f1f3',
    color: '#000000',
  },
];
