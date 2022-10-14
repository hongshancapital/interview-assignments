import React from 'react';
import { render } from '@testing-library/react';
import { SlideItem } from '../slide-item';
import { SlideItemProps } from '../../mocks/slide-item';

describe('test slide-item', () => {
  it('slide-item snapshot', () => {
    // 渲染
    const { container } = render(<SlideItem {...SlideItemProps} />);
    // 快照记录
    expect(container).toMatchSnapshot();
  });

  it('slide-item elements', () => {
    const { getByAltText, getByText, getByTestId } = render(<SlideItem {...SlideItemProps} />);
    const imageElement = getByAltText('slide-img');
    const xPhoneDescElement = getByText('xPhone');
    const slideDescDom = getByTestId('slide-desc');
    expect(imageElement).toBeInTheDocument();
    expect(xPhoneDescElement).toBeInTheDocument();
    expect(slideDescDom.childNodes.length).toBe(SlideItemProps.descContents.length);
  });
});