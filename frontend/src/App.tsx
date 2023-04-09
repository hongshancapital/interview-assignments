import './App.css';
import { Carousel } from './components';

const SLIDE_DATA = [
  {
    title: 'xPhone',
    texts: ['Lots to love.Less to spend.', 'Starting at $399.'],
    className: 'App__slide__phone',
  },
  {
    title: 'Tablet',
    texts: ['Just the right amount of everything'],
    className: 'App__slide__tablet',
  },
  {
    title: (
      <>
        Buy a Tablet or xPhone for college .<br />
        Get arPods.
      </>
    ),
    texts: [],
    className: 'App__slide__arpods',
  },
];
const COUNT = SLIDE_DATA.length;
function App() {
  return (
    <div className="App">
      {/* write your component here */}
      <Carousel count={COUNT} speed={300} delay={3000}>
        {SLIDE_DATA.map((el, idx) => (
          <Carousel.Slide
            className={`App__slide ${el.className || ''}`}
            key={idx}
          >
            <h2 className="App__title">{el.title}</h2>
            {el.texts.map((text, idx2) => (
              <p className="App__text" key={idx2}>
                {text}
              </p>
            ))}
          </Carousel.Slide>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
