import './common.css';
import img from '../assets/tablet.png';

export default function Page2() {
  return (
    <div className="page box bg-img reset" style={{ backgroundImage: `url(${img})`, backgroundColor: '#F9F9F9' }}>
      <h1 style={{marginTop: 80, marginBottom: 24}}>Tablet</h1>
      <h4>Just the right amount of everything.</h4>
    </div>
  );
}