import { Carousel } from './components/Carousel/carousel';
import AirpodsPng from './assets/airpods.png';
import IphonePng from './assets/iphone.png';
import TabletPng from './assets/tablet.png';
import styles from './App.module.scss';

export const PAGES = [
  {
    image: IphonePng,
    title: <h1>xPhone</h1>,
    content: <div>Lots to love. Less to spend.</div>,
    description: <p>Starting at $399.</p>,
    style: {
      backgroundColor: 'rgb(17,17,17)',
      color: '#fff',
    },
  }, {
    image: TabletPng,
    title: <h1>Tablet</h1>,
    content: <div>Just the right amount of everything</div>,
    style: {
      backgroundColor: '#fff',
    },
  },
  {
    image: AirpodsPng,
    title: <h1>Buy a Tablet or xPhone for college.<br />Get arPods.</h1>,
    style: {
      backgroundColor: 'rgb(241,241,243)'
    }
  }
];

function App() {
  const elements = PAGES.map((it, index) => {
    const pageStyle = {
      backgroundImage: `url(${it.image})`,
      ...(it.style || {})
    }
    return (
      <div style={pageStyle} className={styles.page} key={index}>
        {it.title}
        {it?.content}
        {it?.description}
      </div>
    )
  });
  return (
    <div className={styles.app} data-testid="app-container">
      <Carousel className={styles.carousel} duration={3000}>
        {elements}
      </Carousel>
    </div>
  );
}

export default App;
