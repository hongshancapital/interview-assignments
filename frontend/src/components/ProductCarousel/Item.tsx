export const Item: React.FC<{ src: string; children: React.ReactNode }> = ({
  src,
  children,
}) => {
  return (
    <div className="h-full w-full relative">
      <div className="absolute w-full flex justify-center flex-col mt-40">
        {children}
      </div>
      <img src={src} alt="" className="h-full w-full object-fit-cover" />
    </div>
  );
};
