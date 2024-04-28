import './App.css';
import Carousel from './components/Carousel/Carousel';
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'

const data = [{
  bgClassName: 'bg-iphone',
  title: 'xPhone',
  titleClassName: 'title color-white',
  text: 'Lots to Love. Less to spend.\nStarting at $399.',
  textClassName: 'text break-spaces color-white',
  image: iphone
}, {
  bgClassName: 'bg-tablet',
  title: 'Tablet',
  titleClassName: 'title color-black',
  text: 'Just the right amount of everything',
  textClassName: 'text color-black',
  image: tablet
}, {
  bgClassName: 'bg-airpods',
  title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
  titleClassName: 'title break-spaces color-black',
  text: '',
  textClassName: '',
  image: airpods
}]

function App() {
  return (
    <div className='App'>
      <Carousel duration={3000} speed={1000}>
        {data.map(el => (
          <Carousel.Slide
            key={el.title}
            className={el.bgClassName}
          >
            {el.title && <p className={el.titleClassName}>{el.title}</p>}
            {el.text && <p className={el.textClassName}>{el.text}</p>}
            <div className="img-wrapper">
              <img src={el.image} alt="" />
            </div>
          </Carousel.Slide>
        ))}
      </Carousel>
    </div>
  )
}

export default App;
