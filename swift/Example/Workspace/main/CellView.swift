//
//  CellView.swift
//  Example
//
//  Created by 聂高涛 on 2022/3/2.
//

import SwiftUI

//单元格
struct CellView : View {
    @State var data : CellData
    let placeholder = UIImage(named: "image")
    var body: some View {
        return HStack.init(alignment: .center, spacing: 10) {
            
            Image(uiImage: data.networkImage ?? self.placeholder!)
                .resizable()
                .frame(width: 40, height: 40)
                .cornerRadius(4)
                .onAppear(perform: fetchRemoteImage)
            
            VStack.init(alignment: .leading, spacing: 4) {
                Text(data.title)
                    .font(.system(size: 16))
                    .lineLimit(1)
                    .foregroundColor(Color.init(red: 30.0/255.0, green: 30.0/255.0, blue: 30.0/255.0))
                
                Text(data.subtitle)
                    .font(.system(size: 13))
                    .lineLimit(2)
                    .foregroundColor(Color.init(red: 90.0/255.0, green: 90.0/255.0, blue: 90.0/255.0))
            }
            
            Spacer()
            
            Button(action:{
                self.data.isSelected = !self.data.isSelected;
            }) {
                Image(data.isSelected ? "loveselected":"loveunselected")
                    .resizable()
                    .frame(width: 28, height: 28)
                    .cornerRadius(4)
            }
            
        }.padding(EdgeInsets(top: 12, leading: 12, bottom: 12, trailing: 12))
    }
    
    
    
    func fetchRemoteImage(){
        if data.networkImage != nil {
            return
        }
        guard let url = URL(string: data.url) else { return }
        URLSession.shared.dataTask(with: url){ (data, response, error) in
            if let image = UIImage(data: data!){
                self.data.networkImage = image
            }
            else{
                Logger.print(error ?? "")
            }
        }.resume()
    }
}
