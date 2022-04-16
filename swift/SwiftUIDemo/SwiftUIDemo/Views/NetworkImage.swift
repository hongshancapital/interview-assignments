//
//  NetworkImage.swift
//  SwiftUIDemo
//
//  Created by Machao on 2022/4/15.
//

import SwiftUI

struct NetworkImage: View {
    @State var image: UIImage?
    var imageURLString: String
    
    
    
    var body: some View {
        ZStack {
            if let image = image {
                Image(uiImage: image)
                    .resizable()
                    .cornerRadius(5)
                    .overlay(
                        RoundedRectangle(cornerRadius: 5, style: .circular)
                            .stroke(Color(UIColor.systemGray3), lineWidth: 0.5))
            } else {
                ProgressView()
                    .onAppear(perform: fetchNetworkImage)
            }
        }
    }
    
    func fetchNetworkImage() {
        guard image == nil else{
            return
        }
        if let url = URL(string: imageURLString) {
            URLSession.shared.dataTask(with: url) {[self] (data, response, error) in
                guard let data = data else {
                    debugPrint("获取网图图片失败: \(error?.localizedDescription ?? "失败原因不明")  response: \(response?.debugDescription ?? "无效响应") ")
                    return
                }

                image = UIImage(data: data)
            }.resume()
        }
    }
}
