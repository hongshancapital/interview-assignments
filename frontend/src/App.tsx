import './App.css';
import Carousel from './components/Carousel';

export const SLIDE_DATA = [
  {
    id: 1,
    title: 'xPhone',
    description: (
      <>
        Lots to love.Less to spend.
        <br />
        Starting at $399.
      </>
    ),
    className: 'App-slide-phone',
  },
  {
    id: 2,
    title: 'Tablet',
    description: <>Just the right amount of everything.</>,
    className: 'App-slide-tablet',
  },
  {
    id: 3,
    title: (
      <>
        Buy a Tablet or xPhone for college.
        <br />
        Get arPods.
      </>
    ),
    description: '',
    className: 'App-slide-arpods',
  },
];

function App() {
  return (
    <div className="App">
      <Carousel list={SLIDE_DATA} />
    </div>
  );
}

export default App;
