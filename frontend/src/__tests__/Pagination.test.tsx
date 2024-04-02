import { render } from '@testing-library/react';
import Pagination from '../components/Carousel/components/Pagination';

describe('Pagination', () => {
  it('should render', () => {
    const { getAllByTestId } = render(
      <div data-testid="root">
        <Pagination pagination={3} active={0} duration={3000} slideTo={() => {}}>
          123
        </Pagination>
      </div>,
    );
    const root = getAllByTestId('root');
    expect(root[0]).toContainHTML(
      '<div class="carousel-pagination">' +
        '<div class="carousel-pagination-item">' +
        '<span class="carousel-pagination-progress" style="animation: progress 3000ms linear;" />' +
        '</div>' +
        '<div class="carousel-pagination-item">' +
        '</div>' +
        '<div class="carousel-pagination-item">' +
        '</div>' +
        '</div>',
    );
  });
});
