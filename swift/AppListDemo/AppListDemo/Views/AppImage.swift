//
//  AppImage.swift
//  AppListDemo
//
//  Created by Mars on 2023/2/2.
//

import SwiftUI

struct AppImage: View {
    
    var url:String
    
    @State private var remoteImage:UIImage? = nil
    
    let placehoderLoadingImg = UIImage(named: "apple")
    
    var body: some View {
        Image(uiImage: self.remoteImage ?? placehoderLoadingImg! )
            .resizable()
            .onAppear(perform: fetchRemote)
            .frame(width: 60, height: 60)
            .scaledToFit()
            .cornerRadius(10)
    }
    
    
    
    
    func fetchRemote() {
        
        guard let url = URL(string: url) else {return}
        URLSession.shared.dataTask(with: url ){
            (data,response,error) in
            if let image = UIImage(data: data!) {
                self.remoteImage = image
            } else {
                print("图片下载失败")
            }
        }.resume()
        
    }
    
}





struct AppImage_Previews: PreviewProvider {
    static var previews: some View {
        let url =  "https://is1-ssl.mzstatic.com/image/thumb/Purple123/v4/19/91/17/199117a1-e4ab-8990-4934-74cb99662f26/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/512x512bb.jpg"
        AppImage(url: url)
    }
}
