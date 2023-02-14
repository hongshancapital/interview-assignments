import './App.css';
import Carousel from './components/carousel';

const contentStyle: React.CSSProperties = {
  margin: 0,
  height: '160px',
  color: '#fff',
  lineHeight: '160px',
  textAlign: 'center',
  background: '#364d79',
};

function App() {
  const onChange = (currentSlide: number) => {
    console.log(currentSlide);
  };

  return <div className='App'>
    <Carousel afterChange={onChange} autoplay={true}>
      <div>
        <h3 style={contentStyle}>1</h3>
      </div>
      <div>
        <h3 style={contentStyle}>2</h3>
      </div>
      <div>
        <h3 style={contentStyle}>3</h3>
      </div>
      <div>
        <h3 style={contentStyle}>4</h3>
      </div>
      <div>
        <h3 style={contentStyle}>5</h3>
      </div>
    </Carousel>
  </div>;
}

export default App;
