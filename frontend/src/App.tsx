import clsx from 'clsx';
import styles from './App.module.scss';
import { Carousel, Slide } from './components';

function App() {
  return <div className={styles.App}>
      <Carousel speed={50} delay={1000}>
        <Slide>
          <div className={clsx(styles.slideRoot, styles.iphone)}>
            <h2>xPhone</h2>
            <div>Lots to love. Less to spend.</div>
            <div>Starting at $399.</div>
          </div>
        </Slide>
        <Slide>
          <div className={clsx(styles.slideRoot, styles.tablet)}>
            <h2>Tablet</h2>
            <div>Just the right amount of everything.</div>
          </div>
        </Slide>
        <Slide>
          <div className={clsx(styles.slideRoot, styles.airpods)}>
            <h2>Buy a Tablet or xPhone for college.</h2>
            <h2>Get airPods.</h2>
          </div>
        </Slide>
      </Carousel>
    </div>;
}

export default App;
