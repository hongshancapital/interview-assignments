import './Indicator.css';

export interface IndicatorsProps {
  currentIndex: number;
  total: number;
  duration: number;
}

const Indicator = ({ currentIndex, total, duration }: IndicatorsProps) => {
  return (
    <ul className={'indicator'}>
      {new Array(Math.max(0, total)).fill(null).map((_, index) => {
        return (
          <li
            key={index}
            className={[
              'indicator__item',
              currentIndex === index ? 'indicator__item_active' : '',
            ]
              .filter(Boolean)
              .join(' ')}
          >
            <span
              className={'indicator__progress'}
              style={
                currentIndex === index
                  ? { animationDuration: `${duration}ms` }
                  : {}
              }
            />
          </li>
        );
      })}
    </ul>
  );
};
Indicator.displayName = 'Indicator';

export default Indicator;
