import {render} from '@testing-library/react';

import {Slide} from './Slide';

describe('Test Slide', () => {
    test('Test slide element', () => {
        const { baseElement, getByText, getAllByRole } = render(<Slide height="500px" width="100vw" title="Test" subTitle="xxx"/>);

        const slides = getAllByRole('slide');
        // show first slide at the beginning
        expect(slides.length).toEqual(1);

        const title = getByText('Test');
        expect(title).toBeInTheDocument();

        const subTitle = getByText('xxx');
        expect(subTitle).toBeInTheDocument();
    });
});