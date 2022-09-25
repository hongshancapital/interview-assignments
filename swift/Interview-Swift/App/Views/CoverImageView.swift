//
//  CoverImageView.swift
//  App
//
//  Created by lizhao on 2022/9/19.
//

import SwiftUI
import GUIKit


struct CoverImageView: View {
    let image: WebImageType?
    init(url: URL) {
        self.image = URLWebImage(url)
    }
    var body: some View {
        WebImageView(image: image) { uiImage, state in
            ZStack {
                switch state {
                case .displaying:
                    if state == .displaying {
                        if let image = uiImage {
                            Image(uiImage: image)
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                        } else {
                            Text("No Image")
                        }
                    }
                case .loading:
                    if let image = uiImage {
                        Image(uiImage: image)
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .colorMultiply(.init(red: 0.5, green: 0.5, blue: 0.5))
                    }
                    ProgressView()
                        .progressViewStyle(.circular)
                case .error:
                    Text("There was an error!")
                }
            }
        }
        .frame(width: 64, height: 64)
        .clipShape(RoundedRectangle(cornerRadius: UI.Corner.default))
        .background(
            ZStack {
                RoundedRectangle(cornerRadius: UI.Corner.default)
                    .stroke(Color(UI.Color.separator), style: StrokeStyle(lineWidth: UI.Border.bold))
            }
        )
    }
}

struct CoverImageView_Previews: PreviewProvider {
    static let url = URL(string: "https://img.51miz.com/Element/00/90/53/37/8334ff2e_E905337_d68765b3.jpg")!
    static var previews: some View {
        CoverImageView(url: url)
    }
}
