//
//  HSImageView.swift
//  HSAppStore
//
//  Created by Sheng ma on 2022/5/15.
//

import SwiftUI

struct HSImageView: View {
    @State private var imageData: Data? = nil
    var imageURL: String

    init(_ imageURL: String) {
        self.imageURL = imageURL
    }

    var body: some View {
        if let imageData = imageData {
            Image(uiImage: UIImage(data: imageData) ?? UIImage(imageLiteralResourceName: "defaultImage.jpg"))
                .resizable()
        } else {
            ProgressView()
                .onAppear {
                    getImageData()
                }
                .padding(.all)
        }
    }
    
    private func getImageData() {
        Task {
            HSImageCache.shared.imageForUrl(urlString: self.imageURL, completionHandler: { (data) -> () in
                imageData = data
            })
        }
    }
}

struct HSImageView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            HSImageView("invaild url")
            HSImageView("https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/60x60bb.jpg")
            HSImageView("https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/100x100bb.jpg")
        }
        .previewLayout(PreviewLayout.fixed(width: 75, height: 75))
    }
}
