import { useState } from 'react';
import { CarouselRAF } from './components/CarouselRAF';
import { CarouselCSS } from './components/CarouselCSS';

type CarouselDataProps = typeof CarouselRAF extends React.FC<infer P>
  ? P
  : never;

const text2Paragraph = (h: React.ReactNode) =>
  Array.isArray(h) ? h.map((h, idx) => <div key={idx}>{h}</div>) : h;
const initData: CarouselDataProps['data'] = [
  {
    header: 'xPhone',
    subHeader: ['Lots to Love. Less to spend.', 'Starting at $399.'],
    className: 'bg-[#111111] text-[#FAFAFA] carousel-bg bg-iphone',
  },
  {
    header: 'Tablet',
    subHeader: 'Just the right amount of everything',
    className: 'bg-[#FAFAFA] carousel-bg bg-tablet',
  },
  {
    header: ['Buy a Tablet or xPhone for collage.', 'Get arPods.'],
    className: 'bg-[#F1F1F3] carousel-bg bg-airpods',
  },
].map((i) => ({
  ...i,
  header: text2Paragraph(i.header),
  subHeader: text2Paragraph(i.subHeader),
}));

function App() {
  const [data] = useState(initData);

  return (
    <>
      <div className="w-[1200px] h-[600px] m-auto mb-6">
        <CarouselRAF data={data} />
      </div>
      <div className="w-[1200px] h-[600px] m-auto">
        <CarouselCSS data={data} />
      </div>
    </>
  );
}

export default App;
