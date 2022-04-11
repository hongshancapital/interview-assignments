//
//  WebImage.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import SwiftUI

struct WebImage: View {
    
    var imageUrl: URL
    
    @State @MainActor var currentImage = UIImage()
    @State @MainActor var finished = false
    
    var body: some View {
        Group {
            if finished {
                Image(uiImage: currentImage)
                    .resizable()
                    .cornerRadius(12)
                    .overlay {
                        RoundedRectangle(cornerRadius: 12)
                            .stroke(.gray, lineWidth: 0.2)
                    }
            } else {
                ProgressView()
                    .progressViewStyle(.circular)
            }
        }
        .task(priority: .background) {
            try? await loadImage()
        }
    }
    
    @MainActor func loadImage() async throws {
        let result = try await URLSession.shared.data(from: imageUrl)
        self.currentImage = UIImage(data: result.0) ?? UIImage()
        self.finished = true
    }
}

struct WebImage_Previews: PreviewProvider {
    static var previews: some View {
        WebImage(imageUrl: URL(string: "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/c8/54/70/c85470ed-3e6c-f7c4-792e-ef30f4fb5c8d/source/60x60bb.jpg")!)
            .frame(width: 60, height: 60)
    }
}
