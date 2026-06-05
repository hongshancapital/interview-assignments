//
//  AppInfoCell.swift
//  DemoApp
//
//  Created by xiongmin on 2022/4/2.
//

import Foundation
import Kingfisher
import SwiftUI

struct APPInfoCell: View {
    var appInfo: AppInfoModel
    let favorTap: (Int) -> Void
    var body: some View {
        HStack {
            KFImage(URL(string: appInfo.appLogoUrl))
                .placeholder {
                    ProgressView()
                }
                .frame(width: 60, height: 60)
                .cornerRadius(8)
                .overlay {
                    RoundedRectangle(cornerRadius: 8).stroke(Color.gray, lineWidth: 0.2)
                }
            VStack(alignment: .leading) {
                Text(appInfo.appName).font(.system(size: 16, weight: .bold)).lineLimit(1).frame(alignment: .leading)
                Text(appInfo.description).font(.system(size: 13)).lineLimit(2).frame(alignment: .leading)
            }
            Spacer()
            Image(systemName: appInfo.isFavorite ? "heart.fill" : "heart")
                .foregroundColor(appInfo.isFavorite ? .red : .gray)
                .scaleEffect(appInfo.isFavorite ? 1.4 : 1.2)
                .animation(.interpolatingSpring(stiffness: 100, damping: 6), value: appInfo.isFavorite)
                .onTapGesture {
                    favorTap(appInfo.appId)
                }
        }
        .padding(12)
        .background(Color.white)
        .cornerRadius(16)
    }
}

struct APPInfoCell_Previews: PreviewProvider {
    static var previews: some View {
        let appInfo = AppInfoModel(appId: 1163852619,
                                   appLogoUrl: "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/3d/aa/70/3daa70a8-282a-ca97-3463-42b5714eca4c/source/60x60bb.jpg",
                                   appName: "Google Chat",
                                   description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export",
                                   isFavorite: false)

        APPInfoCell(appInfo: appInfo) { appId in
            print("has tap favor")
        }
    }
}
