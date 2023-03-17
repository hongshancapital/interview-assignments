//
//  DemoImageView.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/16.
//

import SwiftUI
import Combine

struct DemoImageView: View {
    @StateObject var viewModel: ViewModel
    
    var body: some View {
        switch viewModel.image {
        case .notRequested:
            notRequestedView
        case .isLoading:
            loadingView
        case let .loaded(image):
            loadedView(image)
        case .failed:
            placeholder()
        }
    }
    
    /// 加载中
    var notRequestedView: some View {
        ProgressView().task {
            await viewModel.load()
        }
    }
    
    /// 加载中
    var loadingView: some View {
        ProgressView()
    }
    
    /// 失败的占位图片
    func placeholder() -> some View {
        Image(systemName: "suit.heart.fill")
    }
    
    /// 加载成功图片
    func loadedView(_ uiImage: UIImage) -> some View {
        Image(uiImage: uiImage)
            .cornerRadius(10)
    }
}

extension DemoImageView {
    class ViewModel: ObservableObject {
        /// 图片url
        var imageURL: String
        
        @Published var image: Loadable<UIImage>
        
        /// 图片下载服务
        let service: DemoImageService
        
        var anyCancellable: AnyCancellable?
        
        init(imageURL: String = "", image: Loadable<UIImage> = .notRequested, service: DemoImageService = DemoRealImageService()) {
            self.imageURL = imageURL
            self.image = image
            self.service = service
        }
        
        /// 加载图片
        @MainActor
        func load() async {
            
            /// 先从缓存取，当前从内存取
            if let image = DemoCache.shared.image(key: imageURL) {
                self.image = .loaded(image)
                return
            }
            
            guard let imageURL = URL(string: imageURL) else {
                self.image = .failed(APIError.invalidURL)
                return
            }
            
            do {
                let image = try await service.load(imageURL: imageURL)
                /// 动画太快消失，延迟0.5秒
                try await Task.sleep(nanoseconds: 500_000_000)
                /// 缓存到内存
                DemoCache.shared.set(key: self.imageURL, image: image)
                /// 刷新
                self.image = .loaded(image)
            } catch let error {
                self.image = .failed(error)
            }
        }
    }
}
