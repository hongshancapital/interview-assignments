//
//  AppImageView.swift
//  Demo
//
//  Created by 李永杰 on 2022/7/4.
//

import SwiftUI

struct AppImageView : View {
    
    var imageUrl : String
    
    @State private var remoteImage = UIImage()
    @State private var isComplete = false
    
    var body: some View {
        if self.isComplete {
            Image(uiImage: self.remoteImage)
                .resizable()
                .aspectRatio(contentMode: .fit)
        } else {
            ProgressView()
                .onAppear(perform: requstRemoteImage)
        }
    }
    
    func requstRemoteImage() {
        guard let url = URL(string: imageUrl) else { return }
        URLSession.shared.dataTask(with: url){ (data, response, error) in
            if let image = UIImage(data: data!){
                self.remoteImage = image
                self.isComplete = true
            }
            else{
                print(error ?? "")
            }
        }.resume()
    }
}
