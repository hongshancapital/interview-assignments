import './App.css';
import Carousel from './components/Carousel';

function App() {

  const slides = [
    {
      bgColor: 'hsl(0deg 0% 6%)',
      textColor: '#fff',
      title: 'xPhone',
      text: 'Lots to love, Less to spend.\nStarting at $399.',
      img: 'iphone.png'
    },
    {
      bgColor:'hsl(0deg 0% 98%)',
      title: 'Tablet',
      text: 'Just the right amount of everything.',
      img: 'tablet.png'
    },
    {
      bgColor: 'hsl(240deg 8% 95%)',
      title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
      text: '',
      img: 'airpods.png'
    }
  ]


  return <div className='App'>{
    /* write your component here */}
    <Carousel slides={slides} interval={3000} />
    </div>;
}

export default App;
