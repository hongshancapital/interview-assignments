import styled from 'styled-components';

interface ProductWrapperProps {
  color: string;
  backgroundColor: string;
  backgroundImage?: string;
}

export const ProductWrapper = styled.div<ProductWrapperProps>`
  height: 100%;
  width: 100%;
  color: ${(props) => props.color};
  background-color: ${(props) => props.backgroundColor};
  ${(props) =>
    props.backgroundImage && `background-image: url(${props.backgroundImage});`}
  background-size: cover;
  background-position-x: center;
  background-position-y: bottom;
`;

export const TextBox = styled.div`
  padding-top: 10%;
`;

export const Title = styled.h1`
  margin: 0;
  font-size: 48px;
  font-weight: bold;
  white-space: pre-line;
`;

export const Subtitle = styled.p`
  font-size: 24px;
  white-space: pre-line;
`;
