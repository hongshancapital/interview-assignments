import { useCallback } from 'react';
import './App.scss';
import Carousel from './components/Carousel';
import SLIDES from './data/slides';
import Slide, { SlideProps } from './components/Slide';

function App({ testID }: { testID?: string }) {
    const renderSlide = useCallback((data: SlideProps) => {
        return <Slide {...data} />;
    }, []);
    const keyExtractor = useCallback((data: SlideProps) => {
        return data.image;
    }, []);

    return (
        <div data-testid={testID} className="App">
            <Carousel
                renderItem={renderSlide}
                keyExtractor={keyExtractor}
                items={SLIDES}
                animationConfig={{ interval: 3000 }}
            />
        </div>
    );
}

export default App;
