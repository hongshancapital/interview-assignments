//
// Created by Jeffrey Wei on 2022/6/27.
//

import SwiftUI

struct DemoCell: View {
    // 渲染对应的model
    private let model: DemoModel
    // 点击收藏事件
    private let onClickCollect: () -> Void

    init(model: DemoModel,
         onClickCollect: @escaping () -> Void) {
        self.model = model
        self.onClickCollect = onClickCollect
    }

    internal var body: some View {
        HStack {
            AsyncImage(
                    url: URL(string: model.icon),
                    content: {
                        $0.resizable()
                    },
                    placeholder: {
                        ProgressView()
                    })
                    .frame(width: 50, height: 50)
                    .cornerRadius(8)
            VStack(alignment: .leading, spacing: 5) {
                Text(model.artistName).font(.headline).lineLimit(2)
                Text(model.description).font(.caption).lineLimit(2)
            }

            Spacer()
            Image(systemName: model.isCollected ? "heart.fill" : "heart")
                    .resizable()
                    .animation(.easeIn(duration: 0.15))
                    .frame(width: model.isCollected ? 23 : 20, height: model.isCollected ? 23 : 20)
                    .foregroundColor(model.isCollected ? Color(.systemRed) : Color(.lightGray))
                    .onTapGesture(perform: onClickCollect)
        }
                .padding(12)
                .background(Color.white)
                .cornerRadius(8)
    }
}
