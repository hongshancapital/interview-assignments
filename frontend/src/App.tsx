import './App.css';
import Carousel from './Carousel';

function App() {
  return <div className='App'>
    {/* write your component here */}
    <Carousel list={[
      {
        className: 'card card-1',
        content: <>
          <span style={{ color: '#fff' }}>
            <h1 className='title'>xPhone</h1>
            <p className="text">Lots to love. Less to spend</p>
            <p className="text">Starting at $399.</p>
          </span>

          <div className='img img-1'></div>
        </>
      },
      {
        className: 'card card-2',
        content: <>
          <h1 className='title' >Tablet</h1>
          <p className="text">Just the right amount to every thing.</p>
        </>
      },
      {
        className: 'card card-3',
        content: <>
          <h1 className='title' >Buy a Tablet or xPhone for college.</h1>
          <h1 className='title' >Get arPods.</h1>
        </>
      }
    ]} duration={3000}/>
  </div>;
}

export default App;
