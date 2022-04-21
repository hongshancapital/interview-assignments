import React from 'react';

import { ProductWrapper, TextBox, Title, Subtitle } from './styles';

type Theme = 'dark' | 'light';

const DARK_COLOR = '#000000';
const LIGHT_COLOR = '#ffffff';

const THEMES = {
  dark: {
    color: LIGHT_COLOR,
    backgroundColor: DARK_COLOR,
  },
  light: {
    color: DARK_COLOR,
    backgroundColor: LIGHT_COLOR,
  },
};

interface ProductProps {
  title: string;
  subtitle: string;
  image: string;
  theme?: Theme;
}

export const Product = (props: ProductProps): JSX.Element => {
  const { theme = 'light', title, subtitle, image } = props;

  const currentTheme = THEMES[theme];

  return (
    <ProductWrapper
      color={currentTheme.color}
      backgroundColor={currentTheme.backgroundColor}
      backgroundImage={image}
    >
      <TextBox>
        <Title>{title}</Title>
        <Subtitle>{subtitle}</Subtitle>
      </TextBox>
    </ProductWrapper>
  );
};
