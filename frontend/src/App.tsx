import './App.css';
import Carousel from './components/Carousel';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

export const resources = [
  {
    title: {
      text: 'xPhone',
    },
    description: {
      text: 'lots to love, less to spend.',
      extra: 'Starting at $399.',
    },
    img: iphone,
    styles: { backgroundPosition: 'bottom', color: '#fff' },
  },
  {
    title: { text: 'Tablet' },
    description: { text: 'Just the right amount of everything.' },
    img: tablet,
    styles: {
      backgroundPosition: 'bottom',
      backgroundSize: '180%',
    },
  },
  {
    title: {
      text: 'Buy a Tablet or xPhone for college.',
      extra: 'Get airPods',
    },
    img: airpods,
    styles: { backgroundPosition: 'bottom', backgroundSize: '250%' },
  },
];

function App() {
  return (
    <div className="App">
      <Carousel
        resources={resources}
      />
    </div>
  );
}

export default App;
