import './common.css';
import img from '../assets/airpods.png';

export default function Page3() {
  return (
    <div className="page box bg-img reset" style={{ backgroundImage: `url(${img})`, backgroundColor: '#EFEFF1' }}>
      <h1 style={{marginTop: 80, marginBottom: 16}}>Buy a Tablet or xPhone for college.</h1>
      <h1>Get arPods.</h1>
    </div>
  );
}