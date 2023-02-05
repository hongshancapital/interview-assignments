import './index.css';

export interface SellPosterProps {
  title: string | string[];
  description?: string | string[];
  imgSrc: string;
  width?: string | number;
  height?: string | number;
  mode?: 'dark' | 'light';
  bgColor?: string;
}

const convertContent = (content: string | string[] | undefined) => {
  if (typeof content === 'string') {
    return [content];
  } else if (content === undefined) {
    return [];
  } else {
    return content;
  }
};

const SellPoster = ({
  title,
  description,
  imgSrc,
  width = '100%',
  height = '100%',
  mode = 'light',
  bgColor = '',
}: SellPosterProps) => {
  const titleArr = convertContent(title);
  const desArr = convertContent(description);

  return <div className={`sell-poster ${mode}`} style={{
    background: `${ bgColor } url('${imgSrc}') center center / auto 100% no-repeat`,
    width: typeof width === 'number' ? `${width}px` : width,
    height: typeof height === 'number' ? `${height}px` : height,
  }}>
    <div className='sell-poster-content'>
      <ul className="sell-poster-title">
        { titleArr.map((item, index) => <li key={index}>{item}</li>) }
      </ul>
      {!!desArr && <ul className="sell-poster-des">
        { desArr.map((item, index) => <li key={index}>{item}</li>) }
      </ul>}
    </div>
  </div>
};

export default SellPoster;