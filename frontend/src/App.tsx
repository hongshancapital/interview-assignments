import './App.css';
import { Carousel } from './Components';
import img_airpods from './assets/airpods.png'
import img_iphone from './assets/iphone.png'
import img_tablet from './assets/tablet.png'

function App() {
  return <div className='App banner'>
    <Carousel
      source={[
        {
          bgPath: img_iphone,
          bgStyle: {
            backgroundSize: "57%",
            backgroundColor: "#111111",
            backgroundPosition: "center 70%",
          },
          content: (
            <div className='t-white'>
              <h2 className="title">xPhone</h2>
              <p className='dec'>Lots to love. Less to spend. <br />Starting at $399</p>
            </div>
          )
        },
        {
          bgPath: img_tablet,
          bgStyle: {
            backgroundSize: "87%",
            backgroundColor: "#fafafa",
            backgroundPosition: "center 72%",
          },
          content: (
            <div className='t-black'>
              <h2 className="title">Tablet</h2>
              <p className='dec'>Just the right amount of everything.</p>
            </div>
          )
        },
        {
          bgPath: img_airpods,
          bgStyle: {
            backgroundSize: "130%",
            backgroundColor: "#f1f1f3",
            backgroundPosition: "center 74%",
          },
          content: (
            <div className='t-black'>
              <h2 className="title">Buy a Tablet or a xPhone for college.<br/>Get airpods</h2>
            </div>
          )
        },
      ]}
    />
  </div>;
}

export default App;
