import Carousel from './Carousel';
import type { CarouselOptionType } from './Carousel';
import './App.css';

const carouselOptions: Array<CarouselOptionType> = [{
	render() {
		return (
			<>
				<h1 className="slide-title">xPhone</h1>
				<p>Lots to love. Less to spend.</p>
				<p>Starting at $399.</p>
			</>
		);
	},
    id: 0,
	className: 'iphone-slide',
}, {
    id: 1,
	title: 'Tablet',
	className: 'tablet-slide',
	content: 'Just the right amount of everything.',
}, {
    id: 2,
	className: 'airpods-slide',
	content: <>Buy a Tablet or xPhone for college.<br />Get arPods.</>,
}];

function App() {
    return (
        <div className='App'>
            <Carousel options={carouselOptions} />
        </div>
    );
}

export default App;
