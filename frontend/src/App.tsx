import { useMemo } from 'react';
import './App.css';
import { Carousel } from './Components/Carousel';
import { FullScreen, IFullScreenProps } from './Components/FullScreen';

function App() {
  const data: IFullScreenProps[] = useMemo(() => [{
    title: "xPhone",
    description: "Lots to love.Less to spend.\nStarting at $399.",
    imgUrl: require("./assets/iphone.png"),
    className: "xPhone-cls",
  },{
    title: "Tablet",
    description: "Just the right amount of everything.",
    imgUrl: require("./assets/tablet.png"),
    className: "",
  },{
    title: "Buy a Table or xPhone for college.\nGet arPods.",
    imgUrl: require("./assets/airpods.png"),
    className: "",
  }], [])

  return <div className='App'>
    {/* write your component here */}
    <Carousel>
      {
        data.map((item: IFullScreenProps) => <FullScreen { ...item } key={item.title} />)
      }
    </Carousel>
  </div>;
}

export default App;
