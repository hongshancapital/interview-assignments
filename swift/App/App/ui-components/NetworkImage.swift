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
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(Color.gray, lineWidth: 0.5)
                    }
            }
        }
        .task(priority: .background) {
            var data = Data()
            do {
                (data, _) = try await URLSession.shared.data(from: imageUrl)
            } catch {}
            
            self.image = UIImage(data: data) ?? UIImage()
            self.finished = true
        }
    }
}

struct NetworkImage_Previews: PreviewProvider {
    static var previews: some View {
        NetworkImage(imageUrl: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/86/a6/bf/86a6bf39-bf4b-25d1-451b-28eef9c15717/source/100x100bb.jpg")!)
    }
}
