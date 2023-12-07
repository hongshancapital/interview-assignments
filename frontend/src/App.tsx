import './App.css';
import Carousel from './components/Carousel'

const images = [
  {
    url: 'airpods.png',
    renderText: () => {
      return <div className='text' style={{ top: '20%', fontSize: '50px', fontWeight: '500' }}>
        <div>Buy a Tablet or xPhone for college.</div>
        <div>Get arpods.</div>
      </div>
    },

  },
  {
    url: 'iphone.png',
    renderText: () => {
      return <div className='text' style={{ top: '20%', color: '#fff' }}>
        <div style={{ fontSize: '40px', fontWeight: '500' }}>xPhone</div>
        <div>Lots to love. Less to spend.</div>
        <div>Starting at $399.</div>
      </div>
    },

  },
  {
    url: 'tablet.png',
    renderText: () => {
      return <div className='text' style={{ top: '80px' }}>
        <div style={{ fontSize: '50px', fontWeight: 'bold' }}>Tablet</div>
        <div>Just the right amount of everything.</div>
      </div>
    },

  }


]
function App() {
  return <div className='App'>
    <Carousel images={images} interval={2000} width={1000}/>
  </div>;
}

export default App;
