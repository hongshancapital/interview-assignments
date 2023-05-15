import clsx from 'clsx';
import style from './App.module.css';
import {
  Carousel,
  CarouselIndicatorList,
  CarouselSlide,
} from './components/Carousel';
import { slides } from './fixtures';

function App() {
  return (
    <Carousel
      interval={2000}
      className='relative'
      aria-label='yangbot-homework'
    >
      {(ctx) => {
        return (
          <>
            <div
              className='flex flex-row transition-transform duration-500'
              style={{
                transform: `translateX(-${ctx.currentIndex * 100}%)`,
              }}
            >
              {slides.map((slide, i) => {
                return (
                  <CarouselSlide
                    key={i}
                    className={clsx(style.item, slide.theme)}
                    activeIndex={i}
                  >
                    <h1 className='text-title leading-tight font-semibold'>
                      {slide.title}
                    </h1>
                    <p className='text-content leading-tight'>
                      {slide.content}
                    </p>
                    <img
                      className={style.icon}
                      src={slide.icon.src}
                      alt={slide.icon.alt}
                    />
                  </CarouselSlide>
                );
              })}
            </div>
            <CarouselIndicatorList
              className={style.indicators}
              style={
                {
                  '--carousel-animation-duration': `${ctx.interval}ms`,
                } as React.CSSProperties
              }
            >
              {(indicators) => {
                return indicators.map(({ props }, i) => {
                  return (
                    <button
                      key={i}
                      className={clsx(
                        'w-10 h-[2px] rounded-full bg-silver',
                        style['progress-bar'],
                      )}
                      {...props}
                    />
                  );
                });
              }}
            </CarouselIndicatorList>
          </>
        );
      }}
    </Carousel>
  );
}

export default App;
