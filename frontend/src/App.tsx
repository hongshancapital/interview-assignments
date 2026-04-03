import Carousel from './components/Carousel';

import './App.css';

const list = [
  {
    id: 1,
    image: require('./assets/iphone.png'),
    title: ['xPhone'],
    describe: ['Lots to love. Less to spend.','Starting at $399.'],
    color: '#fff',
    bgColor: 'rgba(17, 17, 17)'
  },
  {
    id: 2,
    image: require('./assets/tablet.png'),
    title: ['Tablet'],
    describe: ['Just the right amount of everything.'],
    color: '#000',
    bgColor: '#fafafa'
  },
  {
    id: 3,
    image: require('./assets/airpods.png'), 
    title: ['Buy a Tablet or xPhone for college. ','Get airpods.'],
    describe: [],
    color: '#000',
    bgColor: 'rgba(241, 241, 243)'
  },
]

function App() {
  return (
    <div className='App'>
      <Carousel>
        {
          list.map(item => (
            <div 
              className="page" 
              key={item.id}
              style={{
                color: item.color,
                backgroundColor: item.bgColor,
                backgroundImage: `url(${item.image})`
              }}
            >
              <div className="main">
                {
                  item.title.map((title, index) => (
                    <div className="title" key={index}>{ title }</div>
                  ))
                }
                {
                  item.describe.map((describe, index) => (
                    <div className="describe" key={index}>{ describe }</div>
                  ))
                }
              </div>
            </div>
          ))
        }
      </Carousel>
    </div>
  )
}

export default App;
