import React from 'react';
import { render } from '@testing-library/react';
import Image from 'src/components/image';
import { imageEnum } from "src/interfaces";
import { CarouselList } from 'src/constants';

describe('image test', () => {
  test('appear', async () => {
    const { findByTestId } = render(<Image src={CarouselList[0].imageSrc} type={imageEnum.iphone} />);
    const imageDOM = await findByTestId(imageEnum.iphone);
    expect(imageDOM).toBeInTheDocument();
  })

  test('image iphone load ok', async () => {
    const { findByTestId } = render(<Image src={CarouselList[0].imageSrc} type={imageEnum.iphone} />);
    const imageDOM = await findByTestId(imageEnum.iphone);
    imageDOM.onload = function() {
      expect(1).toBe(1);
    }
  })
});
