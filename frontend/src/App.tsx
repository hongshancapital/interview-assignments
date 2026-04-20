import './App.css';
import { Carousel } from './components';

const SLIDE_DATA = [
  {
    title: 'xPhone',
    texts: ['Lots to love.Less to spend.', 'Starting at $399.'],
    className: 'App-slide-phone',
  },
  {
    title: 'Tablet',
    texts: ['Just the right amount of everything'],
    className: 'App-slide-tablet',
  },
  {
    title: (
      <>
        Buy a Tablet or xPhone for college .<br />
        Get arPods.
      </>
    ),
    texts: [],
    className: 'App-slide-arpods',
  },
];

function App() {
  return (
    <div className="App">
      <Carousel count={SLIDE_DATA.length} speed={1000} duration={3000}>
        {SLIDE_DATA.map(({ className, title, texts }, idx) => (
          <Carousel.Slide
            className={`App-slide ${className || ''}`}
            key={idx}
          >
            <h2 className="App-title">{title}</h2>
            {texts.map((text, idx2) => (
              <p className="App-text" key={idx2}>
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
