import './App.css';
import Carousel from './components/Carousel/Index'

const assetsMap: Record<string, string> = {
  tablet: require('./assets/tablet.png'),
  iphone: require('./assets/iphone.png'),
  airpods: require('./assets/airpods.png')
}

function App() {
  const iPhoneSlogan = (
    <div style={{color: 'white', fontSize: '30px'}}>
      <div style={{fontSize: '60px'}}>xPhone</div>
      <div>Lots to love.Less to spend <br/> Starting at $399</div>
    </div>
  )

  const tabletSlogan = (
    <div style={{fontSize: '30px'}}>
      <div style={{fontSize: '60px'}}>Tablet</div>
      <div style={{color: '#494949'}}>Just right amount of everything.</div>
    </div>
  )

  const airpodsSlogan = (
    <div style={{fontSize: '30px'}}>
      <div style={{fontSize: '60px'}}>Tablet</div>
      <div style={{color: '#494949'}}>Just right amount of everything.</div>
    </div>
  )

  const itemsConfig: Array<SCDT.ICarouselItemOption> = [{
    slogan: iPhoneSlogan,
    backgroundImage: assetsMap['iphone']
  }, {
    slogan: tabletSlogan,
    backgroundImage: assetsMap['tablet']
  }, {
    slogan: airpodsSlogan,
    backgroundImage: assetsMap['airpods']
  }]

  return <div className='App'>
    <Carousel
      items={itemsConfig}
      duration={4000}
    />
  </div>;
}

export default App;
