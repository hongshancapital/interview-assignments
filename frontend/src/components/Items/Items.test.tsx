import React from 'react';
import { render, cleanup, screen } from '@testing-library/react';
import Items from '../Items/Items';

describe('<Items>{children}</Item>', () => {
	afterEach(cleanup);

	it('should render if has children', async () => {
    const mUseRef = { current: null };

		const { container } = render(<Items
      carouselRef={mUseRef}
      className="test-items"
    >
      <span>1</span>
      <span>2</span>
      <span>3</span>
    </Items>);

    const items = container.querySelector('.test-items');
		expect(items).toBeInTheDocument();
	});

	it('should not render if children is empty', async () => {
    const mUseRef = { current: null };

		const { container } = render(<Items
      carouselRef={mUseRef}
      className="test-items"
    />);

    const items = container.querySelector('.carousel-wrap');
		expect(items).not.toBeInTheDocument();
	});

	it('should render the true length of children, if infinite is false', async () => {
    const mUseRef = { current: null };

		const { container } = render(<Items
      carouselRef={mUseRef}
      className="test-items"
      infinite={false}
    >
      <span>1</span>
    </Items>);

    const itemsChildren = container.querySelectorAll('span');
    expect(itemsChildren.length).toEqual(1);
	});

	it('should render the length + 2 of children', async () => {
    const mUseRef = { current: null };

		const { container } = render(<Items
      carouselRef={mUseRef}
      className="test-items"
    >
      <span>1</span>
      <span>2</span>
      <span>3</span>
    </Items>);

    const itemsChildren = container.querySelectorAll('span');
    expect(itemsChildren.length).toEqual(3 + 2);
	});

});
