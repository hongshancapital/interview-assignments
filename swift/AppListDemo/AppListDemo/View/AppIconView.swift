//
//  AppIconView.swift
//  AppListDemo
//
//  Created by HQ on 2022/8/19.
//

import SwiftUI

struct AppIconView: View {
    
    /// APP图标链接
    @Binding var url: String
    
    var body: some View {
        CacheAsyncImage(url: URL (string: url), transaction: Transaction (animation: .spring())) { phase in
            switch phase {
            case .success (let image):
                image
                    .resizable()
                    .scaledToFit()
                    .cornerRadius(10)
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.gray, lineWidth: 0.5)
                    }
            case .empty:
                ProgressView()
            case .failure(_):
                failureImage()
            @unknown default:
                failureImage()
            }
        }
    }
    
    private func failureImage() -> AnyView {
        return AnyView(Image(systemName: "exclamationmark.circle")
            .resizable()
            .aspectRatio(contentMode: .fit)
            .overlay {
                RoundedRectangle(cornerRadius: 10)
                    .stroke(.gray, lineWidth: 0.5)
            }
        )
    }
}

struct AppIconView_Previews: PreviewProvider {
    @State static var url = "https://c-ssl.duitang.com/uploads/item/202007/15/20200715230728_LQEQc.jpeg"
    static var previews: some View {
        AppIconView(url: $url)
            .frame(width: 60, height: 60, alignment: .center)
    }
}
