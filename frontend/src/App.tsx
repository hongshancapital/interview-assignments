import './App.css';
import Carousel from './Carousel';
function App() {
  return (
  <div className='App' style={{width: '100vw', height: '100vh'}}>
    <Carousel dotPosition='bottom' needArrows={false} autoplay={true}>
     <div style={{ 
        background: `url(${require('./assets/iphone.png')}) center center/100% 100% no-repeat`, 
        height: '100vh', 
        backgroundSize: 'cover',
        color: '#ffffff'
      }}>
      <h2>xPhone</h2>
      <p>
       lots to love.less to speed, Starting at $399
      </p>
    </div>
    <div style={{ background: `url(${require('./assets/tablet.png')}) center center/100% 100% no-repeat`, height: '100vh', backgroundSize: 'cover'}}>
       <h2>Tablet</h2>
      <p>
       just to right amount of everything
      </p>
    </div>
    <div style={{ background: `url(${require('./assets/airpods.png')}) center center/100% 100% no-repeat`, height: '100vh', backgroundSize: 'cover'}}>
      <h2>Buy a Tablet or xPhone for college</h2>
    </div>
  </Carousel>
  </div>
  ) 
}

export default App;
