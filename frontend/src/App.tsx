import './App.css';
import Carousel from './Carousel'
function App() {
  interface sliders {
    imgUrl: string;
    text: string
  }
  const slidesArr:sliders[] = [
    {
      imgUrl: require('./assets/iphone.png'),
      text: 'xPhone | Lots to love. Less to spend. | Starting at $399.' 
    },
    {
      imgUrl: require('./assets/tablet.png'),
      text: 'Tablet | Just the right amount of everything.' 
    },
    {
      imgUrl: require('./assets/airpods.png'),
      text: 'Buy a Tablet or Phone for college. | Get arPods.' 
    }]

  return <div className='App'>
    <Carousel slides={slidesArr} />
  </div>;
}

export default App;
