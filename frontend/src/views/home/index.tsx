import { FunctionComponent } from 'react';
import Carousel from '../../components/Carousel';
import './index.scss';
import PageXPhone from './components/PageXPhone';
import PageTablet from './components/PageTablet';
import PageAirPods from './components/PageAirPods';

interface Props {

}

const Home: FunctionComponent = (props: Props) => {

    const components: React.ReactNode[] = [
        <PageXPhone></PageXPhone>,
        <PageTablet></PageTablet>,
        <PageAirPods></PageAirPods>
    ];
    return (
        <div className={'page-container home'}>
            <Carousel components={components}
                indicatorProps={{ type: 'dot' }}
                orientation={'horizontal'}
                showIndicator={true}></Carousel>
        </div>
    );
};

export default Home;
