//
//  NetImageView.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import SwiftUI

struct NetImageView: View {
    
    private var url: String
    
    @State private var logo: UIImage? = nil
    @State private var error: ImageLoaderError?
    @State private var loaded: Bool = false
    
    init(url: String) {
        self.url = url
    }
    
    var body: some View {
        if !loaded {
            ProgressView()
                .frame(idealWidth: 50, idealHeight: 50)
                .fixedSize(horizontal: true, vertical: true)
                .onAppear {
                    fetchImage()
                }
        } else {
            Image.init(uiImage: logo ?? (error == nil ? UIImage.init(named: "image-placeholder") ?? UIImage() : UIImage.init(named: "image-error") ?? UIImage()))
                .resizable()
                .frame(idealWidth: 50, idealHeight: 50)
                .fixedSize(horizontal: true, vertical: true)
                .cornerRadius(6)
        }
    }
    
    private func fetchImage () {
        Task {
            loaded = false
            let (image, err) = await ImageLoader.shared.loadImage(with: url)
            logo = image
            error = err
            loaded = true
        }
    }
}
