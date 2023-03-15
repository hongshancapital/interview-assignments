import './common.css';
import img from '../assets/iphone.png';

export default function Page1() {
  return (
    <div className="page box bg-img reset" style={{ backgroundImage: `url(${img})`, backgroundColor: '#111111' }}>
      <h1 style={{color: '#ccc', marginTop: 80, marginBottom: 24}}>xPhone</h1>
      <h4 style={{color: '#ccc'}}>Lots to love. Less to spend.</h4>
      <h4 style={{color: '#ccc'}}>Starting at $399.</h4>
    </div>
  );
}