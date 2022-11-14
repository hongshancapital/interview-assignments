//
//  AppListRow.swift
//  AppList
//
//  Created by Xiaojian Duan on 2022/11/13.
//

import SwiftUI

struct AppListCell: View {
    struct UI {
        static let margin: CGFloat = 8
        static let iconSize: CGFloat = 60
        static let rowHeight: CGFloat = 70
        static let cornerRadius: CGFloat = 8
        static let padding: CGFloat = 5
    }
    
    let info: AppInfo
    let viewModel: AppListViewModel
    
    var body: some View {
        HStack {
            Spacer().frame(width: UI.margin)
            
            AsyncImage(url: URL(string: info.icon)) { image in
                image
                    .resizable()
                    .scaledToFit()
            } placeholder: {
                ProgressView()
            }
            .frame(width: UI.iconSize, height: UI.iconSize)
            .cornerRadius(UI.cornerRadius)
            .overlay(RoundedRectangle(cornerRadius: UI.cornerRadius, style: .circular).stroke(Color(.lightGray), lineWidth: 1))
            
            VStack(alignment: .leading, spacing: 6) {
                Text(info.name)
                    .font(.headline)
                    .fontWeight(.bold)
                Text(info.description)
                    .font(.subheadline)
            }
            
            Spacer()
            
            Button {
                self.viewModel.toggleLike(info.id)
            } label: {
                Label("Toggle Like", systemImage: info.like ? "heart.fill" : "heart")
                    .labelStyle(.iconOnly)
                    .foregroundColor(info.like ? .red : .gray)
            }
            
            Spacer().frame(width: UI.margin)
        }
        .frame(width: UIScreen.main.bounds.width - 30, height: UI.rowHeight)
        .background(Color.white)
    }
}

struct AppListRow_Previews: PreviewProvider {
    static var info = AppInfo(
        icon: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/d1/8e/c5/d18ec597-7ae3-10a3-826e-1e9f4f0cb65f/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/60x60bb.jpg",
        name: "Google Chat",
        description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export",
        id: 1163852619,
        like: true
    )
    static var vm = AppListViewModel()
    
    static var previews: some View {
        
        AppListCell(info: info, viewModel: vm)
            .previewLayout(.fixed(width: 375, height: 70))
    }
}
