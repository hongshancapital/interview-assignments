import './App.css';
import Carousel from './components/Carousel'
import { airpods,iphone,tablet} from './assets'

function App() {
  return <div className='App'><Carousel images={[iphone,tablet,airpods]} 
  texts={[
    <div>
       <div className='carousel-item-title'>xPhone</div>
        <div className='carousel-item-describe' style={{  fontWeight: '400'}}>Lots to love.Less to spend.<br/>Starting at $399</div>
    </div>,
     <div>
     <div className='carousel-item-title' style={{color:'#000'}}>Tablet</div>
      <div className='carousel-item-describe' style={{color:'#000',fontWeight: '400'}}>Just the right amount of everything</div>
  </div>,
   <div>
        <div className='carousel-item-describe' style={{fontSize:50,color:'#000',fontWeight: 'bold'}}>Buy a Tablet or xPhone for college.<br/>Get arpods</div>
    </div>
]}/></div>;
}

export default App;
