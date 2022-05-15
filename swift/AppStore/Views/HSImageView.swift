//
//  HSImageView.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import SwiftUI

class ImageViewModel: ObservableObject {
    var url: String
    var placeholder: AnyView?
    var image: UIImage?
    var configOprations = [(Image) -> Image]()

    enum State {
        case loading
        case completed
    }

    @Published var state: ImageViewModel.State = .loading

    init(url: String) {
        self.url = url
        Task {
            self.image = try await ImageManager.shared.fetchImage(url: url)
            DispatchQueue.main.async {
                self.state = .completed
            }
        }
    }
}

struct HSImageView: View {
    @ObservedObject var viewModel: ImageViewModel

    init(url: String) {
        viewModel = ImageViewModel(url: url)
    }

    func placeholder<T>(@ViewBuilder content: () -> T) -> HSImageView where T: View {
        viewModel.placeholder = AnyView(content())
        return self
    }

    func resizable(edgeInset: EdgeInsets = EdgeInsets(), resizingMode: Image.ResizingMode = .stretch) -> HSImageView {
        viewModel.configOprations.append {
            $0.resizable(capInsets: edgeInset, resizingMode: resizingMode)
        }
        return self
    }

    func configuredImage(image: Image) -> some View {
        viewModel.configOprations.reduce(image) { previous, configureBlock in
            configureBlock(previous)
        }
    }

    var body: some View {
        switch viewModel.state {
        case .loading:
            if let placeholder = viewModel.placeholder {
                placeholder
            } else {
                HSClearBackground()
            }
        case .completed:
            if let image = viewModel.image {
                configuredImage(image: Image(uiImage: image))
            } else {
                HSClearBackground()
            }
        }
    }
}

struct HSImageView_Previews: PreviewProvider {
    static var previews: some View {
        HSImageView(url: "")
    }
}
