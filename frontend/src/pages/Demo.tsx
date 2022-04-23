import React from 'react';

import Carousel from '../components/Carousel';
import Product from '../components/Product';
import xPhoneImage from '../assets/iphone.png';
import tabletImage from '../assets/tablet.png';
import airpodsImage from '../assets/airpods.png';

interface ProductModel {
  id: string;
  title: string;
  subtitle: string;
  image: string;
  theme?: 'dark' | 'light';
}

export const Demo = () => {
  const products: ProductModel[] = [
    {
      id: 'product-1',
      title: 'xPhone',
      subtitle: 'Lots to love. Less to spend. \n Starting at $399.',
      theme: 'dark',
      image: xPhoneImage,
    },
    {
      id: 'product-2',
      title: 'Tablet',
      subtitle: 'Just the right amount of everything.',
      image: tabletImage,
    },
    {
      id: 'product-3',
      title: 'Buy a Tablet or xPhone for college. \n Get arPods.',
      subtitle: '',
      image: airpodsImage,
    },
  ];

  return (
    <Carousel>
      {products.map((product) => (
        <Product key={product.id} {...product} />
      ))}
    </Carousel>
  );
};
