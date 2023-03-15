import './App.css';
import CarouselBox from './comp/carousel';
import Page1 from './page/page1';
import Page2 from './page/page2';
import Page3 from './page/page3';

function App() {
  return (
    <div className='App'>
      <CarouselBox>
        <Page1 />
        <Page2 />
        <Page3 />
      </CarouselBox>
    </div>
  );
}

export default App;
