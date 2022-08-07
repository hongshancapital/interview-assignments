import React,{ FC, useState, useEffect } from 'react';
import './carousel-item.css';

interface CarouselItemProps {
  className?: string,
}

const CarouselItem: FC<CarouselItemProps> = (props) => {
  const { children, className } = props;
  return <div className={`carousel-item ${className}`}>{children}</div>;
}

export default CarouselItem;