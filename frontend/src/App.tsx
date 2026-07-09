import './App.css';
import {Carousel, CarouselItem} from  './components';

function App() {
  return <div className='App'>
      <Carousel 
        onChange={(oldIndex: number, newIndex: number) => {
          // console.log("oldIndex:", oldIndex, "newIndex:", newIndex)
        }}
      >
          <CarouselItem className='item item1'>
            <p className='title'>xPhone</p>
            <p className='text'>Lots to love. Less to spend</p>
            <p className='text'>Starting at $399.</p>
          </CarouselItem>
          <CarouselItem className='item item2'>
            <p className='title'>Tablet</p>
            <p className='text'>Just the right amount of everything.</p>
          </CarouselItem>
          <CarouselItem className='item item3'>
            <p className='title'>Buy a Tablet or Phone for college.</p>
            <p className='title'>Get arPods.</p>  
          </CarouselItem>
      </Carousel>
  </div>;
}

export default App;