//
//  OTWebImage.swift
//  OTAppStore
//
//  Created by liuxj on 2022/3/18.
//

import SwiftUI

struct OTWebImage: View {
    @State private var imageData: Data? = nil
    var imageURL: String

    init(_ imageURL: String) {
        self.imageURL = imageURL
    }

    func fetchImageData() {
        Task {
            do {
                imageData = try await OTWebCacheLoader.shared.loadData(from: imageURL)
            } catch {
                print("fetchImageData with error: \(error.localizedDescription)")
            }
        }
    }

    var body: some View {
        if let imageData = imageData {
            Image(uiImage: UIImage(data: imageData) ?? UIImage())
                .resizable()
        } else {
            ProgressView()
                .onAppear {
                fetchImageData()
            }.padding(.all)
        }
    }
}

struct OTWebImage_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            OTWebImage("invaild url")
            OTWebImage("https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/60x60bb.jpg")
            OTWebImage("https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/100x100bb.jpg")
        }
            .previewLayout(PreviewLayout.fixed(width: 75, height: 75))
    }
}
