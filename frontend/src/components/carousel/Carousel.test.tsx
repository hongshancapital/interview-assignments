import {render} from '@testing-library/react';

import{ Carousel } from './Carousel';
import {Slide} from './Slide';

describe('Test Carousel', () => {
    test('Test carousel and its children elements', () => {
        const { baseElement, getByRole, getAllByRole } = render(<Carousel height="500px" width="100vw" time={3000}>
        <Slide height="500px" width="100vw" title="xPhone" subTitle={'zzz'}/>
        <Slide height="500px" width="100vw" title="Tablet" subTitle="yyy"/>
        <Slide height="500px" width="100vw" title="Test" subTitle="xxx"/>
      </Carousel>);
        const carousel = getByRole('carousel');
        expect(carousel).toBeInTheDocument();

        const slides = getAllByRole('slide');
        // show first slide at the beginning
        expect(slides.length).toEqual(1);

        const itemBars = getByRole('itembars');
        expect(itemBars).toBeInTheDocument();

        const items = getAllByRole('itembar');
        expect(items.length).toEqual(3);
    });
});