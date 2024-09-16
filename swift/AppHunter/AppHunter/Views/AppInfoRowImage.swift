//
//  AppInfoRowImage.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Kingfisher
import SwiftUI
struct AppInfoRowImage: View {
    let url: URL
    var body: some View {
        KFImage.url(url)
            .placeholder { ProgressView() }
            .fade(duration: 0.25)
            .onProgress { _, _ in }
            .onSuccess { _ in }
            .onFailure { _ in }.resizable()
            .scaledToFit()
            .cornerRadius(12, antialiased: true)
    }
}
