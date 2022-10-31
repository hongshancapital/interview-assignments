//
//  WebImage.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI

struct WebImage: View {
    var url: String
    @State private var image: UIImage?
    
    var body: some View {
        VStack {
            if let image = image {
                Image(uiImage: image)
            } else {
                ProgressView()
            }
        }
        .onAppear(perform: loadImage)
    }
    
    func loadImage() {
        Task {
            do {
                let img = try await ImageLoader.shared.loadImage(url: url)
                await MainActor.run {
                    self.image = img
                }
            } catch {
                await MainActor.run {
                    self.image = UIImage() // placeholder
                }
            }
        }
    }
}

struct WebImage_Previews: PreviewProvider {
    static var previews: some View {
        WebImage(url: NetWorkManager.shared.mockBackendApps.first!.artworkUrl60)
    }
}
