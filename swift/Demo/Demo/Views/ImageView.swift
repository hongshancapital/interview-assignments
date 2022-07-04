//
//  ImageView.swift
//  Demo
//
//  Created by 李永杰 on 2022/7/4.
//

import SwiftUI

struct ImageView: View {
    
    @StateObject var loader: ImageLoader
    
    init(imageUrl: String?) {
        self._loader = StateObject(wrappedValue: ImageLoader(url: imageUrl))
    }
    
    var body: some View {
        Group {
            if loader.isLoading {
                ProgressView()
            } else if loader.image != nil {
                Image(uiImage: loader.image!)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
            } else {
                PlaceHolderView()
            }
        }
        .onAppear {
            loader.load()
        }
    }
}



struct PlaceHolderView: View {
    
    var body: some View {
        ZStack {
            Color.blue
            Text("placeholder")
        }
    }
}
