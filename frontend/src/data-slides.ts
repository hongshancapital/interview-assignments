import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';
import { TSlide } from './components/types';

const slides:Array<TSlide> = [
    {
      img: iphone ,
      backgroundColor: '#111111',
      imgWidth: 960,
      imgHeight: 600,
      title: 'xPhone',
      desc: 'Lots to love.less to spend.<br />Starting at $399',
      textColor: '#ffffff'
    },
    {
      img: tablet,
      backgroundColor: '#fafafa',
      imgWidth: 1920,
      imgHeight: 600,
      title: 'Tablet',
      desc: 'Just the right amount of everything.',
      textColor: '#111111',
    },
    {
      img: airpods,
      backgroundColor: '#f1f1f3',
      imgWidth: 2880,
      imgHeight: 600,
      title: 'Buy a Tablet or xPhone for college.<br />Get arPods.',
      desc: '',
      textColor: '#111111'
    }
  ];

  export default slides;