import './styles/carousel.scss';
import Layer from './components/Layer';
import Slide from './components/Slide';
import Carousel from './components/Carousel';

declare module 'react' {
    interface HTMLAttributes<T> extends AriaAttributes, DOMAttributes<T> {
        slide?: number;
        exposure?: number;
    }
}

export { Carousel, Layer, Slide };