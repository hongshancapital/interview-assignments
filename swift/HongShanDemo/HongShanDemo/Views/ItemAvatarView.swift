//
//  ItemAvatarView.swift
//  HongShanDemo
//
//  Created by Peng Shu on 2023/2/24.
//

import SwiftUI


/// NOTE: 没有采用系统自带的AsyncImage, 因为在 iOS16 以下会有bug. 图片有时不能正常加载. SwiftUI的bug.
/// 这里手动实现一个重用cell的异步加载. 充分利用Swift并发中的Task, 已自动支持cancel
struct ItemAvatarView: View {
    var url: URL
    @State var image: UIImage?
    
    var body: some View {
        Group {
            if let image = image {
                Image(uiImage: image)
                    .resizable()
                    .aspectRatio(1, contentMode: .fill)
                    .frame(width: 60, height: 60)
                    .cornerRadius(8)
            } else {
                ProgressView()
                    .frame(width: 60, height: 60)
            }
        }
        .task {
            if let (data, _) = try? await URLSession.shared.data(from: url) {
                do {
                    // 设置一个检查点, 避免cell被重用时造成的数据紊乱. 很重要!
                    try Task.checkCancellation()
                    
                    await MainActor.run(body: {
                        image = UIImage(data: data)
                    })
                } catch {
                    
                }
            }
        }

    }
}
