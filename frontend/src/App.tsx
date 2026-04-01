import Carousel from './components/Carousel';
import { CarouselItem } from './components/Carousel/types';
import './App.css';

const dataSource: CarouselItem[] = [
  {
    title: 'Tablet',
    theme: 'Lighten',
    descriptions: ['Just the right amount of everything.'],
    image: 'https://img.alicdn.com/imgextra/i1/O1CN01vgHRN61bd5usKPMkZ_!!6000000003487-0-tps-290-290.jpg'
  },
  {
    title: 'xPhone',
    theme: 'Darken',
    descriptions: ['Lots to love,Less to spend.', 'Starting at $399'],
    image: 'https://img.alicdn.com/imgextra/i3/O1CN01kWJzWD1gcFGJeTHik_!!6000000004162-0-tps-264-316.jpg'
  },
  {
    title: 'Buy a Tablet or xPhone for college. Get arPods.',
    theme: 'Lighten',
    descriptions: [],
    image: 'https://img.alicdn.com/imgextra/i3/O1CN01VAEHLG1joYkvCuNVj_!!6000000004595-0-tps-200-200.jpg'
  }
];

// 测试大数据渲染情况
// for (let i = 0; i < 1000; i++) {
//   dataSource.push({
//     title: `xPhone ${i}`,
//     theme: 'Darken',
//     descriptions: ['Lots to love,Less to spend.', 'Starting at $399'],
//     image: 'https://img.alicdn.com/imgextra/i3/O1CN01kWJzWD1gcFGJeTHik_!!6000000004162-0-tps-264-316.jpg'
//   });
// }

function App() {
  return <div className='App'>
    <Carousel dataSource={dataSource} duration={3000} />
  </div>;
}

export default App;
