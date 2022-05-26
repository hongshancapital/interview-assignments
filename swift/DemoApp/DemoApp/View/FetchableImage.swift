//
//  FetchableImage.swift
//  DemoApp
//
//  Created by liang on 2022/5/19.
//

import SwiftUI
import Combine

struct FetchableImage: View {
    @State var image: UIImage?
    var imageUrl: String
    var concellable: AnyCancellable?
    var body: some View {
        if let image = image {
            Image(uiImage: image)
        } else {
            ProgressView()
                .onAppear(perform: fetchImage)
        }
    }
    
    
    
    func fetchImage() {
        if let url = URL(string: imageUrl) {

            Task {
            do {
                let (data, rsp) = try await URLSession.shared.data(for: (URLRequest(url: url)))
                guard (rsp as? HTTPURLResponse)?.statusCode == 200 else {
                    fatalError("image req failed")
                }
                image = UIImage(data: data)
            } catch {}
            }

//            URLSession.shared.dataTask(with: url) {[self] (data, rsp, error) in
//                guard let data = data else {
//                    return
//                }
//                image = UIImage(data: data)
//            }.resume()
        }
    }
}

struct FetchableImage_Previews: PreviewProvider {
    static var previews: some View {
        FetchableImage(image: nil, imageUrl: "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/e7/a6/28/e7a62890-5938-b2c0-bf38-60d787419d5c/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/60x60bb.jpg")
    }
}
