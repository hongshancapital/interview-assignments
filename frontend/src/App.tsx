import './App.css';
import Swipe from './carousel/Swipe'
import SwipeItem from './carousel/SwipeItem'

function App() {

  const config = {
    num: 3,
    duration: 5000
  }

  return (
    <div className='App'>
      <Swipe config={config}>
        <SwipeItem></SwipeItem>
      </Swipe>
    </div>
  );
}

export default App;
