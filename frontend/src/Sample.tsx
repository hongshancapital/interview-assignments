import Carousel from './components/Carousel';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';
import './Sample.css';

function Sample() {
    const items = [
        <div className='item item-dark' key='xPhone'>
            <div className='title'>xPhone</div>
            <div className='description'>Lots to love. Less to spend.</div>
            <div className='description'>Starting at $399.</div>
            <div className='logo'>
                <img src={iphone} alt=''/>
            </div>
        </div>,
        <div className='item item-white' key='Tablet'>
            <div className='title title-dark'>Tablet</div>
            <div className='description description-dark'>Just the right amount of everything.</div>
            <div className='logo'>
                <img src={tablet} alt=''/>
            </div>
        </div>,
        <div className='item' key='airpods'>
            <div className='title title-dark'>Buy a Tablet or XPhone for college.</div>
            <div className='title subtitle title-dark'>Get airPods.</div>
            <div className='logo'>
                <img src={airpods} alt=''/>
            </div>
        </div>
    ];

    return <Carousel duration={2000} speed={300}>{items}</Carousel>;
}

export default Sample;