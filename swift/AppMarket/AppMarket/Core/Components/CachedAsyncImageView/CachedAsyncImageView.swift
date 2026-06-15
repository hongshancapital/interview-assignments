//
//  CachedAsyncImageView.swift
//  AppMarket
//
//  Created by xcz on 2022/9/7.
//

import SwiftUI

struct CachedAsyncImageView: View {
    
    @StateObject var vm: CachedAsyncImageViewModel
    
    
    init(urlString: String) {
        _vm = StateObject(wrappedValue: CachedAsyncImageViewModel(url: URL(string: urlString)!))
    }
    
    
    var body: some View {
        ZStack {
            if let image = vm.image {
                Image(uiImage: image)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .cornerRadius(10)
                    .background(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Color.gray, lineWidth: 1.0)
                            .opacity(0.5)
                    )
            } else if vm.isLoading {
                ProgressView()
            } else {
                Image(systemName: "questionmark")
                    .foregroundColor(.secondary)
                    .onTapGesture {
                        Task{
                            await vm.fetchImage()
                        }
                    }
            }
        }
    }
}

struct CachedAsyncImageView_Previews: PreviewProvider {
    static var previews: some View {
        CachedAsyncImageView(urlString: "https://is2-ssl.mzstatic.com/image/thumb/Purple112/v4/06/b2/84/06b284d5-e33f-7a28-c773-1ef86a5c5418/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/100x100bb.jpg")
    }
}
