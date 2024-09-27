//
//  NetImageView.swift
//  RefreshTableDemo
//
//  Created by yaojinhai on 2022/12/14.
//

import Foundation
import SwiftUI
import Kingfisher

struct NetImageView: View {
    let url: String
    let placeImage: UIImage?
    @State private var image: UIImage? = nil
    @State private var isLoading = true
    var body: some View {
        Image(uiImage: image ?? placeImage ?? UIImage())
            .background(ActivityIndicator(isLoading: $isLoading, style: .medium))
            .task(id: url, loadImageFromNet)
    }
    
    @Sendable
    private func loadImageFromNet() async  {
       guard let tempURL = URL(string: url) else{
           isLoading = false
           return
       }
        isLoading = true
        ImageDownloader.default.downloadImage(with: tempURL) {
            resultImage in
            let result = try? resultImage.get()
            DispatchQueue.main.async {
                self.image = result?.image
                isLoading = false
            }
            
        }
    }
}
