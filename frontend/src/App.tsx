import './App.css';
import useCarousel from './customHook/useCarousel';

function App() {
  // 轮播数据
  const data = [
    {
      children: <div><p className='title'>xPhone</p><p className='text'>Lots to love. Less to spend.</p><p className='text'>Starting at $399.</p></div>,
      iconName: 'iphone',
    },
    {
      children: <div><p className='title'>Tablet</p><p className='text'>Just the right amount of everything.</p></div>,
      iconName: 'tablet',
    },
    {
      children: <div className='title'><p>Buy a Tablet or xPhone for college.</p><p>Get arPods.</p></div>,
      iconName: 'airpods',
    }
  ];
  return (
    <div className='App'>
      {
        useCarousel({data: data})
      }
    </div>
  );
}

export default App;
