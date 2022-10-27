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
            let img = await ImageLoader.shared.loadImage(url: url)
            await MainActor.run {
                self.image = img
            }
        }
    }
}

struct WebImage_Previews: PreviewProvider {
    static var previews: some View {
        WebImage(url: DataManager.shared.appModels.first!.artworkUrl60)
    }
}
