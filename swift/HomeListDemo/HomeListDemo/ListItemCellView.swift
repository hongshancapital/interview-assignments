//
//  ListItemCellView.swift
//  HomeListDemo
//
//  Created by yaojinhai on 2022/9/22.
//

import SwiftUI
import Kingfisher

struct ListItemCellView: View {
    let itemJson: ListItemCellDataJson
    @Binding var isSelected: Bool
    @Environment(\.colorScheme) private var colorScheme
    @State private var isLoadingImage = true
    @State private var leftImage: UIImage? = nil

    var body: some View {
        Group{
            
            HStack(alignment: .center,spacing: 0) {
                HStack(alignment: .top,spacing: 0) { 
                    createLeftImage().frame(width: 50, height: 50, alignment: .center).background(Color.clear).cornerRadius(8)
                    rowItemCell
                }
                Spacer()
                createLoveImage()

                
            }.background(Color(uiColor: UIColor.clear))
        }.padding(12).background(Color(uiColor: .systemBackground)).cornerRadius(8)
        
    }
    
    func createLeftImage() -> some View {
        Group{
            if let leftImage = leftImage {
                Image(uiImage: leftImage)
            }else {
                ActivityIndicator(style: .medium)
            }
        }.onAppear { 
            ImageDownloader.default.downloadImage(with: itemJson.imageURL!) { resultImage in
                let result = try? resultImage.get()
                if let img = result?.image {
                    DispatchQueue.main.async {
                        self.leftImage = img
                    }
                }
                
            }
        }
    }

    
    var rowItemCell: some View {
        
        VStack(alignment: .leading, spacing: 0) { 
            Text(itemJson.trackCensoredName).font(Font.system(size: 14, weight: .bold, design: .rounded)).lineLimit(1).foregroundColor(Color(.label))
            .padding(.bottom, 4)
            Text(itemJson.description).lineLimit(2).font(.system(size: 13)).foregroundColor(Color.init(.secondaryLabel))
        }.padding(.leading, 10)
        
        
    }
    
    private func createLoveImage() -> some View {
        
        
        var image = UIImage(named: isSelected ? "selected_love_flag" : "love_flag")
        if !isSelected {
            image = image?.drawImageColor(scolor: colorScheme == .dark ? UIColor.white : UIColor.black)
        }
        
        return Image(uiImage: image!).onTapGesture {
                    isSelected.toggle()
                } 
    }
    
    func drawLoveImageFlag() -> some View {
        Group { 
            GeometryReader { geo in
                let path = Path { path in
                    let topPoint = CGPoint(x: geo.size.width/2, y: 8)
                    let bottomPoint = CGPoint(x: geo.size.width/2, y: geo.size.height-2)
                    path.move(to: topPoint)
                    path.addCurve(to: bottomPoint, control1: CGPoint(x: -4, y: -1), control2: CGPoint(x: 0, y: geo.size.height - 10))
                    
                    path.addCurve(to: topPoint, control1: CGPoint(x: geo.size.width, y: geo.size.height - 10), control2: CGPoint(x: geo.size.width + 4, y: -1))
                    
                    path.closeSubpath()
                    
                }
                if isSelected {
                    path.fill(Color.red)

                }else {
                    path.stroke(Color(uiColor: UIColor.secondaryLabel), lineWidth: 1.5)
                }
            }
        }.frame(width: 30, height: 30, alignment: .center).padding().background(Color.clear).onTapGesture {
            self.isSelected.toggle()
        }
    }
}

struct ListItemCellView_Previews: PreviewProvider {
    static var previews: some View {
        
        ListItemCellView(itemJson: .init(artworkUrl60: "", trackCensoredName: "", description: "", trackId: 0), isSelected: .init(get: { 
            true
        }, set: { newValue in
            
        }))
        
    }
}


extension UIImage {
    func drawImageColor(scolor: UIColor?) -> UIImage {
        guard let color = scolor else {
            return self
        }
        UIGraphicsBeginImageContextWithOptions(size, false, 0);
        color.setFill();
        let bounds = CGRect(x: 0, y: 0, width: size.width, height: size.height);
        UIRectFill(bounds);
        draw(in: bounds, blendMode: CGBlendMode.destinationIn, alpha: 1);
        let newImage = UIGraphicsGetImageFromCurrentImageContext();
        UIGraphicsEndImageContext();
        
        return newImage!;
    }
}
