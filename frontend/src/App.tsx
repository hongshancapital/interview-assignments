import React from 'react';
import './App.css';
import { Carousel, CarouselItem } from './components/carousel';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

interface IDataItem {
	id: number;
	title: string;
	describe?: string;
	style?: React.CSSProperties;
}

// Carousel Item data
export const dataInfos: IDataItem[] = [
	{
		id: 1,
		title: 'XPhone',
		describe: 'Lots to love. Less to spend.\n Starting at $399.',
		style: {
			backgroundImage: `url(${iphone})`,
			backgroundColor: `#111`,
			backgroundPosition: 'center 80%',
			color: '#fff',
		},
	},
	{
		id: 2,
		title: 'Tablet',
		describe: 'Just the right amount of everything',
		style: {
			backgroundImage: `url(${tablet})`,
			backgroundColor: `#fafafa`,
		},
	},
	{
		id: 3,
		title: 'Buy a Tablet or xPhone for college.\n Get airPods',
		style: {
			backgroundImage: `url(${airpods})`,
			backgroundColor: `#fafafa`,
		},
	},
];

function App() {
	return (
		<div className="App">
			<Carousel>
				{dataInfos?.map((item, index) => {
					return (
						<CarouselItem key={index.toString()} styles={item.style}>
							<h1 className="title">{item.title}</h1>
							{item.describe && <p className="text">{item.describe}</p>}
						</CarouselItem>
					);
				})}
			</Carousel>
		</div>
	);
}

export default App;
