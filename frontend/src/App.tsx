import React from 'react';
import './App.css';
import Carousel from './Carousel';
import CarouselItem from './CarouselItem';

function App() {
	return (
		<div className="App">
			<Carousel loop={false}>
				<CarouselItem
					itemStyle={{ backgroundColor: '#111111', color: '#fff' }}
					imageUrl={require('./assets/iphone.png')}
					titles={['xPhone']}
					texts={['Lots to love.Less to spend.', 'Starting at $399.']}
				/>
				<CarouselItem
					itemStyle={{ backgroundColor: '#FAFAFA' }}
					imageUrl={require('./assets/tablet.png')}
					titles={['Tablet']}
					texts={['Just the right amount of everything.']}
				/>
				<CarouselItem
					itemStyle={{ backgroundColor: '#F1F1F3' }}
					imageUrl={require('./assets/airpods.png')}
					titles={['Buy a Tablet or xPhone for college.', 'Get arPods.']}
				/>
			</Carousel>
		</div>
	);
}

export default App;
