import Carousel, { CarouselCard } from './components/carousel';
import './App.css';
import IPhonePic from './assets/iphone.png';
import TabletPic from './assets/tablet.png';
import AirPodsPic from './assets/airpods.png';

const CAROUSEL_ITEM_LIST = [
  {
    id: 1,
    title: 'xPhone',
    desc: 'Lots to love.Less to spend. \n Starting at $399.',
    bgImage: IPhonePic,
    color: '#fff',
    bgColor: '#101010',
  },
  {
    id: 2,
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    bgImage: TabletPic,
    color: '#000',
    bgColor: '#fafafa',
  },
  {
    id: 3,
    title: 'Buy a Tablet or a xPhone for college.\nGet arPods.',
    bgImage: AirPodsPic,
    color: '#000',
    bgColor: '#f2f2f4',
  },
];

function App() {
  return (
    <div className="App">
      <Carousel>
        {CAROUSEL_ITEM_LIST.map(data => (
          <CarouselCard
            key={data.id}
            title={data.title}
            desc={data.desc}
            bgImage={data.bgImage}
            color={data.color}
            bgColor={data.bgColor}
          />
        ))}
      </Carousel>
    </div>
  );
}

export default App;
