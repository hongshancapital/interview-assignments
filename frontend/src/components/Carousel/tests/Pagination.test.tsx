import { render } from '@testing-library/react'
import { CarouselContext } from '../Context'
import { Pagination } from '../Pagination'

test('test carousel pagination', () => {
    const slidesCount = 5;
    const activedIndex = 3;
    const { getByTestId } = render(<CarouselContext.Provider value={{ autoPlay: true, autoPlayInterval: 3, activedIndex, slidesCount }}>
        <Pagination/>
    </CarouselContext.Provider>)
    const pagination = getByTestId('carousel-pagination');
    expect(pagination.children.length).toBe(slidesCount)
    expect(pagination.children[activedIndex].querySelector('.actived')).not.toBeNull()
})