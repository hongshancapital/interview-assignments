export const getIndexes = (
  slide: number,
  endSlide: number,
  count: number
): number[] => {
  let slideIndex = slide;
  let endSlideIndex = endSlide;

  if (slideIndex < 0) {
    slideIndex += count;
  } else if (slideIndex > count - 1) {
    slideIndex -= count;
  }

  if (endSlideIndex < 0) {
    endSlideIndex += count;
  } else if (endSlideIndex > count - 1) {
    endSlideIndex -= count;
  }

  return [slideIndex, endSlideIndex];
};


export const getNextMoveIndex = (
  currentSlide: number,
  count: number,
  slidesToScroll: number,
  slidesToShow: number
) => {
  return currentSlide + slidesToScroll;
};

export const getPrevMoveIndex = (
  currentSlide: number,
  slidesToScroll: number
): number => {
  return currentSlide - slidesToScroll;
};
