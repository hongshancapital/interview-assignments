
function Progress(active: number, index: number) {
  return (
    <div className='progressWrap' key={index}>
      <div className={active === index ? 'progress' : undefined}></div>
    </div>
  );
}

export default Progress;
