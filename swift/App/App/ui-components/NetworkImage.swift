//
//  NetworkImage.swift
//  App
//
//  Created by august on 2022/3/22.
//

import SwiftUI

struct NetworkImage: View {
    
    var imageUrl: URL
    @State @MainActor var image = UIImage()
    @State @MainActor var finished = false
    
    var body: some View {
        Group {
            if !finished {
                ProgressView()
                    .progressViewStyle(.circular)
            } else {
                Image(uiImage: image)
                    .resizable()
                    .cornerRadius(10)
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Color.gray, lineWidth: 0.5)
                    }
            }
        }
        .task(priority: .background) {
            try? await loadImage()
        }
    }
    
    @MainActor func loadImage() async throws {
        let result = try await URLSession.shared.data(from: imageUrl)
        self.image = UIImage(data: result.0) ?? UIImage()
        self.finished = true
    }
}

struct NetworkImage_Previews: PreviewProvider {
    static var previews: some View {
        NetworkImage(imageUrl: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/86/a6/bf/86a6bf39-bf4b-25d1-451b-28eef9c15717/source/100x100bb.jpg")!)
    }
}
