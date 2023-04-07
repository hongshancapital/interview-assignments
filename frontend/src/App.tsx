import "./App.css";
import Carousal from "./components/carousal";
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

function App() {
  const dataSource = [
    {
      background: iphone,
      title: ['xPhone'],
      desc: ['Lots to love. Less to spend', 'Starting at $399'],
      key: '001'
    },
    {
      background: tablet,
      title: ['Tablet'],
      desc: ['Just the right amount of everyting.'],
      key: '002'
    },
    {
      background: airpods,
      title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
      key: '003'
    }
  ];
  return (
    <div className="App">
      <Carousal dataSource={dataSource} autoplay />
    </div>
  );
}

export default App;
