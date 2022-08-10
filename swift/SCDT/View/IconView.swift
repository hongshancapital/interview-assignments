//
//  IconView.swift
//  SCDT
//
//  Created by Zhao Sam on 2022/8/5.
//

import SwiftUI

struct IconView: View {
    var url: String = ""
    var body: some View {
        AsyncImage(url: URL(string: url), content: { image in
            image.resizable()
        }, placeholder: {
            ProgressView()
        })
        .frame(width: 60, height: 60)
        .cornerRadius(10)
    }
}

struct IconView_Previews: PreviewProvider {
    static var previews: some View {
        IconView(url: "https://usercontent.one/wp/www.swiftlyrush.com/wp-content/uploads/2021/11/image-2.png?media=1637150467")
    }
}
