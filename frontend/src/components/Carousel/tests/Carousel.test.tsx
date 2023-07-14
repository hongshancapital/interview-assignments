import { render } from '@testing-library/react'
import { CarouSel } from '../Carousel'


test(
    'test auto play Carousel', () => {
        const autoPlayInterval = 3
        const { getByTestId } = render(<CarouSel autoPlayInterval={autoPlayInterval}>
            <div>Slide one</div>
            <div>Slide two</div>
            <div>Slide three</div>
        </CarouSel>)

        expect(getByTestId('carousel-slide-index-0').className).toContain('active')
        let shouldActivedIndex = 1;
        setInterval(() => {
            expect(getByTestId(`carousel-slide-index-${shouldActivedIndex}`).className).toContain('active')
            shouldActivedIndex += 1;
        }, autoPlayInterval * 1000)

    }
)