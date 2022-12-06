import './App.css';
import Carousel from './components/carousel/Carousel';

function App() {
  const data = [
    {
      title: 'xPhone',
      subTitle: 'Lots to love. Less to spend.<br/>Starting at $399.',
      color: '#fff',
      img: require('./assets/iphone.png'),
      bgColor: '#111'
    },
    {
      title: 'Tablet',
      subTitle: 'Just the right amount of everything.',
      color: '#000',
      img: require('./assets/tablet.png'),
      bgColor: '#fafafa'
    },
    {
      title: 'Buy a Tablet or xPhone for college.<br/>Get arPods.',
      color: '#000',
      img: require('./assets/airpods.png'),
      bgColor: '#f1f1f3'
    }
  ]
  return <div className='App'><Carousel data={data} interval={2000} duration={500} /></div>;
}

export default App;
