import React from "react";
import Carousel from "./components/Carousel";
import Ihpone from "./assets/img/iphone.png";
import Tablet from "./assets/img/tablet.png";
import Airpods from "./assets/img/airpods.png";
import "./App.css";

const SLIDES_DATA = [
	{
		id: 1,
		title: 'xPhone',
		text: 'Loats',
		description: 'start',
		img: Ihpone
	},
	{
		id: 2,
		title: 'xPhone',
		text: 'Tablet',
		description: '',
		img: Tablet
	},
	{
		id: 3,
		title: 'Airpods',
		text: '',
		description: '',
		img: Airpods
	}
];

const DELAY_TIME = 3000;

function App() {
  return (
  	<div className="App">
  		<Carousel slides={SLIDES_DATA} delay={DELAY_TIME} />
  	</div>
  );
}

export default App;
