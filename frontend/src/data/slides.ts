import airpods from '../assets/airpods.png';
import iphone from '../assets/iphone.png';
import tablet from '../assets/tablet.png';
import { SlideProps } from '../components/Slide';

const SLIDES: SlideProps[] = [
    {
        image: iphone,
        title: 'xPhone',
        description: ['Lots to love. Less to spend.', 'Starting at $399.'],
        backgroundColor: '#0a0a0a',
        useWhiteText: true,
    },
    {
        image: tablet,
        title: 'Tablet',
        description: 'Just the right amount of everything.',
        backgroundColor: '#fafafa',
    },
    {
        image: airpods,
        title: ['Buy a Tablet or Xphone for college.', 'Get airPods.'],
        backgroundColor: '#f1f1f3',
    },
];

export default SLIDES;
