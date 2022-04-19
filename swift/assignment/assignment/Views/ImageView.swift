//
//  ImageView.swift
//  assignment
//
//  Created by 干饭人肝不完DDL on 2022/4/19.
//

import SwiftUI

struct ImageView: View {
    @StateObject var imageVM: ImageViewModel
    init(app: AppModel){
        _imageVM = StateObject(wrappedValue: ImageViewModel(imageURL: app.artworkUrl60 ?? ""))
    }
    var body: some View {
        Group{
            if let image = imageVM.image {
                    Image(uiImage: image)
                        .resizable()
                        .scaledToFit()
            }else if imageVM.isLoading {
                ProgressView()
            }else {
                Image(systemName: "questionmark")
            }
        }
    }
}

struct ImageView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
