import "./App.css";
import Carousel from "./components/Carousel";
import imgIphone from "./assets/iphone.png";
import imgTablet from "./assets/tablet.png";
import imgAirpods from "./assets/airpods.png";

function MySlider() {
  const sliders = [
    {
      name: "xphone",
      img: imgIphone,
      title: 'xPhone',
      desc: 'Lots to love.Less to spend.\nStarting at $399.'

    },
    {
      name: "tablet",
      img: imgTablet,
      title: 'Tablet',
      desc: 'Just the right amount of everything.',
    },
    {
      name: "airpods",
      img: imgAirpods,
      title: 'Buy a Tablet or xPhone for college.\nGet arPods.', // airpods ??
    },
  ];

  return (
    <Carousel>
      {sliders.map(s => (
        <div className={`slide ${s.name}`} key={s.name}>
          {s.title && <div className="slide__title title">{s.title}</div>}
          {s.desc && <div className="slide__desc text">{s.desc}</div>}
          <div className="slide__img-container">
            <img className="slide__img" src={s.img} alt={s.name} />
          </div>
        </div>
      ))}
    </Carousel>
  );
}

function App() {
  return (
    <div className="App">
      <MySlider />
    </div>
  );
}

export default App;
