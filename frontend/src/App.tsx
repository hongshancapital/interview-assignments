import './App.css';
import Carousel from './components/Carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

function App() {
  const images = [
		
		{ url: iphone, title: [<span style={{ color: "white" }}>xPhone</span>], description: [<span style={{ color: "white" }}>"Lots to love.Less to spend", "Starting at $399."</span>] },
		{ url: tablet, title: ["Tablet"],  description: ["Just the right amount of everything."] },
		{ url: airpods, title: ["Buy a Tablet or xPhone for college.", "Get airpods."] },
	];
	return (
		<div className="App">
			<Carousel images={images} />
		</div>
	);
}

export default App;
