//
//  LCImageView.swift
//  LCSwitfDemo
//
//  Created by 梁杰 on 2022/3/23.
//

import SwiftUI
//加载图片 默认显示菊花
struct LCImageView : View {
    var imageUrlStr : String
    @State private var remoteImage : UIImage? = nil
    @State private var hasFinish : Bool? = false
    var body: some View {
        if self.hasFinish!
        {
            Image(uiImage: self.remoteImage!).resizable()
                .aspectRatio(contentMode: .fit)
        }else{
            ProgressView().progressViewStyle(CircularProgressViewStyle(tint: Color.gray)).onAppear(perform: fetchRemoteImage)
        }
    }
    func fetchRemoteImage()
    {
        guard let url = URL(string: imageUrlStr) else { return }
        URLSession.shared.dataTask(with: url){ (data, response, error) in
            if let image = UIImage(data: data!){
                self.remoteImage = image
                self.hasFinish = true
            }
            else{
                print(error ?? "")
            }
        }.resume()
    }
}
