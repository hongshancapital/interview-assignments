import Carousel from './components/Carousel';
import Layout from './components/Layout';
import './App.css';

function App() {
  return (
    <div className="app">
      <Carousel autoplay>
        <Carousel.Item>
          <Layout
            theme="dark"
            title="xPhone"
            desc={
              <>
                Lots to love. Less to spend.
                <br />
                Starting at $399.
              </>
            }
            bg={require('./assets/iphone_icon.png')}
          />
        </Carousel.Item>
        <Carousel.Item>
          <Layout
            theme="light"
            title="Tablet"
            desc="Just the right amount of everything."
            bg={require('./assets/tablet_icon.png')}
          />
        </Carousel.Item>
        <Carousel.Item>
          <Layout
            theme="gray"
            title={
              <>
                Buy a Tablet or Phone for college.
                <br />
                Get airPods.
              </>
            }
            bg={require('./assets/airpods_icon.png')}
          />
        </Carousel.Item>
      </Carousel>
    </div>
  );
}

export default App;
