import "./App.css";
import Carousel from "./components/Carousel";
import airpods from "./assets/airpods.png";
import iphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";

function App() {
	const images = [
		{ url: airpods, description: ["Buy a Tablet or xPhone for college.", "Get airpods."] },
		{ url: iphone, description: ["xPhone", "Lots to love.Less to spend", "Starting at $399."] },
		{ url: tablet, description: ["Tablet", "Just the right amount of everything."] },
	];
	return (
		<div className="App">
			{/* write your component here */}
			<Carousel images={images} />
		</div>
	);
}

export default App;
