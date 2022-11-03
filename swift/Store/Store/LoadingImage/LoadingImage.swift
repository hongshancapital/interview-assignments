//
//  URLImage.swift
//  Store
//
//  Created by 张欣宇 on 2022/11/2.
//

import SwiftUI
import Combine

struct LoadingImage: View {
    var url: String
    
    var body: some View {
        AsyncImage(url: URL(string: url)) { image in
            image.resizable()
                .scaledToFit()
        } placeholder: {
            ProgressView()
        }
    }
}
