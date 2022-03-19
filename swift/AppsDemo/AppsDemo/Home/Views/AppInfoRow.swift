//
//  AppInfoRow.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/5.
//

import SwiftUI
import Kingfisher

typealias AppInfoRowCollectBlock = (Int) -> Void

struct AppInfoRow: View {
    @Binding var appInfo: AppInfoModel
    var index: Int
    var collectBlock: AppInfoRowCollectBlock

    var body: some View {
        ZStack(alignment: .leading) {
            HStack (alignment: .center) {
                KFImage(URL(string: appInfo.imageUrlString)!)
                    .placeholder({ p in
                        ProgressView(p)
                            .foregroundColor(Color.h8f8e94)
                    })
                    .resizable()
                    .frame(width: 65,
                           height: 65)
                    .cornerRadius(preferredCornerRadius)
                    .overlay(RoundedRectangle(cornerRadius: preferredCornerRadius,
                                              style: .continuous)
                                .stroke(Color.hcecdd2,
                                        lineWidth: 1 / UIScreen.main.scale))
                VStack(alignment: .leading) {
                    Text(appInfo.name)
                        .font(.headline)
                        .fontWeight(.bold)
                        .lineLimit(1)
                        .id(AppInfoRow.Identifiers.title)
                    Spacer(minLength: 4)
                    Text(appInfo.description)
                        .font(.body)
                        .foregroundColor(Color.h8f8e94)
                        .padding(EdgeInsets(top: 0,
                                            leading: 0,
                                            bottom: -10,
                                            trailing: 0))
                        .lineLimit(2)
                        .id(AppInfoRow.Identifiers.subTitle)
                    Spacer()
                }
                Spacer()
                Button(action: {
                    withAnimation(.easeInOut(duration: 0.2)) {
                        collectBlock(index)
                    }
                }) {
                    if appInfo.isCollected {
                        Image(systemName: "suit.heart.fill")
                            .foregroundColor(Color.red)
                            .font(Font.system(size: 20))
                    } else {
                        Image(systemName: "suit.heart")
                            .foregroundColor(Color.h8f8e94)
                            .font(Font.system(size: 18))
                    }
                }
                .id(AppInfoRow.Identifiers.collectButton)
                .buttonStyle(.borderless)
                .frame(idealWidth: 36, idealHeight: 36, alignment: .center)
                .fixedSize()
            }
            .padding(preferredPadding)
        }
        .cardify()
    }
}

extension AppInfoRow {
    enum Identifiers {
        static let collectButton = "collectButton"
        static let title = "title"
        static let subTitle = "subTitle"
    }
}

struct AppInfoRow_Previews: PreviewProvider {
    static var previews: some View {
        AppInfoRow(appInfo: .constant(AppInfoModel.init(name: "google",
                                              description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.",
                                              imageUrlString: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/100x100bb.jpg")),
                   index: 0, collectBlock: {index in})
            .previewLayout(.fixed(width: 375, height: 95))
    }
}
