//
//  AppListRowView.swift
//  AppMarket
//
//  Created by xcz on 2022/8/25.
//

import SwiftUI

struct AppListRowView: View {
    
    @EnvironmentObject var listVm: AppListViewModel
    @Binding var appInfo: AppInfoModel
    
    var body: some View {
        
        HStack {
            
            // 此处不适合使用AsyncImage，AsyncImage没有url缓存，每次滚动出现后台都会重新下载图片
            CachedAsyncImageView(urlString: appInfo.artworkUrl100)
                .frame(width: 50, height: 50)
            
            VStack(alignment:.leading, spacing: 5) {
                Text(appInfo.trackName)
                    .lineLimit(1)
                    .font(.callout.bold())
                    .minimumScaleFactor(0.5)

                Text(appInfo.description)
                    .lineLimit(2)
                    .font(.caption)
            }
            .padding(.trailing, 5)
            
            Spacer()
            
            Button {
                withAnimation {
                    appInfo.isCollected.toggle()
                    listVm.updateUserCollectedApps(appInfo: appInfo)
                }
            } label: {
                Image(systemName: appInfo.isCollected ? "heart.fill" : "heart")
                    .resizable()
                    .scaledToFill()
            }
            .frame(width: 16, height: 16)
            .scaleEffect(appInfo.isCollected ? 1.2 : 1.0)
            .symbolRenderingMode(appInfo.isCollected ? .multicolor : .monochrome)
            .foregroundColor(.secondary)
            .buttonStyle(.plain)

        }
        .padding()
        .background(Color(UIColor.systemBackground))
        .cornerRadius(10)
    }
}




struct AppListRowView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            let appInfo = AppInfoModel(
                trackId: 1163852619,
                trackName: "Google Chat",
                artworkUrl100: "https://is2-ssl.mzstatic.com/image/thumb/Purple112/v4/06/b2/84/06b284d5-e33f-7a28-c773-1ef86a5c5418/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/100x100bb.jpg",
                description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export",
                isCollected: true
            )
            AppListRowView(appInfo: .constant(appInfo))
                .preferredColorScheme(.light)
                .previewLayout(.sizeThatFits)
            AppListRowView(appInfo: .constant(appInfo))
                .preferredColorScheme(.dark)
                .previewLayout(.sizeThatFits)
        }
    }
}
