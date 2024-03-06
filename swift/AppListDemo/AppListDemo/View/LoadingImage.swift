//
//  LoadingImage.swift
//  AppListDemo
//
//  Created by arthur on 2022/10/22.
//

import SwiftUI

struct LoadingImage: View {
    
    var url: URL?
    
    var body: some View {
        AsyncImage(url: url) { image in
            image.resizable()
                .scaledToFill()
                .cornerRadius(7)
        } placeholder: {
            ProgressView()
                .progressViewStyle(.circular)
        }
        .overlay {
            RoundedRectangle(cornerRadius: 7)
                .stroke(Color(uiColor: .lightGray) ,lineWidth: 0.5)
        }
    }
}

struct LoadingImage_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            LoadingImage(url: URL(string: AppInfo.preview.imageUrl))
                .frame(width: 60, height: 60)
        }
        .padding(.all, 40)
        .background(Color(uiColor: .systemGroupedBackground))
    }
}
