import './style/item.css';

export const CarouselItem: React.FC<{
  children: React.ReactNode;
  id: string;
}> = ({ children, id }) => {
  return (
    <div className="carouse-item" id={id}>
      {children}
    </div>
  );
};
