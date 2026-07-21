//
//  NetworkImage.swift
//  SwiftUIDemo
//
//  Created by chenghao on 2022/5/17.
//

import SwiftUI
import UIKit

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
                            .stroke(Color(UIColor.systemGray3),lineWidth: 0.5)
                    )
            } else {
                ProgressView()
                    .onAppear(perform: fetchNetworkImage)
            }
        }
    }
    
    func fetchNetworkImage() {
        guard image == nil else {
            return
        }
        if let url = URL(string: imageURLString) {
            URLSession.shared.dataTask(with: url) { [self] (data, response, error) in
                guard let data = data else {
                    debugPrint("获取图片失败：\(error?.localizedDescription ?? "未知失败原因") respnse:\(response?.debugDescription ?? "无效响应")")
                    return
                }
                image = UIImage(data: data)
            }.resume()
        }
    }
}

//struct NetworkImage_Previews: PreviewProvider {
//    static var previews: some View {
//        NetworkImage()
//    }
//}
