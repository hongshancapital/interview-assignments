
function useProgress(active: number, index: number) {
  return (
    <div className='progressWrap'>
      <div className={active === index ? 'progress' : undefined}></div>
    </div>
  );
}

export default useProgress;
