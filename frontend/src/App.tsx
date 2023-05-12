import './App.css';
import Carousel from './components/Carousel';
import styles from './App.module.scss';

function App() {
  return (
    <div className="App" style={{ width: 600 }}>
      <Carousel interval={3000}>
        <div
          className={styles.page}
          style={{ backgroundColor: 'black', color: 'white' }}
        >
          <h1>xPhone</h1>
          <div>Lots to love. Less to spend.</div>
          <div>Starting at $399.</div>
        </div>

        <div className={styles.page} style={{ backgroundColor: 'white' }}>
          <h1>Tablet</h1>
          <div>Just the right amout of everything.</div>
          <div>Starting at $399.</div>
        </div>

        <div
          className={styles.page}
          style={{ backgroundColor: 'rgb(239,239,241)' }}
        >
          <h1>Buy a Tablet or xPhone for college</h1>
          <div>Get arPods</div>
          <div>Starting at $399.</div>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
