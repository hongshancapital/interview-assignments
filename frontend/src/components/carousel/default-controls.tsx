import { CSSProperties } from 'react';
import { ControlProps } from './types';

const defaultButtonStyles = (disabled: boolean): CSSProperties => ({
  border: 0,
  background: 'rgba(0,0,0,0.4)',
  color: 'white',
  padding: 10,
  textTransform: 'uppercase',
  opacity: disabled ? 0.3 : 1,
  cursor: disabled ? 'not-allowed' : 'pointer',
});

export const prevButtonDisabled = ({
  currentSlide,
}: Pick<ControlProps, 'currentSlide'>) => {
  if (currentSlide !== 0) {
    return false;
  }
  return true;
};

export const nextButtonDisabled = ({
  currentSlide,
  slideCount,
  slidesToScroll,
}: Pick<ControlProps, 'currentSlide' | 'slideCount' | 'slidesToScroll'>) => {
  if (currentSlide < slideCount - slidesToScroll) {
    return false;
  }
  return true;
};

export const PreviousButton = (props: ControlProps) => {
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();
    props?.previousSlide();
  };

  const {
    prevButtonClassName,
    prevButtonStyle = {},
    prevButtonText,
  } = props.defaultControlsConfig || {};

  const disabled = prevButtonDisabled(props);

  return (
    <button
      className={prevButtonClassName}
      style={{
        ...defaultButtonStyles(disabled),
        ...prevButtonStyle,
      }}
      disabled={disabled}
      onClick={handleClick}
      aria-label="previous"
      type="button"
    >
      {prevButtonText || 'Prev'}
    </button>
  );
};

export const NextButton = (props: ControlProps) => {
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    event.preventDefault();
    props.nextSlide();
  };

  const { defaultControlsConfig } = props;

  const {
    nextButtonClassName,
    nextButtonStyle = {},
    nextButtonText,
  } = defaultControlsConfig;

  const disabled = nextButtonDisabled(props);

  return (
    <button
      className={nextButtonClassName}
      style={{
        ...defaultButtonStyles(disabled),
        ...nextButtonStyle,
      }}
      disabled={disabled}
      onClick={handleClick}
      aria-label="next"
      type="button"
    >
      {nextButtonText || 'Next'}
    </button>
  );
};
